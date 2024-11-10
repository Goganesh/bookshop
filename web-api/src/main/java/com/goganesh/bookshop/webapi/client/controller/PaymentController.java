package com.goganesh.bookshop.webapi.client.controller;

import com.goganesh.bookshop.model.domain.BalanceTransaction;
import com.goganesh.bookshop.model.domain.User;
import com.goganesh.bookshop.model.repository.BalanceTransactionRepository;
import com.goganesh.bookshop.webapi.client.dto.PaymentDto;
import com.goganesh.bookshop.webapi.client.dto.ResponseDto;
import com.goganesh.bookshop.webapi.client.dto.TransactionDto;
import com.goganesh.bookshop.webapi.client.dto.TransactionsDto;
import com.goganesh.bookshop.webapi.client.service.BalanceTransactionRestService;
import com.goganesh.bookshop.webapi.client.mapper.BalanceTransactionMapper;
import com.goganesh.security.service.UserRegisterService;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.TimeZone;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1")
@PreAuthorize("hasRole('USER')")
public class PaymentController {

    private final UserRegisterService userRegisterService;
    private final BalanceTransactionRestService balanceTransactionRestService;
    private final BalanceTransactionRepository balanceTransactionRepository;
    private final BalanceTransactionMapper transactionMapper;

    public PaymentController(UserRegisterService userRegisterService,
                             BalanceTransactionRestService balanceTransactionRestService,
                             BalanceTransactionRepository balanceTransactionRepository,
                             BalanceTransactionMapper transactionMapper) {
        this.userRegisterService = userRegisterService;
        this.balanceTransactionRestService = balanceTransactionRestService;
        this.balanceTransactionRepository = balanceTransactionRepository;
        this.transactionMapper = transactionMapper;
    }

    @GetMapping("/transactions")
    public TransactionsDto getTransactions(@RequestParam("offset") Integer offset,
                                           @RequestParam("limit") Integer limit,
                                           @RequestParam("sort") String sort) {
        User user = userRegisterService.getCurrentUser();
        TransactionsDto transactionsDto = new TransactionsDto();
        Sort sortOrder = Sort.by("time").descending();

        if (sort.equals("asc")) {
            sortOrder = Sort.by("time").ascending();
        }

        List<TransactionDto> transactionDtos = balanceTransactionRestService.getPageByUser(user, offset, limit, sortOrder)
                .stream()
                .map(transactionMapper::toDto)
                .collect(Collectors.toList());

        transactionsDto.setTransactions(transactionDtos);
        transactionsDto.setCount(transactionDtos.size());

        return transactionsDto;
    }

    @PostMapping("/payment")
    public ResponseDto makePayment(@Valid @RequestBody PaymentDto paymentDto) {
        User user = userRegisterService.getCurrentUser();
        int money = Integer.parseInt(paymentDto.getSum());
        long time = paymentDto.getTime();

        BalanceTransaction balanceTransaction = BalanceTransaction.builder()
                .user(user)
                .description("ADD_MONEY_DESCRIPTION")
                .time(LocalDateTime.ofInstant(Instant.ofEpochMilli(time),
                        TimeZone.getDefault().toZoneId()))
                .value(money)
                .build();

        balanceTransactionRepository.save(balanceTransaction);

        return ResponseDto.builder()
                .result(true)
                .build();
    }
}
