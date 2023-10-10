package com.cloud.cloudstorage.services;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import java.io.IOException;

@SpringBootTest
@WithMockUser(username = "root", password = "root")
public class FileServiceTest {

    @Autowired
    FileService fileService;

    String fileName = "123";
    byte[] bytes = {};

    @Test
    void getFileTest() throws Exception{



        fileService.saveFile(fileName, bytes, bytes.length);
        System.out.println(fileService.getFile(fileName));
        Assertions.assertEquals(fileService.getFile(fileName).toString()
                .substring(fileService.getFile(fileName).toString().length() - fileName.length() - 1
                        , fileService.getFile(fileName).toString().length() -1)
                , fileName);
    }

    @AfterEach
    public void clearStorage() throws IOException {
        fileService.deleteFile(fileName);
    }

}
