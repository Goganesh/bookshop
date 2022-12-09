package com.goganesh.bookshop.model.service;

import com.goganesh.bookshop.model.domain.InvalidToken;

public interface InvalidTokenWriteRepository {

    void save(InvalidToken invalidToken);
}
