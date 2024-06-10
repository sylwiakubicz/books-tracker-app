package com.example.books_tracker.service;

import com.example.books_tracker.model.Authors;
import com.example.books_tracker.model.Books;
import com.example.books_tracker.model.Genres;
import com.example.books_tracker.repository.AuthorsRepository;
import com.example.books_tracker.repository.BooksRepository;
import com.example.books_tracker.repository.GenresRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

    @Override
    public Books get(Long aLong) {
        return null;
    }

    @Override
    @Transactional
    public Books create(Books books) {
        List<Authors> managedAuthors = new ArrayList<>();
        for (Authors author : books.getAuthors()) {
            Authors managedAuthor = authorsRepository.findByNameAndSurname(author.getName(), author.getSurname())
                    .orElse(author);
            managedAuthors.add(managedAuthor);
        }
        books.setAuthors(managedAuthors);

        List<Genres> managedGenres = new ArrayList<>();
        for (Genres genre : books.getGenres()) {
            Genres managedGenre = genresRepository.findByName(genre.getName())
                    .orElse(genre);
            managedGenres.add(managedGenre);
        }
        books.setGenres(managedGenres);

        return booksRepository.save(books);
    }

    @Override
    public Books update(Books books) {
        return null;
    }

    @Override
    public void delete(Long aLong) {
        booksRepository.deleteById(aLong);
    }
}
