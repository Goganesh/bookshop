package com.goganesh.bookshop.webapi.client.controller;

import com.goganesh.bookshop.model.domain.Genre;
import com.goganesh.bookshop.webapi.client.dto.GenreApiRequestDto;
import com.goganesh.bookshop.webapi.client.dto.GenreApiResponseDto;
import com.goganesh.bookshop.webapi.client.exception.NoSuchGenreException;
import com.goganesh.bookshop.webapi.client.mapper.GenreApiMapper;
import com.goganesh.bookshop.webapi.client.service.GenreRestService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@PreAuthorize("hasAnyRole('ADMIN')")
@RequestMapping("/api/v1/genres")
public class GenreController {

    private final GenreRestService genreRestService;
    private final GenreApiMapper genreApiMapper;

    public GenreController(GenreRestService genreRestService, GenreApiMapper genreApiMapper) {
        this.genreRestService = genreRestService;
        this.genreApiMapper = genreApiMapper;
    }

    @GetMapping()
    public Page<GenreApiResponseDto> getGenres(@PageableDefault(value = 20) Pageable pageable) {
        return genreRestService.findAll(pageable).map(genreApiMapper::toDto);
    }

    @GetMapping("/{id}")
    public GenreApiResponseDto getGenre(@PathVariable("id") Integer id) {
        Genre genre = genreRestService.findById(id).orElseThrow(() -> new NoSuchGenreException("No such genre with id " + id));
        return genreApiMapper.toDto(genre);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGenre(@PathVariable("id") Integer id) {
        Genre genre = genreRestService.findById(id).orElseThrow(() -> new NoSuchGenreException("No such genre with id " + id));
        genreRestService.delete(genre);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping
    public GenreApiResponseDto postGenre(@Validated @RequestBody GenreApiRequestDto genreApiRequestDto) {
        if (genreApiRequestDto.getId() == -1) {
            genreApiRequestDto.setId(null);
        }

        Genre genre = genreApiMapper.toModel(genreApiRequestDto);
        genre = genreRestService.save(genre);

        return genreApiMapper.toDto(genre);
    }
}
