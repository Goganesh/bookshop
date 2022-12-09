package com.goganesh.bookshop.webapi.client.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class ResponseDto {
    private boolean result;
    private String error;
}
