package com.example.books_tracker.web.controller;

import com.example.books_tracker.data.entities.Books;
import com.example.books_tracker.service.BooksService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/books")
public class BooksController {

    private final BooksService booksService;

    public BooksController(BooksService booksService) {
        this.booksService = booksService;
    }

    @GetMapping
    public Page<Books> list(@RequestParam(required = false) String title,
                            @RequestParam(required = false) String authorName,
                            @RequestParam(required = false) String authorSurname,
                            @RequestParam(required = false) String genre, @PageableDefault(size=2, sort = "id")Pageable pageable) {

        return booksService.listBy(title, authorName, authorSurname, genre, pageable);
    }


    @GetMapping("/{id}")
    public Books get(@PathVariable Long id) {
        return booksService.get(id);
    }

    @PostMapping
    public Books create(@RequestBody Books books) {
        return booksService.create(books);
    }

    @PutMapping("/{id}")
    public Books update(@PathVariable Long id, @RequestBody Books book){
        book.setBookId(id);
        return booksService.update(book);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        booksService.delete(id);
    }

}
