package com.goganesh.bookshop.webapi.client.service;

import com.goganesh.bookshop.model.domain.Genre;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface GenreRestService {

    Page<Genre> findAll(Pageable pageable);

    Optional<Genre> findById(Integer id);

    void delete(Genre genre);

    Genre post(Genre genre);
}
