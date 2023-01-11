package com.goganesh.bookshop.model.service;

import com.goganesh.bookshop.model.domain.Genre;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface GenreReadRepository {

    List<Genre> findRootGenres();

    List<Genre> findChildrenGenresByParent(Genre genre);

    Page<Genre> findAll(Pageable pageable);

    Optional<Genre> findBySlug(String slug);

    Optional<Genre> findById(Integer id);
}
