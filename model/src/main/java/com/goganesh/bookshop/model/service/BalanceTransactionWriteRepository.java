package com.goganesh.bookshop.model.service;

import com.goganesh.bookshop.model.domain.BalanceTransaction;

public interface BalanceTransactionWriteRepository {

    void save(BalanceTransaction balanceTransaction);
}
