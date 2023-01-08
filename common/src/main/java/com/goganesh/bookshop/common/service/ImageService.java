package com.goganesh.bookshop.common.service;

import com.goganesh.bookshop.model.domain.Book;

import java.io.IOException;
import java.io.InputStream;

public interface ImageService {

    String saveBookImage(InputStream initialStream, Book book, String originalFileName) throws IOException;
}
