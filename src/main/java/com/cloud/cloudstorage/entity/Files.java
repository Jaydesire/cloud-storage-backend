package com.cloud.cloudstorage.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Builder
@Table(name = "Files")
public class Files {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;

    @Column(nullable = false)
    private String filename;

    private Long size;

    @JsonIgnore
    @Column(nullable = false)
    private String filepath;

    @JsonIgnore
    @OneToOne
    private Users userEntity;
}
