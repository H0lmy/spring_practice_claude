package com.max.bookwishlist.service;

import com.max.bookwishlist.dto.CreateBookRequest;
import com.max.bookwishlist.dto.UpdateBookRequest;
import com.max.bookwishlist.exception.BookNotFoundException;
import com.max.bookwishlist.model.Book;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Slf4j
@Service
public class BookService {
    private final List<Book> books = new ArrayList<>(List.of(
            new Book(1L, "The Hobbit", "J.R.R. Tolkien", 1937),
            new Book(2L, "1984", "George Orwell", 1949),
            new Book(3L, "Dune", "Frank Herbert", 1965)
    ));

    private Long nextId = 4L;

    public List<Book> getAllBooks() {
        return List.copyOf(books);
    }

    public Book getBookById(Long id) {
        for (Book book : books) {
            if (book.getId().equals(id)) {
                return book;
            }
        }
        throw new BookNotFoundException(id);
    }

    public Book createBook(CreateBookRequest request) {
        log.info("Creating a new book with title{}", request.getTitle());
        Book book = new Book();
        book.setId(nextId++);
        book.setAuthor(request.getAuthor());
        book.setYear(request.getYear());
        book.setTitle(request.getTitle());
        books.add(book);
        log.info("Created a new book with title {}", book.getTitle());
        return book;
    }

    public Book updateBook(Long id, UpdateBookRequest request) {
        Book book = getBookById(id);
        book.setTitle(request.getTitle());
        book.setAuthor(request.getAuthor());
        book.setYear(request.getYear());
        return book;
    }

    public void deleteBook(Long id) {
        boolean removed = books.removeIf(book -> book.getId().equals(id));
        if(!removed){
            throw new BookNotFoundException(id);
        }

    }
}
