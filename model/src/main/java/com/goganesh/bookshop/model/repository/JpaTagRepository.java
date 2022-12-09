package com.goganesh.bookshop.model.repository;

import com.goganesh.bookshop.model.domain.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaTagRepository extends JpaRepository<Tag, Integer> {

    Optional<Tag> findBySlug(String slug);
}
