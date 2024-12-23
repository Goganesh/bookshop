package com.goganesh.bookshop.webui.mapper;

import com.goganesh.bookshop.model.domain.BookFile;
import com.goganesh.bookshop.webui.dto.BookFilePageDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BookFileMapper {

    @Mapping(target = "hash",
            source = "bookFile.hash")
    @Mapping(target = "path",
            source = "bookFile.path")
    @Mapping(target = "type",
            source = "bookFile.type.name")
    BookFilePageDto toDto(BookFile bookFile);
}
