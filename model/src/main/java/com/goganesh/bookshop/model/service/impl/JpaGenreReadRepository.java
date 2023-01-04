package com.goganesh.bookshop.model.service.impl;

import com.goganesh.bookshop.model.domain.Genre;
import com.goganesh.bookshop.model.repository.JpaGenreRepository;
import com.goganesh.bookshop.model.service.GenreReadRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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

    @Override
    public Page<Genre> findAll(Pageable pageable) {
        return jpaGenreRepository.findAll(pageable);
    }

    @Override
    public Optional<Genre> findById(Integer id) {
        return jpaGenreRepository.findById(id);
    }
}
