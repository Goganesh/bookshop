package com.goganesh.bookshop.webapi.client.service.impl;

import com.goganesh.bookshop.model.domain.Author;
import com.goganesh.bookshop.model.service.AuthorReadRepository;
import com.goganesh.bookshop.model.service.AuthorWriteRepository;
import com.goganesh.bookshop.webapi.client.service.AuthorRestService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

@AllArgsConstructor
public class AuthorRestServiceImpl implements AuthorRestService {

    private final AuthorReadRepository authorReadRepository;
    private final AuthorWriteRepository authorWriteRepository;

    @Override
    public Page<Author> findAll(Pageable pageable) {
        return authorReadRepository.findAll(pageable);
    }

    @Override
    public Optional<Author> findById(Integer id) {
        return authorReadRepository.findById(id);
    }

    @Override
    public void delete(Author author) {
        authorWriteRepository.delete(author);
    }

    @Override
    public Author save(Author author) {
        return authorWriteRepository.save(author);
    }
}
