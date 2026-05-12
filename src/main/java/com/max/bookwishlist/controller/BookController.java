package com.max.bookwishlist.controller;

import com.max.bookwishlist.dto.CreateBookRequest;
import com.max.bookwishlist.dto.PageResponse;
import com.max.bookwishlist.dto.UpdateBookRequest;
import com.max.bookwishlist.model.Book;
import com.max.bookwishlist.service.BookService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
public class BookController {
    private final BookService bookService;
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }
    @PostMapping("/books")
    public Book createBook(@Valid @RequestBody CreateBookRequest request){
        return bookService.createBook(request);

    }
    @GetMapping("/books")
    public PageResponse<Book> getBooks(@RequestParam(required = false) String keyword, Pageable pageable) {
        Page<Book> page  = (keyword!=null && !keyword.isBlank()) ? bookService.searchBooks(keyword,pageable):
                bookService.getAllBooks(pageable);
        return PageResponse.from(page);
    }

    @GetMapping("/books/{id}")
    public Book getBookById(@PathVariable Long id) {
        return bookService.getBookById(id);
    }

    @PutMapping("/books/{id}")
    public Book updateBookById(@PathVariable Long id,@Valid @RequestBody UpdateBookRequest request){
        return bookService.updateBook(id,request);
    }

    @DeleteMapping("/books/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id){
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();

    }







}
