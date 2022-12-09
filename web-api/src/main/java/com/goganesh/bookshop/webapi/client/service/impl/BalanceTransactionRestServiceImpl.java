package com.goganesh.bookshop.webapi.client.service.impl;

import com.goganesh.bookshop.model.domain.BalanceTransaction;
import com.goganesh.bookshop.model.domain.User;
import com.goganesh.bookshop.model.service.BalanceTransactionReadRepository;
import com.goganesh.bookshop.webapi.client.service.BalanceTransactionRestService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

@AllArgsConstructor
public class BalanceTransactionRestServiceImpl implements BalanceTransactionRestService {

    private final BalanceTransactionReadRepository balanceTransactionReadRepository;

    @Override
    public Page<BalanceTransaction> getPageByUser(User user, Integer offset, Integer limit, Sort sortOrder) {
        return balanceTransactionReadRepository.getPageByUser(user, offset, limit, sortOrder);
    }
}
