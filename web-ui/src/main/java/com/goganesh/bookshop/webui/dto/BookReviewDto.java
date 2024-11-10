package com.goganesh.bookshop.webui.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookReviewDto {
    private int id;
    private String reviewer;
    private String text;
    private String reviewTime;
    private long positiveReview;
    private long negativeReview;
}
