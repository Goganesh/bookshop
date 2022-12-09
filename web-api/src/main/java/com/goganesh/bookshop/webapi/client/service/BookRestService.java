package com.goganesh.bookshop.webapi.client.service;

import com.goganesh.bookshop.model.domain.*;
import org.springframework.data.domain.Page;

import java.time.LocalDate;

public interface BookRestService {

    Page<Book> getPageOfRecommendedBooks(Integer offset, Integer limit);
    Page<Book> getPageOfPopularBooks(Integer offset, Integer limit);
    Page<Book> getPageOfRecentBooks(LocalDate from , LocalDate to, Integer offset, Integer limit);
    Page<Book> getPageOfSearchResultBooks(String searchWord, Integer offset, Integer limit);
    Page<Book> getPageOfGenreBooks(Genre genre, Integer offset, Integer limit);
    Page<Book> getPageOfAuthorBooks(Author author, Integer offset, Integer limit);
    Page<Book> getPageOfTagBooks(Tag tag, Integer offset, Integer limit);
    Page<Book> getPageBooksPageByUserAndTypeCode(User user, String typeCode, Integer offset, Integer limit);
}
