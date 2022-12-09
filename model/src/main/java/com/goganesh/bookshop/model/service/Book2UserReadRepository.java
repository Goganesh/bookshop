package com.goganesh.bookshop.model.service;


import com.goganesh.bookshop.model.domain.Book;
import com.goganesh.bookshop.model.domain.Book2User;
import com.goganesh.bookshop.model.domain.Book2UserType;
import com.goganesh.bookshop.model.domain.User;

import java.util.List;

public interface Book2UserReadRepository {


    List<Book2User> findByBook(Book book);

    List<Book2User> findByUser(User user);

    List<Book2User> findByUserAndBook2UserType(User user, Book2UserType book2UserType);

    List<Book2User> findByUserAndBook(User user, Book book);

}
