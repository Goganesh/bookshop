package com.goganesh.bookshop.common.service.impl;

import com.goganesh.bookshop.common.service.BookPopularityServiceConfig;
import lombok.AllArgsConstructor;


@AllArgsConstructor
public class BookPopularityServiceConfigImpl implements BookPopularityServiceConfig {

    private final Double paidRateConfig;
    private final Double cartRateConfig;
    private final Double keptRateConfig;
    private final Double viewedRateConfig;
    private final Double defaultRateConfig;
    private final Integer viewedTimeLimitMinutesConfig;

    @Override
    public Double getPaidRate() {
        return paidRateConfig;
    }

    @Override
    public Double getCartRate() {
        return cartRateConfig;
    }

    @Override
    public Double getKeptRate() {
        return keptRateConfig;
    }

    @Override
    public Double getViewedRate() {
        return viewedRateConfig;
    }

    @Override
    public Double getDefaultRate() {
        return defaultRateConfig;
    }

    @Override
    public Integer getViewedTimeLimitMinutes() {
        return viewedTimeLimitMinutesConfig;
    }
}
