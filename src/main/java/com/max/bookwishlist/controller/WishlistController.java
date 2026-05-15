package com.max.bookwishlist.controller;

import com.max.bookwishlist.dto.CreateWishlistRequest;
import com.max.bookwishlist.dto.UpdateWishlistRequest;
import com.max.bookwishlist.dto.WishlistResponse;
import com.max.bookwishlist.security.UserPrincipal;
import com.max.bookwishlist.service.WishlistService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class WishlistController {
    private final WishlistService wishlistService;
    @PreAuthorize("#createWishlistRequest.userId==authentication.principal.id")
    @PostMapping("/wishlists")
    public WishlistResponse createWishlist(@Valid @RequestBody CreateWishlistRequest createWishlistRequest){
        return WishlistResponse.from(wishlistService.createWishlist(createWishlistRequest));
    }

    @GetMapping("/wishlists")
    public List<WishlistResponse> getAllWishlist(){
        return wishlistService.getAllWishlists().stream().map(WishlistResponse::from).toList();
    }

    @GetMapping("/wishlists/{id}")
    public WishlistResponse getWishlistById(@PathVariable Long id, @AuthenticationPrincipal UserPrincipal principal){
        return WishlistResponse.from(wishlistService.getWishlistOwnedBy(id, principal.getId()));
    }

    @PutMapping("/wishlists/{id}")
    public WishlistResponse updateWishlist(@PathVariable Long id, @Valid @RequestBody UpdateWishlistRequest updateWishlistRequest,@AuthenticationPrincipal UserPrincipal principal){
        wishlistService.getWishlistOwnedBy(id, principal.getId());
        return WishlistResponse.from(wishlistService.updateWishlist(id,updateWishlistRequest));
    }

    @DeleteMapping("/wishlists/{id}")
    public ResponseEntity<Void> deleteWishlist(@PathVariable Long id,@AuthenticationPrincipal UserPrincipal principal){
        wishlistService.getWishlistOwnedBy(id,principal.getId());
        wishlistService.deleteWishlist(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/users/{userId}/wishlists")
    @PreAuthorize("#userId == authentication.principal.id")
    public List<WishlistResponse> getWishlistsByUserId(@PathVariable Long userId){
        return wishlistService.getWishlistsByUserId(userId).stream().map(WishlistResponse::from).toList();

    }



}
