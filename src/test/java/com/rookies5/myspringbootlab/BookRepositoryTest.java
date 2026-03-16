package com.rookies5.myspringbootlab;

import com.rookies5.myspringbootlab.entity.Book;
import com.rookies5.myspringbootlab.repository.BookRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Test
    @DisplayName("도서 등록 테스트")
    void testCreateBook() {
        Book book1 = new Book(
                "스프링 부트 입문",
                "홍길동",
                "9788956746425",
                30000,
                LocalDate.of(2025, 5, 7)
        );

        Book book2 = new Book(
                "JPA 프로그래밍",
                "박둘리",
                "9788956746432",
                35000,
                LocalDate.of(2025, 4, 30)
        );

        Book savedBook1 = bookRepository.save(book1);
        Book savedBook2 = bookRepository.save(book2);

        assertNotNull(savedBook1.getId());
        assertNotNull(savedBook2.getId());
        assertEquals("스프링 부트 입문", savedBook1.getTitle());
        assertEquals("JPA 프로그래밍", savedBook2.getTitle());
    }

    @Test
    @DisplayName("ISBN으로 도서 조회 테스트")
    void testFindByIsbn() {
        bookRepository.save(new Book(
                "스프링 부트 입문",
                "홍길동",
                "9788956746425",
                30000,
                LocalDate.of(2025, 5, 7)
        ));

        Optional<Book> foundBook = bookRepository.findByIsbn("9788956746425");

        assertTrue(foundBook.isPresent());
        assertEquals("스프링 부트 입문", foundBook.get().getTitle());
        assertEquals("홍길동", foundBook.get().getAuthor());
    }

    @Test
    @DisplayName("저자명으로 도서 목록 조회 테스트")
    void testFindByAuthor() {
        bookRepository.save(new Book(
                "스프링 부트 입문",
                "홍길동",
                "9788956746425",
                30000,
                LocalDate.of(2025, 5, 7)
        ));

        bookRepository.save(new Book(
                "스프링 핵심 원리",
                "홍길동",
                "9788956746000",
                28000,
                LocalDate.of(2025, 3, 1)
        ));

        bookRepository.save(new Book(
                "JPA 프로그래밍",
                "박둘리",
                "9788956746432",
                35000,
                LocalDate.of(2025, 4, 30)
        ));

        List<Book> books = bookRepository.findByAuthor("홍길동");

        assertEquals(2, books.size());
    }

    @Test
    @DisplayName("도서 정보 수정 테스트")
    void testUpdateBook() {
        Book book = bookRepository.save(new Book(
                "스프링 부트 입문",
                "홍길동",
                "9788956746425",
                30000,
                LocalDate.of(2025, 5, 7)
        ));

        book.setPrice(32000);
        book.setTitle("스프링 부트 입문 개정판");

        Book updatedBook = bookRepository.save(book);

        assertEquals("스프링 부트 입문 개정판", updatedBook.getTitle());
        assertEquals(32000, updatedBook.getPrice());
    }

    @Test
    @DisplayName("도서 삭제 테스트")
    void testDeleteBook() {
        Book book = bookRepository.save(new Book(
                "JPA 프로그래밍",
                "박둘리",
                "9788956746432",
                35000,
                LocalDate.of(2025, 4, 30)
        ));

        Long id = book.getId();

        bookRepository.deleteById(id);

        Optional<Book> deletedBook = bookRepository.findById(id);
        assertFalse(deletedBook.isPresent());
    }
}