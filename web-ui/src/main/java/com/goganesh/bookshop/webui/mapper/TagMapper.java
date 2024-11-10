package com.goganesh.bookshop.webui.mapper;

import com.goganesh.bookshop.model.domain.Tag;
import com.goganesh.bookshop.model.repository.BookRepository;
import com.goganesh.bookshop.webui.client.dto.TagPageDto;
import com.goganesh.bookshop.webui.client.service.TagSizeService;
import lombok.Getter;
import lombok.Setter;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
@Getter
@Setter
public abstract class TagMapper {

    @Autowired
    protected BookRepository bookRepository;
    @Autowired
    protected TagSizeService tagSizeService;

    @Mapping(target = "slug",
            source = "tag.slug"
    )
    @Mapping(target = "name",
            source = "tag.name"
    )
    @Mapping(target = "count",
            expression = "java( bookRepository.countBooksByTag(tag) )"
    )
    @Mapping(target = "size",
            expression = "java( tagSizeService.getTagSize(tag) )"
    )
    public abstract TagPageDto toDto(Tag tag);
}
