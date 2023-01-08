package com.goganesh.bookshop.common.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public interface FileStorageService {

    String saveFile(InputStream initialStream, String directory, String originalFileName) throws IOException;
}
