package com.max.bookwishlist.controller;

import com.max.bookwishlist.dto.BookResponse;
import com.max.bookwishlist.dto.CreateBookRequest;
import com.max.bookwishlist.dto.PageResponse;
import com.max.bookwishlist.dto.UpdateBookRequest;
import com.max.bookwishlist.model.Book;
import com.max.bookwishlist.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;

    @PostMapping("/books")
    public BookResponse createBook(@Valid @RequestBody CreateBookRequest request){
        return BookResponse.from(bookService.createBook(request)) ;

    }
    @GetMapping("/books")
    public PageResponse<BookResponse> getBooks(@RequestParam(required = false) String keyword, Pageable pageable) {
        Page<Book> page  = (keyword!=null && !keyword.isBlank()) ? bookService.searchBooks(keyword,pageable):
                bookService.getAllBooks(pageable);
        Page<BookResponse> bookResponsePage = page.map(BookResponse::from);
        return PageResponse.from(bookResponsePage);
    }

    @GetMapping("/books/{id}")
    public BookResponse getBookById(@PathVariable Long id) {
        return BookResponse.from(bookService.getBookById(id)) ;
    }

    @PutMapping("/books/{id}")
    public BookResponse updateBookById(@PathVariable Long id,@Valid @RequestBody UpdateBookRequest request){
        return BookResponse.from(bookService.updateBook(id,request)) ;
    }

    @DeleteMapping("/books/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id){
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();

    }







}
