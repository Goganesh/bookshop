package com.goganesh.bookshop.model.service.impl;

import com.goganesh.bookshop.model.domain.Genre;
import com.goganesh.bookshop.model.repository.JpaGenreRepository;
import com.goganesh.bookshop.model.service.GenreWriteRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class JpaGenreWriteRepository implements GenreWriteRepository {

    private final JpaGenreRepository jpaGenreRepository;

    @Override
    public void delete(Genre genre) {
        jpaGenreRepository.delete(genre);
    }

    @Override
    public Genre save(Genre genre) {
        return jpaGenreRepository.save(genre);
    }
}
