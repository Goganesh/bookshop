package com.goganesh.bookshop.common.service;

import java.io.IOException;
import java.io.InputStream;

public interface ImageService {

    String saveBookImage(InputStream initialStream, String slug, String originalFileName) throws IOException;
}
