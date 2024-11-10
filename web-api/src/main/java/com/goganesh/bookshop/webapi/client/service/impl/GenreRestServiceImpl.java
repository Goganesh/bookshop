package com.goganesh.bookshop.webapi.client.service.impl;

import com.goganesh.bookshop.model.domain.Genre;
import com.goganesh.bookshop.model.repository.GenreRepository;
import com.goganesh.bookshop.webapi.client.service.GenreRestService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class GenreRestServiceImpl implements GenreRestService {

    private final GenreRepository genreRepository;

    public GenreRestServiceImpl(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    @Override
    public Genre save(Genre genre) {
        return genreRepository.save(genre);
    }

    @Override
    public Page<Genre> findAll(Pageable pageable) {
        return genreRepository.findAll(pageable);
    }

    @Override
    public Optional<Genre> findById(Integer id) {
        return genreRepository.findById(id);
    }

    @Override
    public void delete(Genre genre) {
        genreRepository.delete(genre);
    }
}
