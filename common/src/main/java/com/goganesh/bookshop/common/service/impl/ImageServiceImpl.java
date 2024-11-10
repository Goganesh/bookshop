package com.goganesh.bookshop.common.service.impl;

import com.goganesh.bookshop.common.service.FileStorageService;
import com.goganesh.bookshop.common.service.ImageService;
import com.goganesh.bookshop.model.domain.Book;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;

@Transactional
@Service
public class ImageServiceImpl implements ImageService {

    private final FileStorageService fileStorageService;

    private final String bookImageDirectory;


    public ImageServiceImpl(FileStorageService fileStorageService,
                            @Value("${com.goganesh.bookshop.filestorage-service.book.image.directory}") String bookImageDirectory) {
        this.fileStorageService = fileStorageService;
        this.bookImageDirectory = bookImageDirectory;
    }

    @Override
    public String saveBookImage(InputStream initialStream, Book book, String originalFileName) throws IOException {
        Path directory = Paths.get(bookImageDirectory, book.getId().toString());
        return fileStorageService.saveFile(initialStream, directory.toString(), originalFileName);
    }
}
