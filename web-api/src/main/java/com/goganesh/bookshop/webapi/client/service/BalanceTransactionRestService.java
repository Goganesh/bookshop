package com.goganesh.bookshop.webapi.client.service;

import com.goganesh.bookshop.model.domain.BalanceTransaction;
import com.goganesh.bookshop.model.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

public interface BalanceTransactionRestService {
    Page<BalanceTransaction> getPageByUser(User user, Integer offset, Integer limit, Sort sortOrder);
}
