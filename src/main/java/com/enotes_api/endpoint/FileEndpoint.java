package com.enotes_api.endpoint;

import com.enotes_api.exception.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.net.MalformedURLException;

@RequestMapping("/api/v1/files")
public interface FileEndpoint {

    @GetMapping("/{file-id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    ResponseEntity<?> downloadFile(@PathVariable(name = "file-id") Long fileId) throws ResourceNotFoundException,
            MalformedURLException;

}
