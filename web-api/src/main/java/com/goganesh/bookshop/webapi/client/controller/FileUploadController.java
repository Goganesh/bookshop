package com.goganesh.bookshop.webapi.client.controller;

import com.goganesh.bookshop.common.service.ImageService;
import com.goganesh.bookshop.model.domain.Book;
import com.goganesh.bookshop.model.repository.BookRepository;
import com.goganesh.bookshop.webapi.client.exception.NoSuchBookException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("/api/v1")
public class FileUploadController {
    private final BookRepository bookRepository;
    private final ImageService imageService;

    public FileUploadController(BookRepository bookRepository, ImageService imageService) {
        this.bookRepository = bookRepository;
        this.imageService = imageService;
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping("/books/{id}/image")
    public ResponseEntity<Void> changeCoverForBook(@RequestParam("image") MultipartFile file,
                                                   @PathVariable("id") Integer id) throws IOException {
        Book book = bookRepository.findById(id).orElseThrow(() -> new NoSuchBookException("No such book with id - " + id));

        //todo save with resources
        InputStream inputStream = file.getInputStream();
        String originalFileName = file.getOriginalFilename();

        String imagePath = imageService.saveBookImage(inputStream, book, originalFileName);
        book.setImage(imagePath);
        bookRepository.save(book);

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
