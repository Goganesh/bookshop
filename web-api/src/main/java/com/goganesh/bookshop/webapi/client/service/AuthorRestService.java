package com.goganesh.bookshop.webapi.client.service;

import com.goganesh.bookshop.model.domain.Author;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface AuthorRestService {

    Page<Author> findAll(Pageable pageable);

    Optional<Author> findById(Integer id);

    void delete(Author author);

    Author save(Author author);
}
