package com.max.bookwishlist.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(Long id) {
        super("User with this id " + id + " not found");
    }
}
