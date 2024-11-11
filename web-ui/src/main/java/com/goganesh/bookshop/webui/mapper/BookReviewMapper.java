package com.goganesh.bookshop.webui.mapper;

import com.goganesh.bookshop.model.domain.BookReview;
import com.goganesh.bookshop.webui.dto.BookReviewDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BookReviewMapper {

    @Mapping(target = "id",
            source = "bookReview.id"
    )
    @Mapping(target = "reviewer",
            source = "bookReview.user.name"
    )
    @Mapping(target = "text",
            source = "bookReview.text"
    )
    @Mapping(target = "reviewTime",
            source = "bookReview.time",
            dateFormat = "dd.MM.yyyy HH:mm"
    )
    @Mapping(target = "positiveReview",
            expression = "java( bookReview.getBookReviewLikes().stream().filter(bookReviewLike -> bookReviewLike.getValue() == 1).count() )"
    )
    @Mapping(target = "negativeReview",
            expression = "java( bookReview.getBookReviewLikes().stream().filter(bookReviewLike -> bookReviewLike.getValue() == -1).count() )"
    )
    BookReviewDto toDto(BookReview bookReview);

}
