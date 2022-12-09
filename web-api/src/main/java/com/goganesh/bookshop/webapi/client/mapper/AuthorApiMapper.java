package com.goganesh.bookshop.webapi.client.mapper;

import com.goganesh.bookshop.model.domain.Author;
import com.goganesh.bookshop.webapi.client.dto.AuthorResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface AuthorApiMapper {

    @Mapping(target="id",
            source = "author.id")
    @Mapping(target="photo",
            source = "author.photo")
    @Mapping(target="name",
            source = "author.name")
    @Mapping(target="slug",
            source = "author.slug")
    @Mapping(target="description",
            source = "author.description")
    AuthorResponseDto toDto(Author author);
}
