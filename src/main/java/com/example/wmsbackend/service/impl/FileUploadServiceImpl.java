package com.example.wmsbackend.service.impl;

import cn.hutool.core.lang.UUID;
import com.example.wmsbackend.service.FileUploadService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileUploadServiceImpl implements FileUploadService {

    @Value("${file.upload-dir}")
    private String uploadDir;  // 上传目录

    // 文件上传并返回 URL
    public String uploadImage(MultipartFile file) throws IOException {
        // 生成唯一的文件名
        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();

        // 设置上传路径
        Path path = Paths.get(uploadDir, fileName);

        // 确保上传目录存在
        File uploadDirectory = new File(uploadDir);
        if (!uploadDirectory.exists()) {
            boolean dirsCreated = uploadDirectory.mkdirs();
            if (!dirsCreated) {
                throw new IOException("Failed to create upload directory: " + uploadDir);
            }
        }

        // 保存文件
        Files.copy(file.getInputStream(), path);

        // 返回文件的 URL
        return "/uploads/" + fileName;
    }
}

