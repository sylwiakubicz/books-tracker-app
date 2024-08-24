package com.example.books_tracker.repository;

import com.example.books_tracker.model.Books;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BooksRepository extends JpaRepository<Books, Long>, JpaSpecificationExecutor<Books> {
    Boolean existsBooksByISBN(String isbn);
    Boolean existsBooksByBookId(Long id);

    @Query("SELECT MIN(e.bookId) FROM Books e")
    Long findMinId();

    @Query("SELECT MAX(e.bookId) FROM Books e")
    Long findMaxId();

    @Query("SELECT e FROM Books e WHERE e.bookId IN :ids")
    List<Books> findByIds(@Param("ids") List<Long> ids);
}
