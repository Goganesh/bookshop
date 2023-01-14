package com.goganesh.bookshop.common.service.impl;

import com.goganesh.bookshop.common.service.BookAction;
import com.goganesh.bookshop.model.domain.Book;
import com.goganesh.bookshop.model.domain.Book2User;
import com.goganesh.bookshop.model.domain.Book2UserType;
import com.goganesh.bookshop.model.domain.User;
import com.goganesh.bookshop.model.service.Book2UserReadRepository;
import com.goganesh.bookshop.model.service.Book2UserTypeReaRepository;
import com.goganesh.bookshop.model.service.Book2UserWriteRepository;
import lombok.AllArgsConstructor;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
public class BookActionUnlink implements BookAction {

    private final Book2UserReadRepository book2UserReadRepository;
    private final Book2UserWriteRepository book2UserWriteRepository;
    private final Book2UserTypeReaRepository book2UserTypeReaRepository;

    @Override
    public void execute(User user, List<Book> books) {

        Book2UserType kept = book2UserTypeReaRepository.findByCode("KEPT").orElse(null);
        Book2UserType cart = book2UserTypeReaRepository.findByCode("CART").orElse(null);

        List<Book2User> keptBook2Users = books.stream()
                .map(book -> book2UserReadRepository.findByUserAndBook(user, book))
                .flatMap(Collection::stream)
                .filter(book2User -> book2User.getBook2UserType().equals(kept) || book2User.getBook2UserType().equals(cart))
                .collect(Collectors.toList());

        book2UserWriteRepository.deleteAll(keptBook2Users);
    }
}
