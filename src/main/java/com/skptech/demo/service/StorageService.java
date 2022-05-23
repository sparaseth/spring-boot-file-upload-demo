package com.skptech.demo.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.stream.Stream;

public interface StorageService {

    void init();

    Path load(String filename);

    Stream<Path> loadAll();

    Resource loadAsResource(String filename);

    void store(MultipartFile file);

    void deleteAll();

}
