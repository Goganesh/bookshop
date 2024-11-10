package com.goganesh.bookshop.webui.mapper;

import com.goganesh.bookshop.model.domain.Genre;
import com.goganesh.bookshop.model.repository.BookRepository;
import com.goganesh.bookshop.model.repository.GenreRepository;
import com.goganesh.bookshop.webui.client.dto.GenrePageDto;
import lombok.Getter;
import lombok.Setter;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.stream.Collectors;

@Mapper(componentModel = "spring",imports = Collectors.class)
@Getter
@Setter
public abstract class GenreMapper {

    @Autowired
    protected BookRepository bookRepository;

    @Autowired
    protected GenreRepository genreRepository;

    @Mapping(target = "name",
            source = "genre.name"
    )
    @Mapping(target = "slug",
            source = "genre.slug"
    )
    @Mapping(target = "bookCount",
            expression = "java( bookRepository.countBooksByGenre(genre) )"
    )
    @Mapping(target = "childGenrePageDtos",
            expression = "java( genreRepository.findAllByParent(genre).stream().map(this::toDto).collect(Collectors.toList()) )"
    )
    public abstract GenrePageDto toDto(Genre genre);
}
