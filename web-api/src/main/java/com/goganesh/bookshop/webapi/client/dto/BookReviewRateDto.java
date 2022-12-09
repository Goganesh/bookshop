package com.goganesh.bookshop.webapi.client.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Builder
@Getter
@Setter
public class BookReviewRateDto {
    @NotNull(message = "The reviewId of bags is required.")
    private int reviewId;
    @NotNull(message = "The value of bags is required.")
    private int value;
}
