package com.example.books_tracker.controller;

import com.example.books_tracker.model.Authors;
import com.example.books_tracker.model.Books;
import com.example.books_tracker.model.Genres;
import com.example.books_tracker.model.UploadResponse;
import com.example.books_tracker.repository.BooksRepository;
import com.example.books_tracker.service.BooksService;
import com.example.books_tracker.service.UploadService;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/books")
public class BooksController {

    private final BooksService booksService;
    private final BooksRepository booksRepository;
    private final UploadService uploadService;

    public BooksController(BooksService booksService, BooksRepository booksRepository, UploadService uploadService) {
        this.booksService = booksService;
        this.booksRepository = booksRepository;
        this.uploadService = uploadService;
    }

    @GetMapping("/get")
    public ResponseEntity<Page<Books>> list(@RequestParam(required = false) String search,
                            @RequestParam(required = false) String genre,
                            @RequestParam(required = false) Integer year,
                            @PageableDefault @ParameterObject Pageable pageable) {

        return new ResponseEntity<>(booksService.listBy(search, genre, year,  pageable), HttpStatus.OK);
    }

    @GetMapping("/get/random")
    public ResponseEntity<List<Books>> getRandom() {
        return new ResponseEntity<>(booksService.randomBookList(10), HttpStatus.OK);
    }

    @GetMapping("/get/newest")
    public ResponseEntity<List<Books>> getNewest(@RequestParam String genre) {
        return new ResponseEntity<>(booksService.getNewBooks(genre), HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Books> get(@PathVariable Long id) {
        return new ResponseEntity<>(booksService.get(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> create(@ModelAttribute Books books,
                                         @RequestParam(value="image", required = false) MultipartFile file,
                                         @RequestParam("authorsJson") String authorsJson,
                                         @RequestParam("genresJson") String genresJson) throws IOException {
        if (booksRepository.existsBooksByISBN(books.getISBN())) {
            return new ResponseEntity<>(Map.of("message", "Book existed!"), HttpStatus.BAD_REQUEST);
        }

        List<Authors> authors = JsonUtils.parseAuthors(authorsJson);
        List<Genres> genres = JsonUtils.parseGenres(genresJson);

        books.setAuthors(authors);
        books.setGenres(genres);

        if (file != null && !file.isEmpty()) {
            File tempFile = File.createTempFile("temp", null);
            file.transferTo(tempFile);
            UploadResponse uploadResponse = uploadService.uploadImageToDrive(tempFile, file.getOriginalFilename());
            books.setCovering(uploadResponse.getUrl());
        } else {
            books.setCovering(null);
        }

        booksService.create(books);
        return new ResponseEntity<>(Map.of("message", "Book created successfully"), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id,
                                         @ModelAttribute Books books,
                                         @RequestParam(value="image", required = false)MultipartFile file,
                                         @RequestParam("authorsJson") String authorsJson,
                                         @RequestParam("genresJson") String genresJson) throws IOException {

        if (!booksRepository.existsBooksByBookId(id)) {
            return new ResponseEntity<>(Map.of("message", "Book not existed"),  HttpStatus.NOT_FOUND);
        }
        books.setBookId(id);

        List<Authors> authors = JsonUtils.parseAuthors(authorsJson);
        List<Genres> genres = JsonUtils.parseGenres(genresJson);

        books.setAuthors(authors);
        books.setGenres(genres);

        if (file != null && !file.isEmpty()) {
            File tempFile = File.createTempFile("temp", null);
            file.transferTo(tempFile);
            UploadResponse uploadResponse = uploadService.uploadImageToDrive(tempFile, file.getOriginalFilename());
            books.setCovering(uploadResponse.getUrl());
        } else {
            books.setCovering(null);
        }

        booksService.updateBook(books);
        return new ResponseEntity<>(Map.of("message", "Book updated successfully"), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        booksService.delete(id);
        return new ResponseEntity<>(Map.of("message", "Book deleted"), HttpStatus.OK);

    }
}
