package com.goganesh.bookshop.model.repository;

import com.goganesh.bookshop.model.domain.Book2UserType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaBook2UserTypeRepository extends JpaRepository<Book2UserType, Integer> {
    Optional<Book2UserType> findByCode(String code);
}
