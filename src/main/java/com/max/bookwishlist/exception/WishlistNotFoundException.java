package com.max.bookwishlist.exception;

public class WishlistNotFoundException extends RuntimeException {
    public WishlistNotFoundException(Long id) {
        super("Wishlist with id " + id + " not found");
    }
}
