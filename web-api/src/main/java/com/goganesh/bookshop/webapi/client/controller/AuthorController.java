package com.goganesh.bookshop.webapi.client.controller;

import com.goganesh.bookshop.model.domain.Author;
import com.goganesh.bookshop.webapi.client.dto.AuthorApiRequestDto;
import com.goganesh.bookshop.webapi.client.dto.AuthorApiResponseDto;
import com.goganesh.bookshop.webapi.client.exception.NoSuchAuthorException;
import com.goganesh.bookshop.webapi.client.mapper.AuthorApiMapper;
import com.goganesh.bookshop.webapi.client.service.AuthorRestService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
//@PreAuthorize("hasAnyRole('USER')")
@AllArgsConstructor
@RequestMapping("/api/v1/authors")
public class AuthorController {

    private final AuthorRestService authorRestService;
    private final AuthorApiMapper authorApiMapper;

    @GetMapping()
    public Page<AuthorApiResponseDto> getAuthorsPage(@PageableDefault(value = 20) Pageable pageable) {
        return authorRestService.findAll(pageable).map(authorApiMapper::toDto);
    }

    @GetMapping("/{id}")
    public AuthorApiResponseDto getGenresPage(@PathVariable("id") Integer id) {
        Author author = authorRestService.findById(id).orElseThrow(() -> new NoSuchAuthorException("No such author with id " + id));
        return authorApiMapper.toDto(author);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGenre(@PathVariable("id") Integer id) {
        Author author = authorRestService.findById(id).orElseThrow(() -> new NoSuchAuthorException("No such author with id " + id));
        authorRestService.delete(author);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping
    public AuthorApiResponseDto postGenre(@Validated @RequestBody AuthorApiRequestDto authorApiRequestDto) {
        Author existedAuthor = null;
        
        if (authorApiRequestDto.getId() == -1 || Objects.isNull(authorApiRequestDto.getId())) {
            authorApiRequestDto.setId(null);
        } else {
            existedAuthor = authorRestService.findById(authorApiRequestDto.getId())
                    .orElseThrow(() -> new NoSuchAuthorException("No such author with id " + authorApiRequestDto.getId()));
        }
        
        Author author = authorApiMapper.toModel(authorApiRequestDto);
        if (Objects.nonNull(existedAuthor)) {
            author.setPhoto(existedAuthor.getPhoto());
        }
        
        author = authorRestService.save(author);

        return authorApiMapper.toDto(author);
    }
}
