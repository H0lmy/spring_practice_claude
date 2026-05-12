package com.max.bookwishlist.service;

import com.max.bookwishlist.dto.CreateBookRequest;
import com.max.bookwishlist.dto.UpdateBookRequest;
import com.max.bookwishlist.exception.BookNotFoundException;
import com.max.bookwishlist.model.Book;

import com.max.bookwishlist.repository.BookRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;

    public List<Book> getAllBooks() {
        return  bookRepository.findAll();
    }

    public Book getBookById(Long id) {
        return bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException(id));
    }

    public Book createBook(CreateBookRequest request) {
        Book book = new Book();
        book.setAuthor(request.getAuthor());
        book.setYear(request.getYear());
        book.setTitle(request.getTitle());
        return bookRepository.save(book);
    }

    public Book updateBook(Long id, UpdateBookRequest request) {
        Book book = getBookById(id);
        book.setTitle(request.getTitle());
        book.setAuthor(request.getAuthor());
        book.setYear(request.getYear());
        return bookRepository.save(book);
    }

    public void deleteBook(Long id) {
        if(!bookRepository.existsById(id)){
            throw new BookNotFoundException(id);
        }
        bookRepository.deleteById(id);

    }
}
