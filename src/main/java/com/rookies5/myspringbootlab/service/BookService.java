package com.rookies5.myspringbootlab.service;

import com.rookies5.myspringbootlab.dto.BookDTO;
import com.rookies5.myspringbootlab.entity.Book;
import com.rookies5.myspringbootlab.entity.BookDetail;
import com.rookies5.myspringbootlab.exception.BusinessException;
import com.rookies5.myspringbootlab.repository.BookDetailRepository;
import com.rookies5.myspringbootlab.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final BookDetailRepository bookDetailRepository;

    public List<BookDTO.Response> getAllBooks() {
        return bookRepository.findAll()
                .stream()
                .map(BookDTO.Response::fromEntity)
                .toList();
    }

    public BookDTO.Response getBookById(Long id) {
        Book book = bookRepository.findByIdWithBookDetail(id)
                .orElseThrow(() -> new BusinessException("Book not found", HttpStatus.NOT_FOUND));
        return BookDTO.Response.fromEntity(book);
    }

    public BookDTO.Response getBookByIsbn(String isbn) {
        Book book = bookRepository.findByIsbnWithBookDetail(isbn)
                .orElseThrow(() -> new BusinessException("Book not found", HttpStatus.NOT_FOUND));
        return BookDTO.Response.fromEntity(book);
    }

    public List<BookDTO.Response> getBooksByAuthor(String author) {
        return bookRepository.findByAuthorContainingIgnoreCase(author)
                .stream()
                .map(BookDTO.Response::fromEntity)
                .toList();
    }

    public List<BookDTO.Response> getBooksByTitle(String title) {
        return bookRepository.findByTitleContainingIgnoreCase(title)
                .stream()
                .map(BookDTO.Response::fromEntity)
                .toList();
    }

    @Transactional
    public BookDTO.Response createBook(BookDTO.Request request) {
        if (bookRepository.existsByIsbn(request.getIsbn())) {
            throw new BusinessException("ISBN already exists", HttpStatus.BAD_REQUEST);
        }

        Book book = Book.builder()
                .title(request.getTitle())
                .author(request.getAuthor())
                .isbn(request.getIsbn())
                .price(request.getPrice())
                .publishDate(request.getPublishDate())
                .build();

        if (request.getDetailRequest() != null) {
            BookDTO.BookDetailDTO d = request.getDetailRequest();

            BookDetail detail = BookDetail.builder()
                    .description(d.getDescription())
                    .language(d.getLanguage())
                    .pageCount(d.getPageCount())
                    .publisher(d.getPublisher())
                    .coverImageUrl(d.getCoverImageUrl())
                    .edition(d.getEdition())
                    .build();

            book.setBookDetail(detail);
        }

        Book savedBook = bookRepository.save(book);
        return BookDTO.Response.fromEntity(savedBook);
    }

    @Transactional
    public BookDTO.Response updateBook(Long id, BookDTO.Request request) {
        Book book = bookRepository.findByIdWithBookDetail(id)
                .orElseThrow(() -> new BusinessException("Book not found", HttpStatus.NOT_FOUND));

        if (!book.getIsbn().equals(request.getIsbn())
                && bookRepository.existsByIsbn(request.getIsbn())) {
            throw new BusinessException("ISBN already exists", HttpStatus.BAD_REQUEST);
        }

        book.setTitle(request.getTitle());
        book.setAuthor(request.getAuthor());
        book.setIsbn(request.getIsbn());
        book.setPrice(request.getPrice());
        book.setPublishDate(request.getPublishDate());

        if (request.getDetailRequest() != null) {
            BookDetail detail = book.getBookDetail();

            if (detail == null) {
                detail = new BookDetail();
                book.setBookDetail(detail);
            }

            detail.setDescription(request.getDetailRequest().getDescription());
            detail.setLanguage(request.getDetailRequest().getLanguage());
            detail.setPageCount(request.getDetailRequest().getPageCount());
            detail.setPublisher(request.getDetailRequest().getPublisher());
            detail.setCoverImageUrl(request.getDetailRequest().getCoverImageUrl());
            detail.setEdition(request.getDetailRequest().getEdition());
        }

        return BookDTO.Response.fromEntity(book);
    }

    @Transactional
    public BookDTO.Response patchBook(Long id, BookDTO.PatchRequest request) {
        Book book = bookRepository.findByIdWithBookDetail(id)
                .orElseThrow(() -> new BusinessException("Book not found", HttpStatus.NOT_FOUND));

        if (request.getIsbn() != null) {
            if (!book.getIsbn().equals(request.getIsbn())
                    && bookRepository.existsByIsbn(request.getIsbn())) {
                throw new BusinessException("ISBN already exists", HttpStatus.BAD_REQUEST);
            }
            book.setIsbn(request.getIsbn());
        }

        if (request.getTitle() != null) {
            book.setTitle(request.getTitle());
        }
        if (request.getAuthor() != null) {
            book.setAuthor(request.getAuthor());
        }
        if (request.getPrice() != null) {
            book.setPrice(request.getPrice());
        }
        if (request.getPublishDate() != null) {
            book.setPublishDate(request.getPublishDate());
        }

        if (request.getDetailRequest() != null) {
            BookDetail detail = book.getBookDetail();

            if (detail == null) {
                detail = new BookDetail();
                book.setBookDetail(detail);
            }

            BookDTO.BookDetailPatchRequest d = request.getDetailRequest();

            if (d.getDescription() != null) {
                detail.setDescription(d.getDescription());
            }
            if (d.getLanguage() != null) {
                detail.setLanguage(d.getLanguage());
            }
            if (d.getPageCount() != null) {
                detail.setPageCount(d.getPageCount());
            }
            if (d.getPublisher() != null) {
                detail.setPublisher(d.getPublisher());
            }
            if (d.getCoverImageUrl() != null) {
                detail.setCoverImageUrl(d.getCoverImageUrl());
            }
            if (d.getEdition() != null) {
                detail.setEdition(d.getEdition());
            }
        }

        return BookDTO.Response.fromEntity(book);
    }

    @Transactional
    public BookDTO.Response patchBookDetail(Long id, BookDTO.BookDetailPatchRequest request) {
        Book book = bookRepository.findByIdWithBookDetail(id)
                .orElseThrow(() -> new BusinessException("Book not found", HttpStatus.NOT_FOUND));

        BookDetail detail = book.getBookDetail();
        if (detail == null) {
            detail = new BookDetail();
            book.setBookDetail(detail);
        }

        if (request.getDescription() != null) {
            detail.setDescription(request.getDescription());
        }
        if (request.getLanguage() != null) {
            detail.setLanguage(request.getLanguage());
        }
        if (request.getPageCount() != null) {
            detail.setPageCount(request.getPageCount());
        }
        if (request.getPublisher() != null) {
            detail.setPublisher(request.getPublisher());
        }
        if (request.getCoverImageUrl() != null) {
            detail.setCoverImageUrl(request.getCoverImageUrl());
        }
        if (request.getEdition() != null) {
            detail.setEdition(request.getEdition());
        }

        return BookDTO.Response.fromEntity(book);
    }

    @Transactional
    public void deleteBook(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Book not found", HttpStatus.NOT_FOUND));

        bookRepository.delete(book);
    }
}