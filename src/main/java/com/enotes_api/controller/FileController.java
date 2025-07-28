package com.enotes_api.controller;

import com.enotes_api.entity.FileEntity;
import com.enotes_api.exception.ResourceNotFoundException;
import com.enotes_api.service.FileService;
import com.enotes_api.utility.FileUtil;
import lombok.AllArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.MalformedURLException;

@RestController
@RequestMapping("/api/v1/files")
@AllArgsConstructor
public class FileController {

    private FileService fileService;

    private FileUtil fileUtil;

    @GetMapping("/{file-id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<?> downloadFile(@PathVariable(name = "file-id") Long fileId) throws ResourceNotFoundException, MalformedURLException {
        FileEntity file = fileService.getFile(fileId);
        Resource resource = fileService.downloadFile(file);

        MediaType contentType = fileUtil.getContentType(file.getOriginalFileName());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(contentType);
        headers.setContentDispositionFormData("attachment", file.getOriginalFileName());

        return ResponseEntity.ok().contentType(contentType).headers(headers).body(resource);
    }

}
