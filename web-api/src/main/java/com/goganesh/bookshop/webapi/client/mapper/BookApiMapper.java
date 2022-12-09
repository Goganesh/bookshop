package com.goganesh.bookshop.webapi.client.mapper;

import com.goganesh.bookshop.model.domain.Book;
import com.goganesh.bookshop.webapi.client.dto.BookResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface BookApiMapper {
    @Mapping(target="id",
            source = "book.id")
    @Mapping(target="pubDate",
            source = "book.pubDate")
    @Mapping(target="bestseller",
            source = "book.bestseller")
    @Mapping(target="slug",
            source = "book.slug")
    @Mapping(target="title",
            source = "book.title")
    @Mapping(target="image",
            source = "book.image")
    @Mapping(target="description",
            source = "book.description")
    @Mapping(target="price",
            source = "book.price")
    @Mapping(target="discount",
            source = "book.discount")
    @Mapping(target="popularity",
            source = "book.popularity")
    BookResponseDto toDto(Book book);
}
