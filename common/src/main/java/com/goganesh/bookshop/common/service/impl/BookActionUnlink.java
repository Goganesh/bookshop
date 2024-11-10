package com.goganesh.bookshop.common.service.impl;

import com.goganesh.bookshop.common.service.BookAction;
import com.goganesh.bookshop.model.domain.Book;
import com.goganesh.bookshop.model.domain.Book2User;
import com.goganesh.bookshop.model.domain.Book2UserType;
import com.goganesh.bookshop.model.domain.User;
import com.goganesh.bookshop.model.repository.Book2UserRepository;
import com.goganesh.bookshop.model.repository.Book2UserTypeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Transactional
@Service
public class BookActionUnlink implements BookAction {

    private final Book2UserRepository book2UserRepository;
    private final Book2UserTypeRepository book2UserTypeRepository;

    @Override
    public void execute(User user, List<Book> books) {

        Book2UserType kept = book2UserTypeRepository.findByCode("KEPT").orElse(null);
        Book2UserType cart = book2UserTypeRepository.findByCode("CART").orElse(null);

        List<Book2User> keptBook2Users = books.stream()
                .map(book -> book2UserRepository.findByUserAndBook(user, book))
                .flatMap(Collection::stream)
                .filter(book2User -> book2User.getBook2UserType().equals(kept) || book2User.getBook2UserType().equals(cart))
                .collect(Collectors.toList());

        book2UserRepository.deleteAll(keptBook2Users);
    }
}
