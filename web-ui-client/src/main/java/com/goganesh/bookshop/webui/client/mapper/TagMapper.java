package com.goganesh.bookshop.webui.client.mapper;

import com.goganesh.bookshop.model.domain.Tag;
import com.goganesh.bookshop.model.service.BookReadRepository;
import com.goganesh.bookshop.webui.client.dto.TagPageDto;
import com.goganesh.bookshop.webui.client.service.TagSizeService;
import lombok.Getter;
import lombok.Setter;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper
@Getter
@Setter
public abstract class TagMapper {

    @Autowired
    protected BookReadRepository bookReadRepository;
    @Autowired
    protected TagSizeService tagSizeService;

    @Mapping(target = "slug",
            source = "tag.slug"
    )
    @Mapping(target = "name",
            source = "tag.name"
    )
    @Mapping(target = "count",
            expression = "java( bookReadRepository.countBooksByTag(tag) )"
    )
    @Mapping(target = "size",
            expression = "java( tagSizeService.getTagSize(tag) )"
    )
    public abstract TagPageDto toDto(Tag tag);
}
