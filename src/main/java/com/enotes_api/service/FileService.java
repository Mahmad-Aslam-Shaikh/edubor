package com.enotes_api.service;

import com.enotes_api.entity.FileEntity;
import com.enotes_api.exception.FileUploadFailedException;
import com.enotes_api.exception.InvalidFileException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileService {

    FileEntity saveFile(MultipartFile multipartFile) throws IOException, FileUploadFailedException, InvalidFileException;

}
