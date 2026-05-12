package com.max.bookwishlist.service;

import com.max.bookwishlist.exception.BookNotFoundException;
import com.max.bookwishlist.model.Book;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Service
public class BookService {
    public List<Book> getAllBooks() {
        return List.of(
                new Book(1L, "The Hobbit", "J.R.R. Tolkien", 1937),
                new Book(2L, "1984", "George Orwell", 1949),
                new Book(3L, "Dune", "Frank Herbert", 1965)
        );
    }

    public Book getBookById(Long id){
        for  (Book book : getAllBooks()) {
            if (book.getId().equals(id)) {
                return book;
            }
        }
        throw new BookNotFoundException(id);
    }
}
