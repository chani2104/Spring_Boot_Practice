package com.rookies5.myspringbootlab.service;

import com.rookies5.myspringbootlab.dto.BookDTO;
import com.rookies5.myspringbootlab.entity.Book;
import com.rookies5.myspringbootlab.exception.BusinessException;
import com.rookies5.myspringbootlab.repository.BookRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public BookDTO.BookResponse createBook(BookDTO.BookCreateRequest request) {
        if (bookRepository.findByIsbn(request.getIsbn()).isPresent()) {
            throw new BusinessException("DUPLICATE_ISBN", "이미 존재하는 ISBN입니다.");
        }

        Book book = new Book(
                request.getTitle(),
                request.getAuthor(),
                request.getIsbn(),
                request.getPrice(),
                request.getPublishDate()
        );

        Book savedBook = bookRepository.save(book);
        return BookDTO.BookResponse.fromEntity(savedBook);
    }

    @Transactional(readOnly = true)
    public List<BookDTO.BookResponse> getAllBooks() {
        return bookRepository.findAll()
                .stream()
                .map(BookDTO.BookResponse::fromEntity)
                .toList();
    }

    @Transactional(readOnly = true)
    public BookDTO.BookResponse getBookById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BusinessException("BOOK_NOT_FOUND", "해당 ID의 도서를 찾을 수 없습니다."));
        return BookDTO.BookResponse.fromEntity(book);
    }

    @Transactional(readOnly = true)
    public BookDTO.BookResponse getBookByIsbn(String isbn) {
        Book book = bookRepository.findByIsbn(isbn)
                .orElseThrow(() -> new BusinessException("BOOK_NOT_FOUND", "해당 ISBN의 도서를 찾을 수 없습니다."));
        return BookDTO.BookResponse.fromEntity(book);
    }

    @Transactional(readOnly = true)
    public List<BookDTO.BookResponse> getBooksByAuthor(String author) {
        return bookRepository.findByAuthor(author)
                .stream()
                .map(BookDTO.BookResponse::fromEntity)
                .toList();
    }

    public BookDTO.BookResponse updateBook(Long id, BookDTO.BookUpdateRequest request) {
        Book existBook = bookRepository.findById(id)
                .orElseThrow(() -> new BusinessException("BOOK_NOT_FOUND", "수정할 도서를 찾을 수 없습니다."));

        if (request.getTitle() != null) {
            existBook.setTitle(request.getTitle());
        }

        if (request.getAuthor() != null) {
            existBook.setAuthor(request.getAuthor());
        }

        if (request.getPrice() != null) {
            existBook.setPrice(request.getPrice());
        }

        if (request.getPublishDate() != null) {
            existBook.setPublishDate(request.getPublishDate());
        }

        return BookDTO.BookResponse.fromEntity(existBook);
    }

    public void deleteBook(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BusinessException("BOOK_NOT_FOUND", "삭제할 도서를 찾을 수 없습니다."));
        bookRepository.delete(book);
    }
}