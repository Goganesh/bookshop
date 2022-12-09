package com.goganesh.bookshop.model.service.impl;

import com.goganesh.bookshop.model.domain.BalanceTransaction;
import com.goganesh.bookshop.model.repository.JpaBalanceTransactionRepository;
import com.goganesh.bookshop.model.service.BalanceTransactionWriteRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class JpaBalanceTransactionWriteRepository implements BalanceTransactionWriteRepository {

    private final JpaBalanceTransactionRepository jpaBalanceTransactionRepository;

    @Override
    public void save(BalanceTransaction balanceTransaction) {
        jpaBalanceTransactionRepository.save(balanceTransaction);
    }
}
