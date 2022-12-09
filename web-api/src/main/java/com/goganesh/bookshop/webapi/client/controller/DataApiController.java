package com.goganesh.bookshop.webapi.client.controller;

import com.goganesh.bookshop.model.domain.Author;
import com.goganesh.bookshop.model.domain.Genre;
import com.goganesh.bookshop.model.domain.Tag;
import com.goganesh.bookshop.model.domain.User;
import com.goganesh.bookshop.model.service.AuthorReadRepository;
import com.goganesh.bookshop.model.service.GenreReadRepository;
import com.goganesh.bookshop.model.service.TagReadRepository;
import com.goganesh.bookshop.webapi.client.dto.BooksDto;
import com.goganesh.bookshop.webapi.client.exception.NoSuchAuthorException;
import com.goganesh.bookshop.webapi.client.exception.NoSuchGenreException;
import com.goganesh.bookshop.webapi.client.exception.NoSuchTagException;
import com.goganesh.bookshop.webapi.client.mapper.BookMapper;
import com.goganesh.bookshop.webapi.client.service.BookRestService;
import com.goganesh.security.service.UserRegisterService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1")
public class DataApiController {

    private final BookRestService bookRestService;
    private final GenreReadRepository genreReadRepository;
    private final AuthorReadRepository authorReadRepository;
    private final TagReadRepository tagReadRepository;
    private final UserRegisterService userRegisterService;
    private final BookMapper bookMapper;

    @PreAuthorize("hasAnyRole('USER')")
    @GetMapping("/books/user/{status}")
    @ResponseBody
    public BooksDto getUserBooks(@RequestParam("offset") Integer offset,
                                 @RequestParam("limit") Integer limit,
                                 @PathVariable(value = "status", required = true) String status) {

        User user = userRegisterService.getCurrentUser();
        return new BooksDto(bookRestService.getPageBooksPageByUserAndTypeCode(user, status, offset, limit).getContent()
                .stream()
                .map(bookMapper::toDto)
                .collect(Collectors.toList()));
    }

    @PreAuthorize("hasAnyRole('USER','TEMP_USER')")
    @GetMapping("/books/tag/{slug}")
    @ResponseBody
    public BooksDto getBooksTag(@RequestParam("offset") Integer offset,
                                @RequestParam("limit") Integer limit,
                                @PathVariable(value = "slug", required = true) String slug) {
        Tag tag = tagReadRepository.findBySlug(slug).orElseThrow(() -> new NoSuchTagException("No Such tag with slug - " + slug));
        return new BooksDto(bookRestService.getPageOfTagBooks(tag, offset, limit).getContent()
                .stream()
                .map(bookMapper::toDto)
                .collect(Collectors.toList()));
    }

    @PreAuthorize("hasAnyRole('USER','TEMP_USER')")
    @GetMapping("/books/author/{slug}")
    @ResponseBody
    public BooksDto getBooksAuthor(@RequestParam("offset") Integer offset,
                                   @RequestParam("limit") Integer limit,
                                   @PathVariable(value = "slug", required = true) String slug) {
        Author author = authorReadRepository.findBySlug(slug).orElseThrow(() -> new NoSuchAuthorException("No  such author with slug " + slug));
        return new BooksDto(bookRestService.getPageOfAuthorBooks(author, offset, limit).getContent()
                .stream()
                .map(bookMapper::toDto)
                .collect(Collectors.toList()));
    }

    @PreAuthorize("hasAnyRole('USER','TEMP_USER')")
    @GetMapping("/books/genre/{slug}")
    @ResponseBody
    public BooksDto getBooksGenre(@RequestParam("offset") Integer offset,
                                  @RequestParam("limit") Integer limit,
                                  @PathVariable(value = "slug", required = true) String slug) {
        Genre genre = genreReadRepository.findBySlug(slug).orElseThrow(() -> new NoSuchGenreException("No such genre with slug " + slug));
        return new BooksDto(bookRestService.getPageOfGenreBooks(genre, offset, limit).getContent()
                .stream()
                .map(bookMapper::toDto)
                .collect(Collectors.toList()));
    }

    @PreAuthorize("hasAnyRole('USER','TEMP_USER')")
    @GetMapping("/books/recommended")
    @ResponseBody
    public BooksDto getBooksRecommended(@RequestParam("offset") Integer offset,
                                        @RequestParam("limit") Integer limit) {

        return new BooksDto(bookRestService.getPageOfRecommendedBooks(offset, limit).getContent()
                .stream()
                .map(bookMapper::toDto)
                .collect(Collectors.toList()));
    }

    @PreAuthorize("hasAnyRole('USER','TEMP_USER')")
    @GetMapping("/books/popular")
    @ResponseBody
    public BooksDto getBooksPopular(@RequestParam("offset") Integer offset,
                                    @RequestParam("limit") Integer limit) {

        return new BooksDto(bookRestService.getPageOfPopularBooks(offset, limit).getContent()
                .stream()
                .map(bookMapper::toDto)
                .collect(Collectors.toList()));
    }


    @PreAuthorize("hasAnyRole('USER')")
    @GetMapping("/books/viewed")
    @ResponseBody
    public BooksDto getViewedUserBooks(@RequestParam("offset") Integer offset,
                                    @RequestParam("limit") Integer limit) {
        User user = userRegisterService.getCurrentUser();
        return new BooksDto(bookRestService.getPageBooksPageByUserAndTypeCode(user, "VIEWED", offset, limit).getContent()
                .stream()
                .map(bookMapper::toDto)
                .collect(Collectors.toList()));
    }

    @PreAuthorize("hasAnyRole('USER','TEMP_USER')")
    @GetMapping("/books/recent")
    @ResponseBody
    public BooksDto getBooksRecent(@RequestParam("offset") Integer offset,
                                   @RequestParam("limit") Integer limit,
                                   @RequestParam("from") String fromDate,
                                   @RequestParam("to") String toDate) {
        LocalDate from = LocalDate.parse(fromDate, DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        LocalDate to = LocalDate.parse(toDate, DateTimeFormatter.ofPattern("dd.MM.yyyy"));

        return new BooksDto(bookRestService.getPageOfRecentBooks(from, to, offset, limit).getContent()
                .stream()
                .map(bookMapper::toDto)
                .collect(Collectors.toList()));
    }

    @PreAuthorize("hasAnyRole('USER','TEMP_USER')")
    @GetMapping("/search/{searchWord}")
    @ResponseBody
    public BooksDto getNextSearchPage(
            @RequestParam("offset") Integer offset,
            @RequestParam("limit") Integer limit,
            @PathVariable(value = "searchWord", required = true) String searchWord){
        return new BooksDto(bookRestService.getPageOfSearchResultBooks(searchWord, offset, limit).getContent()
                .stream()
                .map(bookMapper::toDto)
                .collect(Collectors.toList()));
    }

}
