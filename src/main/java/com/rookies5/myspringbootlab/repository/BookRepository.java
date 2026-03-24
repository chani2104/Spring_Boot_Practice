package com.rookies5.myspringbootlab.repository;

import com.rookies5.myspringbootlab.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {

    Optional<Book> findByIsbn(String isbn);

    List<Book> findByAuthorContainingIgnoreCase(String author);

    List<Book> findByTitleContainingIgnoreCase(String title);

    boolean existsByIsbn(String isbn);

    @Query("select b from Book b left join fetch b.bookDetail where b.id = :id")
    Optional<Book> findByIdWithBookDetail(Long id);

    @Query("select b from Book b left join fetch b.bookDetail where b.isbn = :isbn")
    Optional<Book> findByIsbnWithBookDetail(String isbn);
}