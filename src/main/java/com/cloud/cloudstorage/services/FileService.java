package com.cloud.cloudstorage.services;

import com.cloud.cloudstorage.entity.Files;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.util.List;

public interface FileService {

    void saveFile(String filename, byte[] fileBytes, long fileSize) throws IOException;
    void deleteFile(String filename) throws IOException;
    Resource getFile(String filename) throws IOException;
    List<Files> getList(int limit);
    void renameFile(String filename, String newFileName) throws IOException;
}
