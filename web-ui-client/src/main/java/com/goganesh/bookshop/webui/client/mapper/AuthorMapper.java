package com.goganesh.bookshop.webui.client.mapper;

import com.goganesh.bookshop.model.domain.Author;
import com.goganesh.bookshop.model.service.BookReadRepository;
import com.goganesh.bookshop.webui.client.dto.AuthorPageDto;
import lombok.Getter;
import lombok.Setter;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper
@Getter
@Setter
public abstract class AuthorMapper {

    @Autowired
    protected BookReadRepository bookReadRepository;

    @Mapping(target = "name",
            source = "author.name"
    )
    @Mapping(target = "slug",
            source = "author.slug"
    )
    @Mapping(target = "description",
            source = "author.description"
    )
    @Mapping(target = "photo",
            source = "author.photo"
    )
    @Mapping(target = "bookCount",
            expression = "java( bookReadRepository.countBooksByAuthor(author) )"
    )
    public abstract AuthorPageDto toDto(Author author);
}
