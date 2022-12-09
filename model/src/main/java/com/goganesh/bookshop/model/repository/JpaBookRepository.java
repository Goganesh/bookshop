package com.goganesh.bookshop.model.repository;

import com.goganesh.bookshop.model.domain.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Optional;

public interface JpaBookRepository extends JpaRepository<Book, Integer> {

    @Query("SELECT count(ba.book) FROM Book2Author ba WHERE ba.author = :author")
    long countBooksByAuthor(@Param("author") Author author);

    @Query("SELECT count(bg.book) FROM Book2Genre bg WHERE bg.genre = :genre")
    long countBooksByGenre(@Param("genre") Genre genre);

    @Query("SELECT count(bt.book) FROM Book2Tag bt WHERE bt.tag = :tag")
    long countBooksByTag(@Param("tag") Tag tag);

    @Query("SELECT bg.book FROM Book2Genre bg WHERE bg.genre = :genre")
    Page<Book> findByGenre(@Param("genre") Genre genre, Pageable nextPage);
    @Query("SELECT ba.book FROM Book2Author ba WHERE ba.author = :author")
    Page<Book> findByAuthor(@Param("author") Author author, Pageable nextPage);
    @Query("SELECT bt.book FROM Book2Tag bt WHERE bt.tag = :tag")
    Page<Book> findByTag(@Param("tag") Tag tag, Pageable nextPage);
    @Query("SELECT bu.book FROM Book2User bu WHERE bu.user = :user and bu.book2UserType.code = :typeCode")
    Page<Book> findByUserAndTypeCode(@Param("user") User user, @Param("typeCode") String typeCode, Pageable nextPage);

    Optional<Book> findBySlug(String slug);
    Page<Book> findByTitleContaining(String bookTitle, Pageable nextPage);
    Page<Book> findByPubDateBetween(LocalDate pubDateStart, LocalDate pubDateFinish, Pageable nextPage);


}
