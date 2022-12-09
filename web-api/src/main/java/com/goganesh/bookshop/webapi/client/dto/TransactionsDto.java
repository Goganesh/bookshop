package com.goganesh.bookshop.webapi.client.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TransactionsDto {
    private int count;
    private List<TransactionDto> transactions;
}
