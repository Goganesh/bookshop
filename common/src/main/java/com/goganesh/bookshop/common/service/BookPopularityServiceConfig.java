package com.goganesh.bookshop.common.service;

public interface BookPopularityServiceConfig {

    Double getPaidRate();

    Double getCartRate();

    Double getKeptRate();

    Double getViewedRate();

    Double getDefaultRate();

    Integer getViewedTimeLimitMinutes();
}
