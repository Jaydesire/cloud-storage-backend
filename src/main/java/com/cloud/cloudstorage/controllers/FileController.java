package com.cloud.cloudstorage.controllers;

import com.cloud.cloudstorage.models.Error;
import com.cloud.cloudstorage.models.File;
import com.cloud.cloudstorage.services.FileService;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping(value = "/file")
public class FileController {

    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping()
    public ResponseEntity<?> postFile(@RequestParam String filename,
                                      @RequestPart MultipartFile file) {
        try {
            fileService.saveFile(filename, file.getBytes(), file.getSize());
        } catch (IOException e) {
            return ResponseEntity.badRequest().body(new Error(e.getMessage(), -32002));
        }
        return ResponseEntity.ok().body(null);
    }

    @DeleteMapping()
    public ResponseEntity<?> deleteFile(@RequestParam String filename) {
        try {
            fileService.deleteFile(filename);
        } catch (IOException e) {
            return ResponseEntity.badRequest().body(new Error(e.getMessage(), -32003));
        }
        return ResponseEntity.ok().body(null);
    }

    @GetMapping()
    public ResponseEntity<?> getFile(@RequestParam String filename) {
        Resource resource = null;
        try {
            resource = fileService.getFile(filename);
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION,
                            "attachment; filename=\"" + resource.getFilename() + "\"")
                    .contentLength(resource.getFile().length())
                    .body(resource);
        } catch (IOException e) {
            return ResponseEntity.badRequest().body(new Error(e.getMessage(), -32004));
        }
    }

    @PutMapping()
    public ResponseEntity<?> updateFile(@RequestParam String filename, @RequestBody File newFile) {
        try {
            fileService.renameFile(filename, newFile.getFilename());
        } catch (IOException e) {
            return ResponseEntity.badRequest().body(new Error(e.getMessage(), -32005));
        }
        return ResponseEntity.ok().body(null);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Error> handleInternalServerErrors(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Error(e.getMessage(), 500));
    }
}
