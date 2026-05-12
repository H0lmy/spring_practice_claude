package com.max.bookwishlist.dto;

import lombok.Data;

@Data
public class UpdateBookRequest {
    private String title;
    private String author;
    private Integer year;
}
