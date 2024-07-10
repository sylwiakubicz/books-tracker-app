package com.example.books_tracker.controller;

import com.example.books_tracker.DTO.BookData;
import com.example.books_tracker.model.Books;
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
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.Base64;

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
    public ResponseEntity<String> create(@RequestBody BookData bookData) throws IOException {
        if (booksRepository.existsBooksByISBN(bookData.getISBN())) {
            return new ResponseEntity<>("Book existed!", HttpStatus.BAD_REQUEST);
        }
        addBook(bookData, null);
        return new ResponseEntity<>("Book created successfully", HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable Long id, @RequestBody BookData bookData) throws IOException {

        if (!booksRepository.existsBooksByBookId(id)) {
            return new ResponseEntity<>("Book not existed", HttpStatus.NOT_FOUND);
        }

        addBook(bookData, id);
        return new ResponseEntity<>("Book updated successfully", HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        booksService.delete(id);
        return new ResponseEntity<>("Book deleted", HttpStatus.OK);
    }

    private void addBook(BookData bookData, Long id) throws IOException {
        if (!bookData.getCovering().isEmpty()) {
            byte[] imageBytes = Base64.getDecoder().decode(bookData.getCovering());
            File tempFile = File.createTempFile("temp", null);
            FileUtils.writeByteArrayToFile(tempFile, imageBytes);
            UploadResponse uploadResponse = uploadService.uploadImageToDrive(tempFile, "image.jpg");
            bookData.setCovering(uploadResponse.getUrl());
        }
        else {
            bookData.setCovering(null);
        }

        Books book = new Books(
                bookData.getTitle(),
                bookData.getDescription(),
                bookData.getPageNumber(),
                bookData.getCovering(),
                bookData.getPublicationYear(),
                bookData.getAuthor(),
                bookData.getGenres(),
                bookData.getISBN());

        if (id != null) {
            book.setBookId(id);
        }

        booksService.create(book);
    }
}
