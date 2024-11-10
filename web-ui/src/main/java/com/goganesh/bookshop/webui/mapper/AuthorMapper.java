package com.goganesh.bookshop.webui.mapper;

import com.goganesh.bookshop.model.domain.Author;
import com.goganesh.bookshop.model.repository.BookRepository;
import com.goganesh.bookshop.webui.client.dto.AuthorPageDto;
import lombok.Getter;
import lombok.Setter;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
@Getter
@Setter
public abstract class AuthorMapper {

    @Autowired
    protected BookRepository bookRepository;

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
            expression = "java( bookRepository.countBooksByAuthor(author) )"
    )
    public abstract AuthorPageDto toDto(Author author);
}
