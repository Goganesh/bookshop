package com.goganesh.bookshop.webui.service.impl;

import com.goganesh.bookshop.model.domain.Tag;
import com.goganesh.bookshop.model.repository.BookRepository;
import com.goganesh.bookshop.webui.client.service.TagSizeConfig;
import com.goganesh.bookshop.webui.client.service.TagSizeService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class TagSizeServiceImpl implements TagSizeService {

    public final BookRepository bookRepository;
    public final TagSizeConfig tagSizeConfig;

    public TagSizeServiceImpl(BookRepository bookRepository, TagSizeConfig tagSizeConfig) {
        this.bookRepository = bookRepository;
        this.tagSizeConfig = tagSizeConfig;
    }

    @Override
    public String getTagSize(Tag tag) {
        long count = bookRepository.countBooksByTag(tag);

        String size = null;
        if (count < tagSizeConfig.getExtraSmallsCount()) {
            size = "XS";
        } else if (count < tagSizeConfig.getSmallCount()) {
            size = "SM";
        } else if (count < tagSizeConfig.getNormalCount()) {
            size = "M";
        } else if (count < tagSizeConfig.getMiddleCount()) {
            size = "MD";
        } else {
            size = "LG";
        }

        return size;
    }
}
