package com.goganesh.bookshop.webui.mapper;

import com.goganesh.bookshop.common.service.BookRatingService;
import com.goganesh.bookshop.model.domain.Book;
import com.goganesh.bookshop.model.domain.Book2Author;
import com.goganesh.bookshop.webui.dto.BookPageDto;
import lombok.Getter;
import lombok.Setter;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Comparator;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", imports = {Comparator.class, Collectors.class, Book2Author.class})
@Getter
@Setter
public abstract class BookPageMapper {

    @Autowired
    protected BookRatingService bookRatingService;

    @Mapping(target = "slug",
            source = "book.slug"
    )
    @Mapping(target = "title",
            source = "book.title"
    )
    @Mapping(target = "description",
            source = "book.description"
    )
    @Mapping(target = "image",
            source = "book.image"
    )
    @Mapping(target = "authors",
            expression = "java( book.getBook2Authors().stream().sorted((Comparator.comparingInt(Book2Author::getSortIndex))).map(book2Author -> book2Author.getAuthor().getName()).collect(Collectors.joining(\", \")) )"
    )
    @Mapping(target = "discount",
            source = "book.discount"
    )
    @Mapping(target = "rating",
            expression = "java( bookRatingService.findRatingByBook(book) )"
    )
    @Mapping(target = "bestseller",
            source = "book.bestseller"
    )
    @Mapping(target = "price",
            source = "book.price"
    )
    @Mapping(target = "discountPrice",
            expression = "java( book.getPrice() - Math.toIntExact(Math.round(book.getDiscount() * book.getPrice())) )"
    )
    public abstract BookPageDto toDto(Book book);
}
