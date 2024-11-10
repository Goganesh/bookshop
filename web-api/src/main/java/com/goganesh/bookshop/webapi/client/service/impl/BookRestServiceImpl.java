package com.goganesh.bookshop.webapi.client.service.impl;

import com.goganesh.bookshop.model.domain.*;
import com.goganesh.bookshop.model.repository.BookRepository;
import com.goganesh.bookshop.webapi.client.service.BookRestService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.util.Optional;

@Service
@Transactional
public class BookRestServiceImpl implements BookRestService {

    private final BookRepository bookRepository;

    public BookRestServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public Page<Book> findAll(Pageable pageable) {
        return bookRepository.findAll(pageable);
    }

    @Override
    public Optional<Book> findById(Integer id) {
        return bookRepository.findById(id);
    }

    @Override
    public void delete(Book book) {
        bookRepository.delete(book);
    }

    @Override
    public Book save(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public Page<Book> getPageBooksPageByUserAndTypeCode(User user, String typeCode, Integer offset, Integer limit) {
        Pageable nextPage = PageRequest.of(offset, limit);
        return bookRepository.findByUserAndTypeCode(user, typeCode, nextPage);
    }

    @Override
    public Page<Book> getPageOfTagBooks(Tag tag, Integer offset, Integer limit) {
        Pageable nextPage = PageRequest.of(offset, limit);
        return bookRepository.findByTag(tag, nextPage);
    }

    @Override
    public Page<Book> getPageOfAuthorBooks(Author author, Integer offset, Integer limit) {
        Pageable nextPage = PageRequest.of(offset, limit);
        return bookRepository.findByAuthor(author, nextPage);
    }

    @Override
    public Page<Book> getPageOfGenreBooks(Genre genre, Integer offset, Integer limit) {
        Pageable nextPage = PageRequest.of(offset, limit);
        return bookRepository.findByGenre(genre, nextPage);
    }

    @Override
    public Page<Book> getPageOfRecommendedBooks(Integer offset, Integer limit) {
        Pageable nextPage = PageRequest.of(offset, limit);
        return bookRepository.findAll(nextPage);
    }

    @Override
    public Page<Book> getPageOfPopularBooks(Integer offset, Integer limit) {
        Pageable nextPage = PageRequest.of(offset, limit, Sort.by("popularity").descending());
        return bookRepository.findAll(nextPage);
    }

    @Override
    public Page<Book> getPageOfRecentBooks(LocalDate from, LocalDate to, Integer offset, Integer limit) {
        Pageable nextPage = PageRequest.of(offset, limit);
        return bookRepository.findByPubDateBetween(from, to, nextPage);
    }

    @Override
    public Page<Book> getPageOfSearchResultBooks(String searchWord, Integer offset, Integer limit) {
        Pageable nextPage = PageRequest.of(offset, limit);
        return bookRepository.findByTitleContaining(searchWord, nextPage);
    }
}
