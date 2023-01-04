package com.goganesh.bookshop.webapi.client.service.impl;

import com.goganesh.bookshop.model.domain.Genre;
import com.goganesh.bookshop.model.service.GenreReadRepository;
import com.goganesh.bookshop.model.service.GenreWriteRepository;
import com.goganesh.bookshop.webapi.client.service.GenreRestService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Objects;
import java.util.Optional;

@AllArgsConstructor
public class GenreRestServiceImpl implements GenreRestService {

    private final GenreReadRepository genreReadRepository;
    private final GenreWriteRepository genreWriteRepository;

    @Override
    public Genre post(Genre genre) {

        Genre savedGenre = genreWriteRepository.save(genre);

        return savedGenre;
    }

    @Override
    public Page<Genre> findAll(Pageable pageable) {
        return genreReadRepository.findAll(pageable);
    }

    @Override
    public Optional<Genre> findById(Integer id) {
        return genreReadRepository.findById(id);
    }

    @Override
    public void delete(Genre genre) {
        genreWriteRepository.delete(genre);
    }
}
