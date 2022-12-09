package com.goganesh.bookshop.webapi.client.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class PaymentDto {

    @NotBlank(message = "The sum is required.")
    private String sum;
    @NotNull(message = "The time is required.")
    private long time;

}
