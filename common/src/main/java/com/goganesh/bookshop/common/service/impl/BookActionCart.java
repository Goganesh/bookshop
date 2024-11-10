package com.goganesh.bookshop.common.service.impl;

import com.goganesh.bookshop.common.service.BookAction;
import com.goganesh.bookshop.model.domain.Book;
import com.goganesh.bookshop.model.domain.Book2User;
import com.goganesh.bookshop.model.domain.User;
import com.goganesh.bookshop.model.repository.Book2UserRepository;
import com.goganesh.bookshop.model.repository.Book2UserTypeRepository;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Transactional
@Service
public class BookActionCart implements BookAction {

    private final Book2UserRepository book2UserRepository;
    private final Book2UserTypeRepository book2UserTypeRepository;

    public BookActionCart(Book2UserRepository book2UserRepository, Book2UserTypeRepository book2UserTypeRepository) {
        this.book2UserRepository = book2UserRepository;
        this.book2UserTypeRepository = book2UserTypeRepository;
    }

    @Override
    public void execute(User user, List<Book> books) {

        List<Book2User> book2UsersKept = new ArrayList<>();

        for (Book book : books) {
             List<Book2User> book2Users = book2UserRepository.findByUserAndBook(user, book);
             if (book2Users.isEmpty()) {
                 book2UsersKept.add(createNewKeptBook2User(user, book));
             } else {
                 //TODO what if i found: same type, or different type
             }
        }

        book2UserRepository.saveAll(book2UsersKept);
    }

    private Book2User createNewKeptBook2User(User user, Book book) {
        Book2User kept = new Book2User();
        kept.setBook(book);
        kept.setTime(LocalDateTime.now());
        kept.setBook2UserType(book2UserTypeRepository.findByCode("CART").get());
        kept.setEnabled(true);
        kept.setUser(user);
        return kept;
    }
}
