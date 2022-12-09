package com.goganesh.bookshop.model.repository;

import com.goganesh.bookshop.model.domain.Author;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaAuthorRepository extends JpaRepository<Author, Integer> {

    Optional<Author> findBySlug(String slug);

}
