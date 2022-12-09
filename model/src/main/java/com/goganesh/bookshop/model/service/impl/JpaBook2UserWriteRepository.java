package com.goganesh.bookshop.model.service.impl;

import com.goganesh.bookshop.model.domain.Book2User;
import com.goganesh.bookshop.model.repository.JpaBook2UserRepository;
import com.goganesh.bookshop.model.service.Book2UserWriteRepository;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class JpaBook2UserWriteRepository implements Book2UserWriteRepository {

    private final JpaBook2UserRepository jpaBook2UserRepository;

    @Override
    public Book2User save(Book2User book2User) {
        return jpaBook2UserRepository.save(book2User);
    }

    @Override
    public void delete(Book2User book2User) {
        jpaBook2UserRepository.delete(book2User);
    }

    @Override
    public void saveAll(List<Book2User> book2UsersNew) {
        jpaBook2UserRepository.saveAll(book2UsersNew);
    }

    @Override
    public void deleteAll(List<Book2User> keptBook2Users) {
        jpaBook2UserRepository.deleteAll(keptBook2Users);
    }
}
