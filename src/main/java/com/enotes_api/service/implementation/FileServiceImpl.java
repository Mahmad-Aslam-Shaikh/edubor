package com.enotes_api.service.implementation;

import com.enotes_api.entity.FileEntity;
import com.enotes_api.messages.ExceptionMessages;
import com.enotes_api.exception.FileUploadFailedException;
import com.enotes_api.exception.InvalidFileException;
import com.enotes_api.exception.ResourceNotFoundException;
import com.enotes_api.repository.FileRepository;
import com.enotes_api.service.FileService;
import com.enotes_api.utility.FileUtil;
import lombok.AllArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class FileServiceImpl implements FileService {

    private FileRepository fileRepository;

    private FileUtil fileUtil;

    static List<String> allowedExtensions = List.of("jpg", "png", "pdf", "docx", "txt");

    @Override
    public FileEntity saveFile(MultipartFile multipartFile) throws IOException, FileUploadFailedException,
            InvalidFileException {
        validateFileExtension(multipartFile);

        FileEntity file = new FileEntity();
        file.setOriginalFileName(multipartFile.getOriginalFilename());

        String[] fileNameAndExtension = multipartFile.getOriginalFilename().split("\\.");
        String originalFileName = fileNameAndExtension[0];
        String extension = "." + fileNameAndExtension[1];
        file.setDisplayFileName(getDisplayFileName(originalFileName, extension));

        String uploadFileName = UUID.randomUUID().toString() + extension;
        file.setUploadFileName(uploadFileName);
        file.setSize(multipartFile.getSize());

        String filePath = fileUtil.uploadFile(multipartFile, uploadFileName);
        file.setPath(filePath);

        return fileRepository.save(file);
    }

    private String getDisplayFileName(String originalFileName, String extension) {
        String displayFileName = originalFileName;

        if (displayFileName.length() > 8)
            displayFileName = displayFileName.substring(0, 7);

        displayFileName = displayFileName + extension;
        return displayFileName;
    }

    private void validateFileExtension(MultipartFile multipartFile) throws InvalidFileException {
        String originalFilename = multipartFile.getOriginalFilename();

        if (originalFilename == null || !originalFilename.contains(".")) {
            throw new InvalidFileException(ExceptionMessages.FILE_EXTENSION_MISSING_MESSAGE);
        }

        String[] parts = originalFilename.split("\\.");
        if (parts.length != 2) {
            throw new InvalidFileException(ExceptionMessages.INVALID_FILE_NAME_MESSAGE);
        }

        String extension = originalFilename.substring(originalFilename.lastIndexOf('.') + 1).toLowerCase();
        if (!allowedExtensions.contains(extension)) {
            throw new InvalidFileException(ExceptionMessages.UNSUPPORTED_FILE_EXTENSION_MESSAGE + extension + " " +
                    "Supported File extensions: "
                    + allowedExtensions);
        }
    }

    @Override
    public FileEntity getFile(Long fileId) throws ResourceNotFoundException {
        return fileRepository.findById(fileId).orElseThrow(() -> new ResourceNotFoundException(ExceptionMessages.FILE_NOT_EXISTS_MESSAGE));
    }

    @Override
    public Resource downloadFile(FileEntity file) throws MalformedURLException, ResourceNotFoundException {
        File directory = new File(file.getPath());
        if (directory.exists()) {
            return new UrlResource(directory.toURI());
        }
        throw new ResourceNotFoundException(ExceptionMessages.UNABLE_TO_PROCESS_FILE);
    }


}
