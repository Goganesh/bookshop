package com.goganesh.bookshop.webapi.client.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Getter
@Setter
@Builder
public class BookRateDto {
    @NotBlank(message = "The bookId is required.")
    private final String bookId;

    @NotNull(message = "The value of bags is required.")
    @Range(min = 1, max = 5, message = "The number of bags must be greater than 0 and less than 6.")
    private final int value;
}
