package com.goganesh.bookshop.webapi.client.controller;

import com.goganesh.bookshop.common.service.ImageService;
import com.goganesh.bookshop.model.domain.Book;
import com.goganesh.bookshop.model.service.BookReadRepository;
import com.goganesh.bookshop.model.service.BookWriteRepository;
import com.goganesh.bookshop.webapi.client.exception.NoSuchBookException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1")
public class FileUploadController {

    private final BookWriteRepository bookWriteRepository;
    private final BookReadRepository bookReadRepository;
    private final ImageService imageService;


    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping("/books/{id}/image")
    public ResponseEntity<Void> changeCoverForBook(@RequestParam("image") MultipartFile file,
                                             @PathVariable("id") Integer id) throws IOException {
        Book book = bookReadRepository.findById(id).orElseThrow(() -> new NoSuchBookException("No such book with id - " + id));

        InputStream inputStream = file.getInputStream();
        String originalFileName = file.getOriginalFilename();

        String imagePath = imageService.saveBookImage(inputStream, book, originalFileName);
        book.setImage(imagePath);
        bookWriteRepository.save(book);

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
