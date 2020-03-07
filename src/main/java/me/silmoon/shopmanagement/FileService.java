package me.silmoon.shopmanagement;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

@Service
public class FileService {
    @Value("${file.upload.path:${user.home}}")
    public String uploadPath;

    public void uploadFile(MultipartFile file, String timestamp) {
        try {
            String originalName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
            String fileExtension = originalName.substring(originalName.lastIndexOf("."));
            Path copyLocation = Paths
                    .get(uploadPath + File.separator + timestamp + fileExtension );
            Files.copy(file.getInputStream(), copyLocation, StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
