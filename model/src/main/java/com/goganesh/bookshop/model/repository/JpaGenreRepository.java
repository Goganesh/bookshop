package com.goganesh.bookshop.model.repository;

import com.goganesh.bookshop.model.domain.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface JpaGenreRepository extends JpaRepository<Genre, Integer> {

    List<Genre> findByParentIsNull();

    Optional<Genre> findBySlug(String slug);

    List<Genre> findAllByParent(Genre genre);
}
