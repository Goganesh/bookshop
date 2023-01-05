package com.goganesh.bookshop.webapi.client.controller;

import com.goganesh.bookshop.common.service.ImageService;
import com.goganesh.bookshop.model.domain.Author;
import com.goganesh.bookshop.model.domain.Book;
import com.goganesh.bookshop.model.service.AuthorReadRepository;
import com.goganesh.bookshop.model.service.AuthorWriteRepository;
import com.goganesh.bookshop.model.service.BookReadRepository;
import com.goganesh.bookshop.model.service.BookWriteRepository;
import com.goganesh.bookshop.webapi.client.dto.AuthorChangeDto;
import com.goganesh.bookshop.webapi.client.dto.BookChangeDto;
import com.goganesh.bookshop.webapi.client.dto.BookResponseDto;
import com.goganesh.bookshop.webapi.client.exception.NoSuchAuthorException;
import com.goganesh.bookshop.webapi.client.exception.NoSuchBookException;
import com.goganesh.bookshop.webapi.client.mapper.AuthorApiMapper;
import com.goganesh.bookshop.webapi.client.mapper.BookApiMapper;
import com.goganesh.bookshop.webapi.client.validation.NoPathTraversal;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/admin/")
public class AdminController {

    private final BookWriteRepository bookWriteRepository;
    private final BookReadRepository bookReadRepository;
    private final AuthorReadRepository authorReadRepository;
    private final AuthorWriteRepository authorWriteRepository;
    private final AuthorApiMapper authorApiMapper;
    private final BookApiMapper bookApiMapper;
    private final ImageService imageService;

    /*
    todo
    добавление книг;
    модерацию отзывов и пользователей;
    редактирование книжных полок пользователей (например, чтобы потом была возможность добавлять подарки в рамках промоакций).

    todo
    решить как организовать ответ общий - TemplateResponse
    добавить validation api
     */

    @Validated
    @GetMapping("test/{path}")
    public String test(@Valid @PathVariable @NoPathTraversal String path) {
        return path;
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PatchMapping("books")
    public BookResponseDto changeBook(@RequestBody BookChangeDto bookChangeDto) {
        Book book = bookReadRepository.findById(bookChangeDto.getId())
                .orElseThrow(() -> new NoSuchBookException("No such book with id - " + bookChangeDto.getId()));

        bookWriteRepository.save(book);

        return bookApiMapper.toDto(bookReadRepository.findById(book.getId()).get());
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @DeleteMapping("books/{id}")
    public void deleteBook(@PathVariable(value = "id") Integer id) {
        bookWriteRepository.deleteById(id);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping("books/{slug}/img")
    public void changeCoverForBook(@RequestParam("file") MultipartFile file,
                                   @PathVariable("slug") String slug) throws IOException {
        InputStream inputStream = file.getInputStream();
        String originalFileName = file.getOriginalFilename();

        String imagePath = imageService.saveBookImage(inputStream, slug, originalFileName);

        Book book = bookReadRepository.findBySlug(slug).orElseThrow(() -> new NoSuchBookException("No such book with slug - " + slug));
        book.setImage(imagePath);
        bookWriteRepository.save(book);
    }


    /*
    @PreAuthorize("hasAnyRole('USER','TEMP_USER')")
    @GetMapping("/books/download/{hash}")
    public ResponseEntity<ByteArrayResource> bookFile(@PathVariable("hash")String hash) throws IOException {
        Path path = resourceStorageService.getBookFilePath(hash);
        Logger.getLogger(this.getClass().getSimpleName()).info("book file path: " + path);

        MediaType mediaType = resourceStorageService.getBookFileMime(hash);
        Logger.getLogger(this.getClass().getSimpleName()).info("book file mime type: " + mediaType);

        byte[] data =resourceStorageService.getBookFileByteArray(hash);
        Logger.getLogger(this.getClass().getSimpleName()).info("book file data len: "+data.length);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + path.getFileName().toString())
                .contentType(mediaType)
                .contentLength(data.length)
                .body(new ByteArrayResource(data));
    }
     */

}
