package com.example.books_tracker.service;

import com.example.books_tracker.data.entities.Authors;
import com.example.books_tracker.data.entities.Books;
import com.example.books_tracker.data.entities.Genres;
import com.example.books_tracker.data.repository.AuthorsRepository;
import com.example.books_tracker.data.repository.BooksRepository;
import com.example.books_tracker.data.repository.GenresRepository;
import com.example.books_tracker.specifications.BooksSpecifications;
import com.example.books_tracker.web.model.BookRequest;
import com.example.books_tracker.web.model.BookResponse;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

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
    public BookResponse createBook(BookRequest bookRequest) {
        List<Authors> authors = bookRequest.authors()
                .stream()
                .map(authorRequest -> authorsRepository.findByNameAndSurname(authorRequest.name(), authorRequest.surname())
                        .orElse(new Authors(authorRequest.name(), authorRequest.surname())))
                .toList();;
        List<Genres> genres = bookRequest.genres()
                .stream()
                .map(genresRequest -> genresRepository.findByName(genresRequest.name())
                        .orElse(new Genres(genresRequest.name())))
                .toList();

        Books book = new Books(
                bookRequest.title(),
                bookRequest.description(),
                bookRequest.pageNumber(),
                bookRequest.covering(),
                bookRequest.publicationYear());

        book.setAuthors(authors);
        book.setGenres(genres);

        Books savedBook = booksRepository.save(book);
        return new BookResponse(savedBook);
    }

    @Override
    public Books update(Books book) {
        return booksRepository.save(book);
    }

    @Override
    public void delete(Long aLong) {
        booksRepository.deleteById(aLong);
    }
}
