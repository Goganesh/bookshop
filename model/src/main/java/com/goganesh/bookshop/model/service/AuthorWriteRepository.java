package com.goganesh.bookshop.model.service;

import com.goganesh.bookshop.model.domain.Author;

public interface AuthorWriteRepository {

    void save(Author author);
}
