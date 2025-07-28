package com.enotes_api.service;

import com.enotes_api.entity.FileEntity;
import com.enotes_api.exception.FileUploadFailedException;
import com.enotes_api.exception.InvalidFileException;
import com.enotes_api.exception.ResourceNotFoundException;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;

public interface FileService {

    FileEntity saveFile(MultipartFile multipartFile) throws IOException, FileUploadFailedException, InvalidFileException;

    FileEntity getFile(Long fileId) throws ResourceNotFoundException;

    Resource downloadFile(FileEntity file) throws MalformedURLException, ResourceNotFoundException;

}
