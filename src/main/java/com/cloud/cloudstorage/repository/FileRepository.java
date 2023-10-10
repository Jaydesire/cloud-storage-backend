package com.cloud.cloudstorage.repository;

import com.cloud.cloudstorage.entity.Files;
import com.cloud.cloudstorage.entity.Users;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileRepository extends JpaRepository<Files, Long> {

    Files findByFilenameAndUserEntity(String filename, Users userEntity);
    List<Files> findAllByUserEntity_Login(String login, Sort sort);
    void removeFileByFilenameAndUserEntity(String fileName, Users userEntity);
}
