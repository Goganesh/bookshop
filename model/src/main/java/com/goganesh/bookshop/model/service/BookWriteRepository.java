package com.goganesh.bookshop.model.service;

import com.goganesh.bookshop.model.domain.*;

public interface BookWriteRepository {

    void save(Book book);
    void deleteById(Integer id);
}
