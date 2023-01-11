package com.goganesh.bookshop.webapi.client.mapper;

import com.goganesh.bookshop.model.domain.Book;
import com.goganesh.bookshop.webapi.client.dto.BookApiRequestDto;
import com.goganesh.bookshop.webapi.client.dto.BookApiResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface BookApiMapper {
    @Mapping(target = "id",
            source = "book.id")
    @Mapping(target = "pubDate",
            source = "book.pubDate")
    @Mapping(target = "isBestseller",
            source = "book.bestseller")
    @Mapping(target = "slug",
            source = "book.slug")
    @Mapping(target = "title",
            source = "book.title")
    @Mapping(target = "image",
            source = "book.image")
    @Mapping(target = "description",
            source = "book.description")
    @Mapping(target = "price",
            source = "book.price")
    @Mapping(target = "discount",
            source = "book.discount")
    @Mapping(target = "popularity",
            source = "book.popularity")
    BookApiResponseDto toDto(Book book);

    @Mapping(target = "id",
            source = "bookApiRequestDto.id")
    @Mapping(target = "pubDate",
            source = "bookApiRequestDto.pubDate")
    @Mapping(target = "slug",
            source = "bookApiRequestDto.slug")
    @Mapping(target = "title",
            source = "bookApiRequestDto.title")
    @Mapping(target = "description",
            source = "bookApiRequestDto.description")
    @Mapping(target = "price",
            source = "bookApiRequestDto.price")
    @Mapping(target = "discount",
            source = "bookApiRequestDto.discount")
    Book toModel(BookApiRequestDto bookApiRequestDto);
}
