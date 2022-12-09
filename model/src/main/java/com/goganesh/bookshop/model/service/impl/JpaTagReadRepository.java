package com.goganesh.bookshop.model.service.impl;

import com.goganesh.bookshop.model.domain.Tag;
import com.goganesh.bookshop.model.repository.JpaTagRepository;
import com.goganesh.bookshop.model.service.TagReadRepository;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class JpaTagReadRepository implements TagReadRepository {

    private final JpaTagRepository jpaTagRepository;

    @Override
    public List<Tag> findAll() {
        return jpaTagRepository.findAll();
    }

    @Override
    public Optional<Tag> findBySlug(String slug) {
        return jpaTagRepository.findBySlug(slug);
    }
}
