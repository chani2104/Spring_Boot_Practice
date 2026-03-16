package com.rookies5.myspringbootlab.controller;

import com.rookies5.myspringbootlab.entity.Book;
import com.rookies5.myspringbootlab.exception.BusinessException;
import com.rookies5.myspringbootlab.repository.BookRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookRestController {

    private final BookRepository bookRepository;

    public BookRestController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    // POST /api/books : 새 도서 등록
    @PostMapping
    public ResponseEntity<Book> createBook(@RequestBody Book book) {
        Book savedBook = bookRepository.save(book);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedBook);
    }

    // GET /api/books : 모든 도서 조회
    @GetMapping
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    // GET /api/books/{id} : ID로 특정 도서 조회
    // Optional의 map / orElse 사용
    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        return bookRepository.findById(id)
                .map(book -> ResponseEntity.ok(book))
                .orElse(ResponseEntity.notFound().build());
    }

    // GET /api/books/isbn/{isbn} : ISBN으로 도서 조회
    // BusinessException 사용
    @GetMapping("/isbn/{isbn}")
    public Book getBookByIsbn(@PathVariable String isbn) {
        return bookRepository.findByIsbn(isbn)
                .orElseThrow(() -> new BusinessException("BOOK_NOT_FOUND", "해당 ISBN의 도서를 찾을 수 없습니다."));
    }

    // GET /api/books/author/{author} : 저자명으로 도서 목록 조회
    @GetMapping("/author/{author}")
    public List<Book> getBooksByAuthor(@PathVariable String author) {
        return bookRepository.findByAuthor(author);
    }

    // PUT /api/books/{id} : 도서 정보 수정
    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Long id, @RequestBody Book book) {
        return bookRepository.findById(id)
                .map(foundBook -> {
                    foundBook.setTitle(book.getTitle());
                    foundBook.setAuthor(book.getAuthor());
                    foundBook.setIsbn(book.getIsbn());
                    foundBook.setPrice(book.getPrice());
                    foundBook.setPublishDate(book.getPublishDate());

                    Book updatedBook = bookRepository.save(foundBook);
                    return ResponseEntity.ok(updatedBook);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // DELETE /api/books/{id} : 도서 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        return bookRepository.findById(id)
                .map(book -> {
                    bookRepository.delete(book);
                    return ResponseEntity.noContent().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}