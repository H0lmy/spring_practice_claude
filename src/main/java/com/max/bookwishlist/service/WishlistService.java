package com.max.bookwishlist.service;

import com.max.bookwishlist.dto.CreateWishlistRequest;
import com.max.bookwishlist.dto.UpdateWishlistRequest;
import com.max.bookwishlist.exception.WishlistNotFoundException;
import com.max.bookwishlist.model.User;
import com.max.bookwishlist.model.Wishlist;
import com.max.bookwishlist.repository.WishlistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WishlistService {
    private final WishlistRepository wishlistRepository;
    private final UserService userService;

    public List<Wishlist> getAllWishlists() {
        return wishlistRepository.findAll();
    }

    public Wishlist getWishlistById(Long id) {
        return wishlistRepository.findById(id).orElseThrow(() -> new WishlistNotFoundException(id));
    }

    public Wishlist createWishlist(CreateWishlistRequest createWishlistRequest) {
        User user = userService.getUserById(createWishlistRequest.getUserId());
        Wishlist wishlist = new Wishlist();
        wishlist.setName(createWishlistRequest.getName());
        wishlist.setUser(user);
        Wishlist savedWishlist = wishlistRepository.save(wishlist);
        return savedWishlist;

    }

    public Wishlist updateWishlist(Long id, UpdateWishlistRequest updateWishlistRequest) {
        Wishlist wishlistToUpdate = getWishlistById(id);
        wishlistToUpdate.setName(updateWishlistRequest.getName());
        wishlistRepository.save(wishlistToUpdate);
        return wishlistToUpdate;
    }

    public void deleteWishlist(Long id) {
        if (!wishlistRepository.existsById(id)) {
            throw new WishlistNotFoundException(id);
        }
        wishlistRepository.deleteById(id);
    }

    public List<Wishlist> getWishlistsByUserId(Long userId) {
        userService.getUserById(userId);
        return wishlistRepository.findByUserId(userId);
    }


}
