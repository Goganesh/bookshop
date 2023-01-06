package com.goganesh.bookshop.model.service;

import com.goganesh.bookshop.model.domain.*;

public interface BookWriteRepository {

    Book save(Book book);
    void deleteById(Integer id);

    void delete(Book book);
}
