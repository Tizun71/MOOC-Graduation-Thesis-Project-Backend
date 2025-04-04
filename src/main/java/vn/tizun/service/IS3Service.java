package vn.tizun.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface IS3Service {
    String uploadFileToS3(String folderName, MultipartFile file);
}
