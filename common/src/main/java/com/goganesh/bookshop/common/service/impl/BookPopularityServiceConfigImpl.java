package com.goganesh.bookshop.common.service.impl;

import com.goganesh.bookshop.common.service.BookPopularityServiceConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


@Service
public class BookPopularityServiceConfigImpl implements BookPopularityServiceConfig {

    private final Double paidRate;
    private final Double cartRate;
    private final Double keptRate;
    private final Double viewedRate;
    private final Double defaultRate;
    private final Integer viewedTimeLimitMinutes;

    public BookPopularityServiceConfigImpl(@Value("${com.goganesh.bookshop.popularity-service.rate.paid}") Double paidRate,
                                           @Value("${com.goganesh.bookshop.popularity-service.rate.cart}") Double cartRate,
                                           @Value("${com.goganesh.bookshop.popularity-service.rate.kept}") Double keptRate,
                                           @Value("${com.goganesh.bookshop.popularity-service.rate.viewed}") Double viewedRate,
                                           @Value("${com.goganesh.bookshop.popularity-service.rate.default}") Double defaultRate,
                                           @Value("${com.goganesh.bookshop.popularity-service.viewed-time-limit-minutes}") Integer viewedTimeLimitMinutes) {
        this.paidRate = paidRate;
        this.cartRate = cartRate;
        this.keptRate = keptRate;
        this.viewedRate = viewedRate;
        this.defaultRate = defaultRate;
        this.viewedTimeLimitMinutes = viewedTimeLimitMinutes;
    }

    @Override
    public Double getPaidRate() {
        return paidRate;
    }

    @Override
    public Double getCartRate() {
        return cartRate;
    }

    @Override
    public Double getKeptRate() {
        return keptRate;
    }

    @Override
    public Double getViewedRate() {
        return viewedRate;
    }

    @Override
    public Double getDefaultRate() {
        return defaultRate;
    }

    @Override
    public Integer getViewedTimeLimitMinutes() {
        return viewedTimeLimitMinutes;
    }
}
