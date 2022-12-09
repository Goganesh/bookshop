package com.goganesh.bookshop.model.service;


import com.goganesh.bookshop.model.domain.Book2User;

import java.util.List;

public interface Book2UserWriteRepository {

    Book2User save(Book2User book2User);

    void delete(Book2User book2User);

    void saveAll(List<Book2User> book2UsersNew);

    void deleteAll(List<Book2User> keptBook2Users);
}
