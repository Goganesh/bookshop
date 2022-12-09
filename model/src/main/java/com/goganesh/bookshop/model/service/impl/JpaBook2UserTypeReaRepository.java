package com.goganesh.bookshop.model.service.impl;

import com.goganesh.bookshop.model.domain.Book2UserType;
import com.goganesh.bookshop.model.repository.JpaBook2UserTypeRepository;
import com.goganesh.bookshop.model.service.Book2UserTypeReaRepository;
import lombok.AllArgsConstructor;

import java.util.Optional;

@AllArgsConstructor
public class JpaBook2UserTypeReaRepository implements Book2UserTypeReaRepository {

    private final JpaBook2UserTypeRepository jpaBook2UserTypeRepository;

    @Override
    public Optional<Book2UserType> findByCode(String code) {
        return jpaBook2UserTypeRepository.findByCode(code);
    }
}
