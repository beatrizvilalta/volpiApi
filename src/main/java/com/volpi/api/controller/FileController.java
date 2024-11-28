package com.volpi.api.controller;

import com.volpi.api.dto.file.FileRequest;
import com.volpi.api.dto.file.FileResponse;
import com.volpi.api.dto.file.FileUrlResponse;
import com.volpi.api.dto.file.PreviewImageResponse;
import com.volpi.api.service.FileService;
import com.volpi.api.service.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

@RestController
@RequestMapping("/file")
@RequiredArgsConstructor
public class FileController {

    private final S3Service s3Service;
    private final FileService fileService;


    @PostMapping()
    public ResponseEntity<FileResponse> createFile(FileRequest fileRequest) {
       return ResponseEntity.ok(fileService.createFile(fileRequest));
    }

    @GetMapping("/{id}")
    public ResponseEntity<FileResponse> getFileById(@PathVariable Long id) {
        return ResponseEntity.ok(fileService.getFileResponse(id));
    }

    @GetMapping("/{id}/download")
    public ResponseEntity<FileUrlResponse> downloadFileById(@PathVariable Long id) {
        return ResponseEntity.ok(fileService.downloadFile(id));
    }

    @GetMapping("/{id}/previewImage")
    public ResponseEntity<PreviewImageResponse> getPreviewImageByFileId(@PathVariable Long id) {
        return ResponseEntity.ok().body(fileService.getPreviewImageUrlByFileId(id));
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFileToS3(@RequestParam("file") MultipartFile file) {
        try (InputStream fileStream = file.getInputStream()) {
            s3Service.uploadFile(file.getOriginalFilename(), fileStream);
            return ResponseEntity.ok("File uploaded successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("File upload failed: " + e.getMessage());
        }
    }

    @GetMapping("/download/{fileName}")
    public ResponseEntity<byte[]> downloadFileToS3(@PathVariable String fileName) {
        try (InputStream fileStream = s3Service.downloadFile(fileName)) {
            byte[] fileContent = fileStream.readAllBytes();
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(fileContent);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}

