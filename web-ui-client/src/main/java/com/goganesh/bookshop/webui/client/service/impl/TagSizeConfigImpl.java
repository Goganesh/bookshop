package com.goganesh.bookshop.webui.client.service.impl;

import com.goganesh.bookshop.webui.client.service.TagSizeConfig;
import org.springframework.stereotype.Service;

@Service
public class TagSizeConfigImpl implements TagSizeConfig {

    private final long getExtraSmallsCountConfig;
    private final long getSmallCountConfig;
    private final long getNormalCountConfig;
    private final long getMiddleCountConfig;

    public TagSizeConfigImpl() {
        this.getExtraSmallsCountConfig = 10;
        this.getSmallCountConfig = 50;
        this.getNormalCountConfig = 100;
        this.getMiddleCountConfig = 200;
    }

    @Override
    public long getExtraSmallsCount() {
        return 0;
    }

    @Override
    public long getSmallCount() {
        return 0;
    }

    @Override
    public long getNormalCount() {
        return 0;
    }

    @Override
    public long getMiddleCount() {
        return 0;
    }

}
