package com.goganesh.bookshop.model.service;

import com.goganesh.bookshop.model.domain.Author;

public interface AuthorWriteRepository {

    Author save(Author author);

    void delete(Author author);
}
