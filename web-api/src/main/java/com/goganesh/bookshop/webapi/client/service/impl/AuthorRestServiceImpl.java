package com.goganesh.bookshop.webapi.client.service.impl;

import com.goganesh.bookshop.model.domain.Author;
import com.goganesh.bookshop.model.repository.AuthorRepository;
import com.goganesh.bookshop.webapi.client.service.AuthorRestService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class AuthorRestServiceImpl implements AuthorRestService {

    private final AuthorRepository authorRepository;

    public AuthorRestServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public Page<Author> findAll(Pageable pageable) {
        return authorRepository.findAll(pageable);
    }

    @Override
    public Optional<Author> findById(Integer id) {
        return authorRepository.findById(id);
    }

    @Override
    public void delete(Author author) {
        authorRepository.delete(author);
    }

    @Override
    public Author save(Author author) {
        return authorRepository.save(author);
    }
}
