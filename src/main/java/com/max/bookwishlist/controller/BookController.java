package com.max.bookwishlist.controller;

import com.max.bookwishlist.dto.CreateBookRequest;
import com.max.bookwishlist.dto.UpdateBookRequest;
import com.max.bookwishlist.model.Book;
import com.max.bookwishlist.service.BookService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
public class BookController {
    private final BookService bookService;
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }
    @PostMapping("/books")
    public Book createBook(@RequestBody CreateBookRequest request){
        return bookService.createBook(request);

    }
    @GetMapping("/books")
    public List<Book> getBooks() {

         return bookService.getAllBooks();
    }

    @GetMapping("/books/{id}")
    public Book getBookById(@PathVariable Long id) {
        return bookService.getBookById(id);
    }

    @PutMapping("/books/{id}")
    public Book updateBookById(@PathVariable Long id, @RequestBody UpdateBookRequest request){
        return bookService.updateBook(id,request);
    }

    @DeleteMapping("/books/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id){
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();

    }




}
