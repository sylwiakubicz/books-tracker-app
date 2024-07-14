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

    @GetMapping
    public ResponseEntity<Page<Books>> list(@RequestParam(required = false) String title,
                            @RequestParam(required = false) String authorName,
                            @RequestParam(required = false) String authorSurname,
                            @RequestParam(required = false) String genre,
                            @PageableDefault @ParameterObject Pageable pageable) {

        return new ResponseEntity<>(booksService.listBy(title, authorName, authorSurname, genre, pageable), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Books> get(@PathVariable Long id) {
        return new ResponseEntity<>(booksService.get(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> create(@ModelAttribute Books books,
                                         @RequestParam("image") MultipartFile file,
                                         @RequestParam("authorsJson") String authorsJson,
                                         @RequestParam("genresJson") String genresJson) throws IOException {
        if (booksRepository.existsBooksByISBN(books.getISBN())) {
            return new ResponseEntity<>("Book existed!", HttpStatus.BAD_REQUEST);
        }

        List<Authors> authors = JsonUtils.parseAuthors(authorsJson);
        List<Genres> genres = JsonUtils.parseGenres(genresJson);

        books.setAuthors(authors);
        books.setGenres(genres);

        if (!file.isEmpty()) {
            File tempFile = File.createTempFile("temp", null);
            file.transferTo(tempFile);
            UploadResponse uploadResponse = uploadService.uploadImageToDrive(tempFile, file.getOriginalFilename());
            books.setCovering(uploadResponse.getUrl());
        } else {
            books.setCovering(null);
        }

        booksService.create(books);
        return new ResponseEntity<>("Book created successfully", HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable Long id,
                                         @ModelAttribute Books books,
                                         @RequestParam("image")MultipartFile file,
                                         @RequestParam("authorsJson") String authorsJson,
                                         @RequestParam("genresJson") String genresJson) throws IOException {

        if (!booksRepository.existsBooksByBookId(id)) {
            return new ResponseEntity<>("Book not existed", HttpStatus.NOT_FOUND);
        }
        books.setBookId(id);

        List<Authors> authors = JsonUtils.parseAuthors(authorsJson);
        List<Genres> genres = JsonUtils.parseGenres(genresJson);

        books.setAuthors(authors);
        books.setGenres(genres);

        if (!file.isEmpty()) {
            File tempFile = File.createTempFile("temp", null);
            file.transferTo(tempFile);
            UploadResponse uploadResponse = uploadService.uploadImageToDrive(tempFile, file.getOriginalFilename());
            books.setCovering(uploadResponse.getUrl());
        } else {
            books.setCovering(null);
        }

        booksService.updateBook(books);
        return new ResponseEntity<>("Book updated successfully", HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        booksService.delete(id);
        return new ResponseEntity<>("Book deleted", HttpStatus.OK);
    }
}
