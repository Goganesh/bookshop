package com.goganesh.bookshop.common.service.impl;

import com.goganesh.bookshop.common.service.BookPopularityService;
import com.goganesh.bookshop.common.service.BookPopularityServiceConfig;
import com.goganesh.bookshop.model.domain.Book;
import com.goganesh.bookshop.model.domain.Book2User;
import com.goganesh.bookshop.model.service.Book2UserReadRepository;
import com.goganesh.bookshop.model.service.BookWriteRepository;
import lombok.AllArgsConstructor;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@AllArgsConstructor
public class BookPopularityServiceImpl implements BookPopularityService {

    private final BookWriteRepository bookWriteRepository;
    private final Book2UserReadRepository book2UserReadRepository;
    private final BookPopularityServiceConfig popularityServiceConfig;

    @Override
    @Transactional
    public void calculateBookPopularity(Book book) {
        Double bookPopularity = book2UserReadRepository.findByBook(book)
                .stream()
                .filter(book2User -> !book2User.getBook2UserType().getCode().equals("VIEWED")
                    || (book2User.getBook2UserType().getCode().equals("VIEWED")
                        && book2User.getTime().isAfter(LocalDateTime.now().minus(popularityServiceConfig.getViewedTimeLimitMinutes(), ChronoUnit.MINUTES))))
                .map(Book2User::getBook2UserType)
                .map(book2UserType -> {
                    switch (book2UserType.getCode()) {
                        case "PAID":
                            return popularityServiceConfig.getPaidRate();
                        case "CART":
                            return popularityServiceConfig.getCartRate();
                        case "KEPT":
                            return popularityServiceConfig.getKeptRate();
                        case "VIEWED":
                            return popularityServiceConfig.getViewedRate();
                        default:
                            return popularityServiceConfig.getDefaultRate();
                    }
                })
                .reduce(0.0d, Double::sum);

        book.setPopularity(bookPopularity);
        bookWriteRepository.save(book);
    }

    @Override
    public void calculateBooksPopularity(List<Book> books) {
        books.forEach(this::calculateBookPopularity);
    }
}
