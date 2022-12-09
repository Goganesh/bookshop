package com.goganesh.bookshop.webapi.client.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransactionDto {
    private long time;
    private String value;
    private String description;
}
