package com.goganesh.bookshop.webapi.client.service;

import com.goganesh.bookshop.model.domain.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.Optional;

public interface BookRestService {

    Page<Book> findAll(Pageable pageable);
    Optional<Book> findById(Integer id);

    void delete(Book book);
    Book save(Book book);

    Page<Book> getPageOfRecommendedBooks(Integer offset, Integer limit);
    Page<Book> getPageOfPopularBooks(Integer offset, Integer limit);
    Page<Book> getPageOfRecentBooks(LocalDate from , LocalDate to, Integer offset, Integer limit);
    Page<Book> getPageOfSearchResultBooks(String searchWord, Integer offset, Integer limit);
    Page<Book> getPageOfGenreBooks(Genre genre, Integer offset, Integer limit);
    Page<Book> getPageOfAuthorBooks(Author author, Integer offset, Integer limit);
    Page<Book> getPageOfTagBooks(Tag tag, Integer offset, Integer limit);
    Page<Book> getPageBooksPageByUserAndTypeCode(User user, String typeCode, Integer offset, Integer limit);
}
