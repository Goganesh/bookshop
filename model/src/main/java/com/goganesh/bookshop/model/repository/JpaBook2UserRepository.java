package com.goganesh.bookshop.model.repository;

import com.goganesh.bookshop.model.domain.Book;
import com.goganesh.bookshop.model.domain.Book2User;
import com.goganesh.bookshop.model.domain.Book2UserType;
import com.goganesh.bookshop.model.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JpaBook2UserRepository extends JpaRepository<Book2User, Integer> {
    List<Book2User> findByBook(Book book);

    List<Book2User> findByUser(User user);

    List<Book2User> findByUserAndBook2UserType(User user, Book2UserType book2UserType);

    List<Book2User> findByUserAndBook(User user, Book book);
}