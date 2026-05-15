package com.max.bookwishlist.service;

import com.max.bookwishlist.dto.CreateBookRequest;
import com.max.bookwishlist.dto.UpdateBookRequest;
import com.max.bookwishlist.exception.BookNotFoundException;
import com.max.bookwishlist.model.Book;

import com.max.bookwishlist.model.Wishlist;
import com.max.bookwishlist.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;
    private final WishlistService wishlistService;

    public Page<Book> getAllBooks(Pageable pageable) {
        return  bookRepository.findAll(pageable);
    }


    public Book getBookById(Long id) {
        return bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException(id));
    }

    public Page<Book> searchBooks(String keyword, Pageable pageable) {
        return bookRepository.findByTitleContainingIgnoreCase(keyword,pageable);
    }

    public Book createBook(CreateBookRequest request,Long id) {
        Wishlist wishlist = wishlistService.getWishlistOwnedBy(request.getWishlistId(), id);
        Book book = new Book();
        book.setAuthor(request.getAuthor());
        book.setYear(request.getYear());
        book.setTitle(request.getTitle());
        book.setWishlist(wishlist);
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

    public Book getBookOwnedBy(Long bookId, Long userId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new BookNotFoundException(bookId));
        if (!book.getWishlist().getUser().getId().equals(userId)) {
            throw new AccessDeniedException("You don't have permission to access this book");
        }
        return book;
    }
}
