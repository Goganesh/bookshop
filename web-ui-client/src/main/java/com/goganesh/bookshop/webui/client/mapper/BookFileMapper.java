package com.goganesh.bookshop.webui.client.mapper;

import com.goganesh.bookshop.model.domain.BookFile;
import com.goganesh.bookshop.webui.client.dto.BookFilePageDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface BookFileMapper {

    @Mapping(target = "hash",
            source = "bookFile.hash")
    @Mapping(target = "path",
            source = "bookFile.path")
    @Mapping(target = "type",
            source = "bookFile.type.name")
    BookFilePageDto toDto(BookFile bookFile);
}
