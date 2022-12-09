package com.goganesh.bookshop.model.service.impl;

import com.goganesh.bookshop.model.domain.BalanceTransaction;
import com.goganesh.bookshop.model.domain.User;
import com.goganesh.bookshop.model.repository.JpaBalanceTransactionRepository;
import com.goganesh.bookshop.model.service.BalanceTransactionReadRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@AllArgsConstructor
public class JpaBalanceTransactionReadRepository implements BalanceTransactionReadRepository {

    private final JpaBalanceTransactionRepository jpaBalanceTransactionRepository;

    @Override
    public Page<BalanceTransaction> getPageByUser(User user, Integer offset, Integer limit, Sort sortOrder) {
        Pageable nextPage = PageRequest.of(offset, limit, sortOrder);
        return jpaBalanceTransactionRepository.findByUser(user, nextPage);
    }
}
