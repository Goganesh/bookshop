package com.goganesh.bookshop.model.service;

import com.goganesh.bookshop.model.domain.Activity;

public interface ActivityWriteRepository {

    void save(Activity activity);
}
