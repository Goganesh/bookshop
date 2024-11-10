package com.goganesh.bookshop.common.service.impl;

import com.goganesh.bookshop.common.service.FileStorageService;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileStorageServiceImpl implements FileStorageService {

    public String saveFile(InputStream initialStream, String directory, String originalFileName) throws IOException {
        Path sourceDirectory = Paths.get(directory);
        if (!new File(sourceDirectory.toString()).exists()) {
            Files.createDirectories(sourceDirectory);
        }

        byte[] buffer = new byte[initialStream.available()];
        initialStream.read(buffer);

        Path path = Paths.get(sourceDirectory.toString(), originalFileName);
        Files.createFile(path);
        File targetFile = new File(path.toString());

        try (OutputStream outStream = new FileOutputStream(targetFile)) {
            outStream.write(buffer);
        }

        return path.toString();
    }
}
