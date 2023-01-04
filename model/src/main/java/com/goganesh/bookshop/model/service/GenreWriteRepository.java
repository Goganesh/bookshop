package com.goganesh.bookshop.model.service;

import com.goganesh.bookshop.model.domain.Genre;

public interface GenreWriteRepository {

    void delete(Genre genre);

    Genre save(Genre genre);
}
