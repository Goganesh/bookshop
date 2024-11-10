package com.goganesh.bookshop.webapi.client.mapper;

import com.goganesh.bookshop.model.domain.Genre;
import com.goganesh.bookshop.model.repository.GenreRepository;
import com.goganesh.bookshop.webapi.client.dto.GenreApiRequestDto;
import com.goganesh.bookshop.webapi.client.dto.GenreApiResponseDto;
import com.goganesh.bookshop.webapi.client.exception.NoSuchGenreException;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring", imports = {NoSuchGenreException.class})
public abstract class GenreApiMapper {

    @Autowired
    protected GenreRepository genreRepository;

    @Mapping(target = "id",
            source = "genre.id")
    @Mapping(target = "parentId",
            source = "genre.parent.id")
    @Mapping(target = "name",
            source = "genre.name")
    @Mapping(target = "slug",
            source = "genre.slug")
    public abstract GenreApiResponseDto toDto(Genre genre);

    @Mapping(target = "id",
            source = "genreApiRequestDto.id")
    @Mapping(target = "parent",
            expression = "java( genreRepository.findById(genreApiRequestDto.getParentId()).orElseThrow(() -> new NoSuchGenreException(\"No such genre with id \" + genreApiRequestDto.getParentId())) )")
    @Mapping(target = "name",
            source = "genreApiRequestDto.name")
    @Mapping(target = "slug",
            source = "genreApiRequestDto.slug")
    public abstract Genre toModel(GenreApiRequestDto genreApiRequestDto);
}
