package com.rookies5.myspringbootlab.dto;

import com.rookies5.myspringbootlab.entity.Book;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public class BookDTO {

    public static class BookCreateRequest {

        @NotBlank(message = "제목은 필수입니다.")
        private String title;

        @NotBlank(message = "저자는 필수입니다.")
        private String author;

        @NotBlank(message = "ISBN은 필수입니다.")
        private String isbn;

        @NotNull(message = "가격은 필수입니다.")
        @Min(value = 0, message = "가격은 0 이상이어야 합니다.")
        private Integer price;

        @NotNull(message = "출판일은 필수입니다.")
        private LocalDate publishDate;

        public String getTitle() {
            return title;
        }

        public String getAuthor() {
            return author;
        }

        public String getIsbn() {
            return isbn;
        }

        public Integer getPrice() {
            return price;
        }

        public LocalDate getPublishDate() {
            return publishDate;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public void setIsbn(String isbn) {
            this.isbn = isbn;
        }

        public void setPrice(Integer price) {
            this.price = price;
        }

        public void setPublishDate(LocalDate publishDate) {
            this.publishDate = publishDate;
        }
    }

    public static class BookUpdateRequest {
        private String title;
        private String author;

        @Min(value = 0, message = "가격은 0 이상이어야 합니다.")
        private Integer price;

        private LocalDate publishDate;

        public String getTitle() {
            return title;
        }

        public String getAuthor() {
            return author;
        }

        public Integer getPrice() {
            return price;
        }

        public LocalDate getPublishDate() {
            return publishDate;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public void setPrice(Integer price) {
            this.price = price;
        }

        public void setPublishDate(LocalDate publishDate) {
            this.publishDate = publishDate;
        }
    }

    public static class BookResponse {
        private Long id;
        private String title;
        private String author;
        private String isbn;
        private Integer price;
        private LocalDate publishDate;

        public BookResponse() {
        }

        public BookResponse(Long id, String title, String author, String isbn, Integer price, LocalDate publishDate) {
            this.id = id;
            this.title = title;
            this.author = author;
            this.isbn = isbn;
            this.price = price;
            this.publishDate = publishDate;
        }

        public static BookResponse fromEntity(Book book) {
            return new BookResponse(
                    book.getId(),
                    book.getTitle(),
                    book.getAuthor(),
                    book.getIsbn(),
                    book.getPrice(),
                    book.getPublishDate()
            );
        }

        public Long getId() {
            return id;
        }

        public String getTitle() {
            return title;
        }

        public String getAuthor() {
            return author;
        }

        public String getIsbn() {
            return isbn;
        }

        public Integer getPrice() {
            return price;
        }

        public LocalDate getPublishDate() {
            return publishDate;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public void setIsbn(String isbn) {
            this.isbn = isbn;
        }

        public void setPrice(Integer price) {
            this.price = price;
        }

        public void setPublishDate(LocalDate publishDate) {
            this.publishDate = publishDate;
        }
    }
}