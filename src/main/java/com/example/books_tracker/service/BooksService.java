package com.example.books_tracker.service;

import com.example.books_tracker.model.Authors;
import com.example.books_tracker.model.Books;
import com.example.books_tracker.model.Genres;
import com.example.books_tracker.repository.AuthorsRepository;
import com.example.books_tracker.repository.BooksRepository;
import com.example.books_tracker.repository.GenresRepository;
import com.example.books_tracker.specifications.BooksSpecifications;
import jakarta.transaction.Transactional;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

@Service
public class BooksService implements CrudService<Books, Long>{

    private final BooksRepository booksRepository;
    private final GenresRepository genresRepository;
    private final AuthorsRepository authorsRepository;

    public BooksService(BooksRepository booksRepository, GenresRepository genresRepository, AuthorsRepository authorsRepository) {
        this.booksRepository = booksRepository;
        this.genresRepository = genresRepository;
        this.authorsRepository = authorsRepository;
    }

    @Override
    public List<Books> list() {
        return booksRepository.findAll();
    }

    @Cacheable(cacheNames = "randomBooksCache", key = "T(java.time.LocalDate).now().toString()")
    public List<Books> randomBookList(int limit) {
        Long minId = booksRepository.findMinId();
        Long maxId = booksRepository.findMaxId();

        if (minId == null || maxId == null || minId.equals(maxId)) {
            return List.of();
        }

        List<Long> randomIds = getRandomIds(limit * 2, minId, maxId);
        List<Books> randomBooks = booksRepository.findByIds(randomIds);

        if (randomBooks.size() > limit) {
            Random random = new Random();
            return random.ints(0, randomBooks.size())
                    .distinct()
                    .limit(limit)
                    .mapToObj(randomBooks::get)
                    .collect(Collectors.toList());
        }

        return randomBooks;
    }

    public List<Books> getNewBooks( String genre) {
        return booksRepository.findTop10ByGenre(genre);
    }

    public Page<Books> listBy(String search, String title, Integer year, Pageable pageable) {
        return booksRepository.findAll(BooksSpecifications.findBooksSpecification(search, title, year), pageable);
    }

    @Override
    public Books get(Long aLong) {
        return booksRepository.findById(aLong).orElseThrow(() -> new NoSuchElementException("Book not found"));
    }

    @Transactional
    public Books create(Books book) {

        book.setAuthors(managedAuthors(book));
        book.setGenres(managedGenres(book));

        return booksRepository.save(book);
    }

    @Transactional
    public Books updateBook(Books book) {

        book.setAuthors(managedAuthors(book));
        book.setGenres(managedGenres(book));

        return booksRepository.save(book);
    }

    @Override
    public Books update(Books books) {
        return null;
    }

    @Override
    public void delete(Long aLong) {
        booksRepository.deleteById(aLong);
    }

    private List<Authors> managedAuthors(Books book) {
        List<Authors> managedAuthors = new ArrayList<>();
        for (Authors author : book.getAuthors()) {
            Authors managedAuthor = authorsRepository.findByNameAndSurname(author.getName(), author.getSurname())
                    .orElse(author);
            managedAuthors.add(managedAuthor);
        }
        return managedAuthors;
    }

    private List<Genres> managedGenres(Books book) {
        List<Genres> managedGenres = new ArrayList<>();
        for (Genres genre : book.getGenres()) {
            Genres managedGenre = genresRepository.findByName(genre.getName())
                    .orElse(genre);
            managedGenres.add(managedGenre);
        }
        return managedGenres;
    }

    private List<Long> getRandomIds(int count, Long minId, Long maxId) {
        Random random = new Random();
        return LongStream.generate(() -> minId + (long) (random.nextDouble() * (maxId - minId + 1)))
                .distinct()
                .limit(count)
                .boxed()
                .collect(Collectors.toList());
    }
}
