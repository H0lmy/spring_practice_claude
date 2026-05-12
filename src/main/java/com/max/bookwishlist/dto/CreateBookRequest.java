package com.max.bookwishlist.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class CreateBookRequest {
    @NotBlank(message = "Title is required")
    @Size(max = 200, message = "Title must be at most 200 characters")
    private String title;
    @NotBlank(message = "Author is required")
    @Size(max = 100, message = "Author must be at most 100 characters")
    private String author;
    @NotNull(message = "Year is required")
    @Min(value = 1000, message = "Year must be at least 1000")
    @Max(value = 2100, message = "Year must be at most 2100")
    private Integer year;

}
