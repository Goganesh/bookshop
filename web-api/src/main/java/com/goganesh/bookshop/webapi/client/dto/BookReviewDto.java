package com.goganesh.bookshop.webapi.client.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Builder
@Getter
@Setter
public class BookReviewDto {
    @NotBlank(message = "The bookId is required.")
    private final String bookId;

    @NotBlank(message = "The text is required.")
    private final String text;
}
