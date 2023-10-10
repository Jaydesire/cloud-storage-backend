package com.cloud.cloudstorage.services;
import com.cloud.cloudstorage.entity.Files;
import com.cloud.cloudstorage.entity.Users;
import com.cloud.cloudstorage.repository.FileRepository;
import com.cloud.cloudstorage.repository.UserRepository;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

@Service
public class FileServiceImpl implements FileService {

    private final static Path rootPath = Path.of("./storage");
    private final StorageService storageService;
    private final UserRepository userRepository;
    private final FileRepository fileRepository;

    public FileServiceImpl(StorageService storageService, UserRepository userRepository, FileRepository fileRepository) {
        this.storageService = storageService;
        this.userRepository = userRepository;
        this.fileRepository = fileRepository;
    }

    @Transactional
    @Override
    public void saveFile(String filename, byte[] fileBytes, long fileSize) throws IOException {
        Files files = Files.builder()
                .filename(filename)
                .userEntity(getUserFromContext())
                .size(fileSize)
                .filepath(getFilePath(filename).toAbsolutePath().toString())
                .build();
        storageService.saveFile(fileBytes, getFilePath(filename).toAbsolutePath());
        fileRepository.save(files);
    }

    @Transactional
    @Override
    public void deleteFile(String filename) throws IOException {
        storageService.deleteFile(getFilePath(filename));
        fileRepository.removeFileByFilenameAndUserEntity(filename, getUserFromContext());
    }

    @Override
    public Resource getFile(String filename) throws IOException {
        return storageService.loadFile(getFilePath(filename));
    }

    @Override
    public List<Files> getList(int limit) {
        Users userEntity = getUserFromContext();
        return fileRepository.findAllByUserEntity_Login(userEntity.getLogin(), Sort.by("filename"));
    }

    private Users getUserFromContext() {
        String login = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByLogin(login).orElseThrow(() -> new UsernameNotFoundException(login));
    }

    private Path getFilePath(String filename) {
        return rootPath.resolve(getUserFromContext().getLogin()).resolve(filename);
    }

    @Override
    public void renameFile(String filename, String newFileName) throws IOException {
        Files files = fileRepository.findByFilenameAndUserEntity(filename, getUserFromContext());
        String newFilePath = storageService.renameFile(getFilePath(filename), newFileName);
        if (newFilePath != null) {
            files.setFilename(newFileName);
            files.setFilepath(newFilePath);
            fileRepository.saveAndFlush(files);
        } else {
            throw new IOException(String.format("Error rename"));
        }
    }
}
