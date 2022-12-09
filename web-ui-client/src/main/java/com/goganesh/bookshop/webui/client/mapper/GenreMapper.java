package com.goganesh.bookshop.webui.client.mapper;

import com.goganesh.bookshop.model.domain.Genre;
import com.goganesh.bookshop.model.service.BookReadRepository;
import com.goganesh.bookshop.webui.client.dto.GenrePageDto;
import lombok.Getter;
import lombok.Setter;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.stream.Collectors;

@Mapper(imports = Collectors.class)
@Getter
@Setter
public abstract class GenreMapper {

    @Autowired
    protected BookReadRepository bookReadRepository;

    @Mapping(target = "name",
            source = "genre.name"
    )
    @Mapping(target = "slug",
            source = "genre.slug"
    )
    @Mapping(target = "bookCount",
            expression = "java( bookReadRepository.countBooksByGenre(genre) )"
    )
    @Mapping(target = "childGenrePageDtos",
            expression = "java( genre.getChildGenres().stream().map(this::toDto).collect(Collectors.toList()) )"
    )
    public abstract GenrePageDto toDto(Genre genre);
}
