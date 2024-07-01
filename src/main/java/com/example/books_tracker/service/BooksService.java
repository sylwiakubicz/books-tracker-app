package com.example.books_tracker.service;

import com.example.books_tracker.model.Authors;
import com.example.books_tracker.model.Books;
import com.example.books_tracker.model.Genres;
import com.example.books_tracker.repository.AuthorsRepository;
import com.example.books_tracker.repository.BooksRepository;
import com.example.books_tracker.repository.GenresRepository;
import com.example.books_tracker.specifications.BooksSpecifications;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

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

    public Page<Books> listBy(String title, String authorName, String authorSurname, String genre, Pageable pageable) {
        return booksRepository.findAll(BooksSpecifications.findBooksSpecification(title, authorName, authorSurname, genre), pageable);
    }
    @Override
    public Books get(Long aLong) {
        return booksRepository.findById(aLong).orElseThrow(() -> new NoSuchElementException("Book not found"));
    }

    @Transactional
    public Books create(Books book) {
        List<Authors> managedAuthors = new ArrayList<>();
        for (Authors author : book.getAuthors()) {
            Authors managedAuthor = authorsRepository.findByNameAndSurname(author.getName(), author.getSurname())
                    .orElse(author);
            managedAuthors.add(managedAuthor);
        }
        book.setAuthors(managedAuthors);

        List<Genres> managedGenres = new ArrayList<>();
        for (Genres genre : book.getGenres()) {
            Genres managedGenre = genresRepository.findByName(genre.getName())
                    .orElse(genre);
            managedGenres.add(managedGenre);
        }
        book.setGenres(managedGenres);

        return booksRepository.save(book);
    }

    @Transactional
    public Books updateBook(Books book) {

        List<Authors> managedAuthors = new ArrayList<>();
        for (Authors author : book.getAuthors()) {
            Authors managedAuthor = authorsRepository.findByNameAndSurname(author.getName(), author.getSurname())
                    .orElse(author);
            managedAuthors.add(managedAuthor);
        }
        book.setAuthors(managedAuthors);

        List<Genres> managedGenres = new ArrayList<>();
        for (Genres genre : book.getGenres()) {
            Genres managedGenre = genresRepository.findByName(genre.getName())
                    .orElse(genre);
            managedGenres.add(managedGenre);
        }
        book.setGenres(managedGenres);



        return booksRepository.save(book);
    }

    @Override
    public Books update(Books book) {
        return null;
    }

    @Override
    public void delete(Long aLong) {
        booksRepository.deleteById(aLong);
    }
}
