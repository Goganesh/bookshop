package com.goganesh.bookshop.common.service.impl;

import com.goganesh.bookshop.common.service.BookPopularityService;
import com.goganesh.bookshop.common.service.BookPopularityServiceConfig;
import com.goganesh.bookshop.model.domain.Book;
import com.goganesh.bookshop.model.domain.Book2User;
import com.goganesh.bookshop.model.repository.Book2UserRepository;
import com.goganesh.bookshop.model.repository.BookRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@AllArgsConstructor
@Transactional
@Service
public class BookPopularityServiceImpl implements BookPopularityService {

    private final BookRepository bookRepository;
    private final Book2UserRepository book2UserRepository;
    private final BookPopularityServiceConfig popularityServiceConfig;

    @Override
    @Transactional
    public void calculateBookPopularity(Book book) {
        Double bookPopularity = book2UserRepository.findByBook(book)
                .stream()
                .filter(book2User -> !book2User.getBook2UserType().getCode().equals("VIEWED")
                    || (book2User.getBook2UserType().getCode().equals("VIEWED")
                        && book2User.getTime().isAfter(LocalDateTime.now().minus(popularityServiceConfig.getViewedTimeLimitMinutes(), ChronoUnit.MINUTES))))
                .map(Book2User::getBook2UserType)
                .map(book2UserType -> switch (book2UserType.getCode()) {
                    case "PAID" -> popularityServiceConfig.getPaidRate();
                    case "CART" -> popularityServiceConfig.getCartRate();
                    case "KEPT" -> popularityServiceConfig.getKeptRate();
                    case "VIEWED" -> popularityServiceConfig.getViewedRate();
                    default -> popularityServiceConfig.getDefaultRate();
                })
                .reduce(0.0d, Double::sum);

        book.setPopularity(bookPopularity);
        bookRepository.save(book);
    }

    @Override
    public void calculateBooksPopularity(List<Book> books) {
        books.forEach(this::calculateBookPopularity);
    }
}
