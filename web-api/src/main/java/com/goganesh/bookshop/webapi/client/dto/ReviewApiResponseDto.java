package com.goganesh.bookshop.webapi.client.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReviewApiResponseDto {
    private Integer id;
    private Integer bookId;
    private Integer userId;
    private LocalDateTime time;
    private String text;

}
