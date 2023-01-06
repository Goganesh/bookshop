package com.goganesh.bookshop.webapi.client.service.impl;

import com.goganesh.bookshop.model.domain.*;
import com.goganesh.bookshop.model.service.BookReadRepository;
import com.goganesh.bookshop.model.service.BookWriteRepository;
import com.goganesh.bookshop.webapi.client.service.BookRestService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.time.LocalDate;
import java.util.Optional;

@AllArgsConstructor
public class BookRestServiceImpl implements BookRestService {

    private final BookReadRepository bookReadRepository;
    private final BookWriteRepository bookWriteRepository;

    @Override
    public Page<Book> findAll(Pageable pageable) {
        return bookReadRepository.findAll(pageable);
    }

    @Override
    public Optional<Book> findById(Integer id) {
        return bookReadRepository.findById(id);
    }

    @Override
    public void delete(Book book) {
        bookWriteRepository.delete(book);
    }

    @Override
    public Book save(Book book) {
        return bookWriteRepository.save(book);
    }

    @Override
    public Page<Book> getPageBooksPageByUserAndTypeCode(User user, String typeCode, Integer offset, Integer limit) {
        Pageable nextPage = PageRequest.of(offset,limit);
        return bookReadRepository.findByUserAndTypeCode(user, typeCode, nextPage);
    }

    @Override
    public Page<Book> getPageOfTagBooks(Tag tag, Integer offset, Integer limit) {
        Pageable nextPage = PageRequest.of(offset,limit);
        return bookReadRepository.findByTag(tag, nextPage);
    }

    @Override
    public Page<Book> getPageOfAuthorBooks(Author author, Integer offset, Integer limit) {
        Pageable nextPage = PageRequest.of(offset,limit);
        return bookReadRepository.findByAuthor(author, nextPage);
    }

    @Override
    public Page<Book> getPageOfGenreBooks(Genre genre, Integer offset, Integer limit) {
        Pageable nextPage = PageRequest.of(offset,limit);
        return bookReadRepository.findByGenre(genre, nextPage);
    }

    @Override
    public Page<Book> getPageOfRecommendedBooks(Integer offset, Integer limit){
        Pageable nextPage = PageRequest.of(offset,limit);
        return bookReadRepository.findAll(nextPage);
    }

    @Override
    public Page<Book> getPageOfPopularBooks(Integer offset, Integer limit) {
        Pageable nextPage = PageRequest.of(offset,limit, Sort.by("popularity").descending());
        return bookReadRepository.findAll(nextPage);
    }

    @Override
    public Page<Book> getPageOfRecentBooks(LocalDate from, LocalDate to, Integer offset, Integer limit) {
        Pageable nextPage = PageRequest.of(offset,limit);
        return bookReadRepository.findByPubDateBetween(from, to, nextPage);
    }

    @Override
    public Page<Book> getPageOfSearchResultBooks(String searchWord, Integer offset, Integer limit) {
        Pageable nextPage = PageRequest.of(offset,limit);
        return bookReadRepository.findByTitleContaining(searchWord, nextPage);
    }
}
