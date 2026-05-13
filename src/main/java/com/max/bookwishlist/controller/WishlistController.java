package com.max.bookwishlist.controller;

import com.max.bookwishlist.dto.CreateWishlistRequest;
import com.max.bookwishlist.dto.UpdateWishlistRequest;
import com.max.bookwishlist.dto.WishlistResponse;
import com.max.bookwishlist.service.WishlistService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class WishlistController {
    private final WishlistService wishlistService;

    @PostMapping("/wishlists")
    public WishlistResponse createWishlist(@Valid @RequestBody CreateWishlistRequest createWishlistRequest){
        return WishlistResponse.from(wishlistService.createWishlist(createWishlistRequest));
    }

    @GetMapping("/wishlists")
    public List<WishlistResponse> getAllWishlist(){
        return wishlistService.getAllWishlists().stream().map(WishlistResponse::from).toList();
    }

    @GetMapping("/wishlists/{id}")
    public WishlistResponse getWishlistById(@PathVariable Long id){
        return WishlistResponse.from(wishlistService.getWishlistById(id));
    }

    @PutMapping("/wishlists/{id}")
    public WishlistResponse updateWishlist(@PathVariable Long id, @Valid @RequestBody UpdateWishlistRequest updateWishlistRequest){
        return WishlistResponse.from(wishlistService.updateWishlist(id,updateWishlistRequest));
    }

    @DeleteMapping("/wishlists/{id}")
    public ResponseEntity<Void> deleteWishlist(@PathVariable Long id){
        wishlistService.deleteWishlist(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/users/{userId}/wishlists")
    public List<WishlistResponse> getWishlistsByUserId(@PathVariable Long userId){
        return wishlistService.getWishlistsByUserId(userId).stream().map(WishlistResponse::from).toList();

    }



}
