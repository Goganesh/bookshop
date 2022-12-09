package com.goganesh.bookshop.model.service;

import com.goganesh.bookshop.model.domain.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorReadRepository {

    Optional<Author> findById(Integer id);
    Optional<Author> findBySlug(String slug);
    List<Author> findAll();
}
