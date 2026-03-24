package com.rookies5.myspringbootlab.repository;

import com.rookies5.myspringbootlab.entity.BookDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BookDetailRepository extends JpaRepository<BookDetail, Long> {

    Optional<BookDetail> findByBookId(Long bookId);

    List<BookDetail> findByPublisher(String publisher);

    @Query("select bd from BookDetail bd join fetch bd.book where bd.id = :id")
    Optional<BookDetail> findByIdWithBook(Long id);
}