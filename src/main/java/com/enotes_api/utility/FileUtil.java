package com.enotes_api.utility;

import com.enotes_api.exception.FileUploadFailedException;
import org.springframework.beans.factory.annotation.Value;
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
        long fileSize = Files.copy(file.getInputStream(), Paths.get(filePathWithName), StandardCopyOption.REPLACE_EXISTING);
        if (fileSize != 0)
            return filePathWithName;

        throw new FileUploadFailedException("File Upload Failed!");
    }

}
