package com.example.wmsbackend.controller;

import com.example.wmsbackend.service.FileUploadService;
import com.example.wmsbackend.util.ApiResponse;
import lombok.Getter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api")
public class FileUploadController {

    private final FileUploadService fileUploadService;

    public FileUploadController(FileUploadService fileUploadService) {
        this.fileUploadService = fileUploadService;
    }

    // 上传图片的接口
    @PostMapping("/upload")
    public ResponseEntity<Object> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            // 调用服务保存文件并返回文件 URL
            String fileUrl = fileUploadService.uploadImage(file);

            // 返回 ApiResponse 状态和图片 URL
            ApiResponse response = new ApiResponse("File uploaded successfully", true);
            return ResponseEntity.ok(new UploadResponse(response, fileUrl));
        } catch (IOException e) {
            ApiResponse response = new ApiResponse("Failed to upload file", false);
            return ResponseEntity.status(500).body(new UploadResponse(response, null));
        }
    }

    // 用于包装返回的 ApiResponse 和图片 URL
    @Getter
    public static class UploadResponse {
        private final ApiResponse apiResponse;
        private final String imageUrl;

        public UploadResponse(ApiResponse apiResponse, String imageUrl) {
            this.apiResponse = apiResponse;
            this.imageUrl = imageUrl;
        }
    }
}


