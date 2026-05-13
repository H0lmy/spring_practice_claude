package com.max.bookwishlist.dto;

import com.max.bookwishlist.model.Wishlist;

public record WishlistResponse(
        Long id,
        String name,
        Long userId,
        String username
) {
    public static WishlistResponse from(Wishlist wishlist) {
        return new WishlistResponse(
                wishlist.getId(),
                wishlist.getName(),
                wishlist.getUser().getId(),
                wishlist.getUser().getUsername()
        );
    }
}
