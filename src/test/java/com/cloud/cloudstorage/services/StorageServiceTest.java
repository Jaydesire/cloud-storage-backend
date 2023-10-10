package com.cloud.cloudstorage.services;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.mock.web.MockMultipartFile;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class StorageServiceTest {

    static String fileName = "321";
    static String textFile = "test text";
    static String newFileName = "New321";
    static Path tempDir = Path.of("./storage/TempForTests/");


    @Autowired
    StorageService storageService;

    @Test
    void saveSuccess() throws IOException {
        MockMultipartFile mockMultipartFile = new MockMultipartFile(fileName, textFile.getBytes(StandardCharsets.UTF_8));
        storageService.saveFile(mockMultipartFile.getBytes(), tempDir.resolve(fileName));

        Assertions.assertLinesMatch(Collections.singletonList(textFile),
                Files.readAllLines(tempDir.resolve(fileName)));
    }

    @Test
    void loadSuccess() throws IOException {
        File tempFile = createFile();
        Resource resource = storageService.loadFile(tempFile.toPath());

        Assertions.assertLinesMatch(Collections.singletonList(textFile),
                Files.readAllLines(resource.getFile().toPath()));
    }

    @Test
    void renameSuccess() throws IOException {
        File tempFile = createFile();
        storageService.renameFile(tempFile.toPath(), newFileName);
        assertTrue(Files.exists(tempDir.resolve(newFileName)));
    }

    @Test
    void deleteSuccess() throws IOException {
        File tempFile = createFile();
        storageService.deleteFile(tempFile.toPath());
        assertFalse(Files.exists(tempFile.toPath()));
    }

    private File createFile() throws IOException {
        File tempFile = new File(tempDir.toFile(), fileName);
        Files.write(tempFile.toPath(), textFile.getBytes());
        return tempFile;
    }

    @AfterEach
    public void clearStorage() throws IOException {
        for (File myFile : new File("./storage/TempForTests/").listFiles())
            if (myFile.isFile()) myFile.delete();
    }


}