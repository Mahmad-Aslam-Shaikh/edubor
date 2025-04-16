package com.enotes_api.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "FILES")
public class FileEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "FILE_ID")
    private Long id;

    @Column(name = "ORIGINAL_FILE_NAME", nullable = false)
    private String originalFileName;

    @Column(name = "DISPLAY_FILE_NAME", nullable = false)
    private String displayFileName;

    @Column(name = "UPLOAD_FILE_NAME", nullable = false)
    private String uploadFileName;

    @Column(name = "FILE_PATH", length = 250, nullable = false)
    private String path;

    @Column(name = "FILE_SIZE", nullable = false)
    private Long size;

}