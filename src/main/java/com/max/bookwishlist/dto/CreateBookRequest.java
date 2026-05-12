package com.max.bookwishlist.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CreateBookRequest {
    private String title;
    private String author;
    private Integer year;
    public CreateBookRequest() {
    }

}
