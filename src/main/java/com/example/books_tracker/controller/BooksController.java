package com.example.books_tracker.controller;

import com.example.books_tracker.model.Books;
import com.example.books_tracker.repository.BooksRepository;
import com.example.books_tracker.service.BooksService;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/books")
public class BooksController {

    private final BooksService booksService;
    private final BooksRepository booksRepository;

    public BooksController(BooksService booksService, BooksRepository booksRepository) {
        this.booksService = booksService;
        this.booksRepository = booksRepository;
    }

    @GetMapping
    public Page<Books> list(@RequestParam(required = false) String title,
                            @RequestParam(required = false) String authorName,
                            @RequestParam(required = false) String authorSurname,
                            @RequestParam(required = false) String genre,
                            @PageableDefault @ParameterObject Pageable pageable) {

        return booksService.listBy(title, authorName, authorSurname, genre, pageable);
    }


    @GetMapping("/{id}")
    public Books get(@PathVariable Long id) {
        return booksService.get(id);
    }

    @PostMapping
    public ResponseEntity<String> create(@RequestBody Books books) {
        if (booksRepository.existsBooksByISBN(books.getISBN())) {
            return new ResponseEntity<>("Book existed!", HttpStatus.BAD_REQUEST);
        }
        booksService.create(books);
        return new ResponseEntity<>("Book created successfully", HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        booksService.delete(id);
    }

}
