package com.goganesh.bookshop.model.service.impl;

import com.goganesh.bookshop.model.domain.Author;
import com.goganesh.bookshop.model.repository.JpaAuthorRepository;
import com.goganesh.bookshop.model.service.AuthorReadRepository;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class JpaAuthorReadRepository implements AuthorReadRepository {

    private final JpaAuthorRepository authorRepository;

    @Override
    public Optional<Author> findById(Integer id) {
        return authorRepository.findById(id);
    }

    @Override
    public List<Author> findAll() {
        return authorRepository.findAll();
    }

    @Override
    public Optional<Author> findBySlug(String slug) {
        return authorRepository.findBySlug(slug);
    }
}
