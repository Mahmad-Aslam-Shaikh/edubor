package com.enotes_api.utility;

import com.enotes_api.exception.FileUploadFailedException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Component
public class FileUtil {

    @Value("${file.path.upload}")
    private String filePath;

    public String uploadFile(MultipartFile file, String uploadFileName) throws IOException, FileUploadFailedException {
        if (Files.notExists(Path.of(filePath))) {
            Files.createDirectories(Paths.get(filePath));
        }
        String filePathWithName = filePath.concat(uploadFileName);
        long fileSize = Files.copy(file.getInputStream(), Paths.get(filePathWithName),
                StandardCopyOption.REPLACE_EXISTING);
        if (fileSize != 0)
            return filePathWithName;

        throw new FileUploadFailedException("File Upload Failed!");
    }

    public MediaType getContentType(String fileName) {
        String[] fileNameAndExtension = fileName.split("\\.");
        String extension = fileNameAndExtension[1];

        return switch (extension.toLowerCase()) {
            case "png" -> MediaType.IMAGE_PNG;
            case "jpg", "jpeg" -> MediaType.IMAGE_JPEG;
            case "pdf" -> MediaType.APPLICATION_PDF;
            case "docx" -> MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.wordprocessingml" +
                    ".document");
            case "txt" -> MediaType.TEXT_PLAIN;
            default -> MediaType.APPLICATION_OCTET_STREAM;
        };
    }
}
