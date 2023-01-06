package com.goganesh.bookshop.webapi.client.mapper;

import com.goganesh.bookshop.model.domain.Author;
import com.goganesh.bookshop.webapi.client.dto.AuthorApiRequestDto;
import com.goganesh.bookshop.webapi.client.dto.AuthorApiResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface AuthorApiMapper {

    @Mapping(target="id",
            source = "author.id")
    @Mapping(target="name",
            source = "author.name")
    @Mapping(target="slug",
            source = "author.slug")
    @Mapping(target="description",
            source = "author.description")
    @Mapping(target="photo",
            source = "author.photo")
    AuthorApiResponseDto toDto(Author author);

    @Mapping(target="id",
            source = "authorApiRequestDto.id")
    @Mapping(target="name",
            source = "authorApiRequestDto.name")
    @Mapping(target="slug",
            source = "authorApiRequestDto.slug")
    @Mapping(target="description",
            source = "authorApiRequestDto.description")
    Author toModel(AuthorApiRequestDto authorApiRequestDto);
}
