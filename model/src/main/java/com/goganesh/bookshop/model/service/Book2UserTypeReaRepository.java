package com.goganesh.bookshop.model.service;

import com.goganesh.bookshop.model.domain.Book2UserType;

import java.util.Optional;

public interface Book2UserTypeReaRepository {

    Optional<Book2UserType> findByCode(String code);
}
