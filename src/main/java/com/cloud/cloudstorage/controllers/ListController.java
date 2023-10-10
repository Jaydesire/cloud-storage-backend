package com.cloud.cloudstorage.controllers;

import com.cloud.cloudstorage.entity.Files;
import com.cloud.cloudstorage.services.FileService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping(path = "/list")
public class ListController {

    private final FileService fileService;

    public ListController(FileService fileService) {
        this.fileService = fileService;
    }

    @GetMapping
    List<Files> getList(@RequestParam int limit) {
        return fileService.getList(limit);
    }
}
