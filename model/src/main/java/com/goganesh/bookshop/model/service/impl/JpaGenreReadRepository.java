package com.goganesh.bookshop.model.service.impl;

import com.goganesh.bookshop.model.domain.Genre;
import com.goganesh.bookshop.model.repository.JpaGenreRepository;
import com.goganesh.bookshop.model.service.GenreReadRepository;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class JpaGenreReadRepository implements GenreReadRepository {

    private final JpaGenreRepository jpaGenreRepository;

    @Override
    public List<Genre> findRootGenres() {
        return jpaGenreRepository.findByParentIsNull();
    }

    @Override
    public Optional<Genre> findBySlug(String slug) {
        return jpaGenreRepository.findBySlug(slug);
    }
}
