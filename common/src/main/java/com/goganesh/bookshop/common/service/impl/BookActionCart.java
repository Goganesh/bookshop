package com.goganesh.bookshop.common.service.impl;

import com.goganesh.bookshop.common.service.BookAction;
import com.goganesh.bookshop.model.domain.Book;
import com.goganesh.bookshop.model.domain.Book2User;
import com.goganesh.bookshop.model.domain.User;
import com.goganesh.bookshop.model.service.Book2UserReadRepository;
import com.goganesh.bookshop.model.service.Book2UserTypeReaRepository;
import com.goganesh.bookshop.model.service.Book2UserWriteRepository;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class BookActionCart implements BookAction {

    private final Book2UserReadRepository book2UserReadRepository;
    private final Book2UserWriteRepository book2UserWriteRepository;
    private final Book2UserTypeReaRepository book2UserTypeReaRepository;

    @Override
    public void execute(User user, List<Book> books) {

        List<Book2User> book2UsersKept = new ArrayList<>();

        for (Book book : books) {
             List<Book2User> book2Users = book2UserReadRepository.findByUserAndBook(user, book);
             if (book2Users.isEmpty()) {
                 book2UsersKept.add(createNewKeptBook2User(user, book));
             } else {
                 //todo what if i found: same type, or different type
             }
        }

        book2UserWriteRepository.saveAll(book2UsersKept);
    }

    private Book2User createNewKeptBook2User(User user, Book book) {
        Book2User kept = new Book2User();
        kept.setBook(book);
        kept.setTime(LocalDateTime.now());
        kept.setBook2UserType(book2UserTypeReaRepository.findByCode("CART").get());
        kept.setEnabled(true);
        kept.setUser(user);
        return kept;
    }
}
