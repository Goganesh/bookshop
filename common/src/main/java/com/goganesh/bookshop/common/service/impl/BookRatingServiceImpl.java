package com.goganesh.bookshop.common.service.impl;

import com.goganesh.bookshop.common.service.BookRatingService;
import com.goganesh.bookshop.model.domain.Book;
import com.goganesh.bookshop.model.domain.BookRating;
import com.goganesh.bookshop.model.domain.User;
import com.goganesh.bookshop.model.service.BookRatingReadRepository;
import com.goganesh.bookshop.model.service.BookRatingWriteRepository;
import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Transactional
public class BookRatingServiceImpl implements BookRatingService {

    private final BookRatingReadRepository bookRatingReadRepository;
    private final BookRatingWriteRepository bookRatingWriteRepository;

    @Override
    public int findRatingByBook(Book book) {
        List<BookRating> bookRatingList  = bookRatingReadRepository.findByBook(book);
        int rating = 0;
        if (bookRatingList.size() > 0) {
            rating = bookRatingList.stream()
                    .map(BookRating::getRating)
                    .reduce(0, Integer::sum) / bookRatingList.size();
        }

        return rating;
    }

    @Override
    public void save(BookRating bookRating) {
        bookRatingWriteRepository.save(bookRating);
    }

    @Override
    public Optional<BookRating> findByUserAndBook(User user, Book book) {
        return bookRatingReadRepository.findByUserAndBook(user, book);
    }
}
