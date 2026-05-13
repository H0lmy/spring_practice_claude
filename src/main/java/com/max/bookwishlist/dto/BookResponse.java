package com.max.bookwishlist.dto;

import com.max.bookwishlist.model.Book;

public record BookResponse(
        Long id,
        String title,
        String author,
        Integer year,
        Long wishlistId,
        String wishlistName
) {
    public static BookResponse from(Book book) {
        return new BookResponse(
                book.getId(),
                book.getTitle(),
                book.getAuthor(),
                book.getYear(),
                book.getWishlist().getId(),
                book.getWishlist().getName()
        );
    }
}
