package com.goganesh.bookshop.model.service.impl;

import com.goganesh.bookshop.model.domain.Author;
import com.goganesh.bookshop.model.repository.JpaAuthorRepository;
import com.goganesh.bookshop.model.service.AuthorWriteRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class JpaAuthorWriteRepository implements AuthorWriteRepository {

    private final JpaAuthorRepository jpaAuthorRepository;

    @Override
    public Author save(Author author) {
        return jpaAuthorRepository.save(author);
    }

    @Override
    public void delete(Author author) {
        jpaAuthorRepository.delete(author);
    }
}
