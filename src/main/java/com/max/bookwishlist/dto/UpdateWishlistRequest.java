package com.max.bookwishlist.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdateWishlistRequest {
    @NotBlank(message = "name is required")
    @Size(max = 150,message = "Name must be at most 150 characters")
    private String name;
}
