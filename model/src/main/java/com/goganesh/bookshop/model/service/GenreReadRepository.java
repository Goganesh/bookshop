package com.goganesh.bookshop.model.service;

import com.goganesh.bookshop.model.domain.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreReadRepository {

    List<Genre> findRootGenres();

    Optional<Genre> findBySlug(String slug);
}
