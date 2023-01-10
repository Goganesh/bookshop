package com.goganesh.bookshop.webapi.client.mapper;

import com.goganesh.bookshop.model.domain.BookReview;
import com.goganesh.bookshop.webapi.client.dto.ReviewApiRequestDto;
import com.goganesh.bookshop.webapi.client.dto.ReviewApiResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface ReviewApiMapper {

    @Mapping(target="id",
            source = "bookReview.id")
    @Mapping(target="bookId",
            source = "bookReview.book.id")
    @Mapping(target="userId",
            source = "bookReview.user.id")
    @Mapping(target="time",
            source = "bookReview.time")
    @Mapping(target="text",
            source = "bookReview.text")
    ReviewApiResponseDto toDto(BookReview bookReview);

    @Mapping(target="id",
            source = "reviewApiRequestDto.id")
    @Mapping(target="text",
            source = "reviewApiRequestDto.text")
    BookReview toModel(ReviewApiRequestDto reviewApiRequestDto);
}
