package com.example.books_tracker.service;

import com.example.books_tracker.model.Books;
import com.example.books_tracker.repository.BooksRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BooksService implements CrudService<Books, Long>{

    private final BooksRepository booksRepository;

    public BooksService(BooksRepository booksRepository) {
        this.booksRepository = booksRepository;
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
    public Books create(Books books) {
        return null;
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
