package com.goganesh.bookshop.webui.client.service.impl;

import com.goganesh.bookshop.model.domain.Tag;
import com.goganesh.bookshop.model.service.BookReadRepository;
import com.goganesh.bookshop.webui.client.service.TagSizeConfig;
import com.goganesh.bookshop.webui.client.service.TagSizeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TagSizeServiceImpl implements TagSizeService {

    public final BookReadRepository bookReadRepository;
    public final TagSizeConfig tagSizeConfig;

    @Override
    public String getTagSize(Tag tag) {
        long count = bookReadRepository.countBooksByTag(tag);

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
