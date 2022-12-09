package com.goganesh.bookshop.model.service;

import com.goganesh.bookshop.model.domain.Tag;

import java.util.List;
import java.util.Optional;

public interface TagReadRepository {

    Optional<Tag> findBySlug(String slug);

    List<Tag> findAll();
}
