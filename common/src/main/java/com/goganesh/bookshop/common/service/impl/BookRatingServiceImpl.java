package com.goganesh.bookshop.common.service.impl;

import com.goganesh.bookshop.common.service.BookRatingService;
import com.goganesh.bookshop.model.domain.Book;
import com.goganesh.bookshop.model.domain.BookRating;
import com.goganesh.bookshop.model.domain.User;
import com.goganesh.bookshop.model.repository.BookRatingRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Transactional
@Service
public class BookRatingServiceImpl implements BookRatingService {

    private final BookRatingRepository bookRatingRepository;

    @Override
    public int findRatingByBook(Book book) {
        List<BookRating> bookRatingList  = bookRatingRepository.findByBook(book);
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
        bookRatingRepository.save(bookRating);
    }

    @Override
    public Optional<BookRating> findByUserAndBook(User user, Book book) {
        return bookRatingRepository.findByUserAndBook(user, book);
    }
}
