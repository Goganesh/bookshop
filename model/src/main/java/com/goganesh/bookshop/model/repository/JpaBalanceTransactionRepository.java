package com.goganesh.bookshop.model.repository;

import com.goganesh.bookshop.model.domain.BalanceTransaction;
import com.goganesh.bookshop.model.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JpaBalanceTransactionRepository extends JpaRepository<BalanceTransaction, Integer> {
    List<BalanceTransaction> findByUser(User user);
    Page<BalanceTransaction> findByUser(User user, Pageable nextPage);
}
