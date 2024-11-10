package com.goganesh.bookshop.webapi.client.service.impl;

import com.goganesh.bookshop.model.domain.BalanceTransaction;
import com.goganesh.bookshop.model.domain.User;
import com.goganesh.bookshop.model.repository.BalanceTransactionRepository;
import com.goganesh.bookshop.webapi.client.service.BalanceTransactionRestService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class BalanceTransactionRestServiceImpl implements BalanceTransactionRestService {

    private final BalanceTransactionRepository balanceTransactionRepository;

    public BalanceTransactionRestServiceImpl(BalanceTransactionRepository balanceTransactionRepository) {
        this.balanceTransactionRepository = balanceTransactionRepository;
    }

    @Override
    public Page<BalanceTransaction> getPageByUser(User user, Integer offset, Integer limit, Sort sortOrder) {
        Pageable nextPage = PageRequest.of(offset, limit, sortOrder);
        return balanceTransactionRepository.findByUser(user, nextPage);
    }
}
