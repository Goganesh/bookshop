package com.goganesh.bookshop.model.service.impl;

import com.goganesh.bookshop.model.domain.Book;
import com.goganesh.bookshop.model.domain.Book2User;
import com.goganesh.bookshop.model.domain.Book2UserType;
import com.goganesh.bookshop.model.domain.User;
import com.goganesh.bookshop.model.repository.JpaBook2UserRepository;
import com.goganesh.bookshop.model.service.Book2UserReadRepository;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class JpaBook2UserReadRepository implements Book2UserReadRepository {

    private final JpaBook2UserRepository jpaBook2UserRepository;

    @Override
    public List<Book2User> findByBook(Book book) {
        return jpaBook2UserRepository.findByBook(book);
    }

    @Override
    public List<Book2User> findByUser(User user) {
        return jpaBook2UserRepository.findByUser(user);
    }


    @Override
    public List<Book2User> findByUserAndBook2UserType(User user, Book2UserType book2UserType) {
        return jpaBook2UserRepository.findByUserAndBook2UserType(user, book2UserType);
    }

    @Override
    public List<Book2User> findByUserAndBook(User user, Book book) {
        return jpaBook2UserRepository.findByUserAndBook(user, book);
    }
}
