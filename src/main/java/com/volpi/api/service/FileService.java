package com.volpi.api.service;

import com.volpi.api.dto.file.FileRequest;
import com.volpi.api.dto.file.FileResponse;
import com.volpi.api.model.File;
import com.volpi.api.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.InputStream;

@Service
@RequiredArgsConstructor
public class FileService {

    private final FileRepository fileRepository;
    private final S3Service s3Service;
    private final String baseUrl = "https://volpi-files.s3.us-east-1.amazonaws.com/";

    public FileResponse createFile(FileRequest fileRequest) {
       try {
           InputStream fileStream = fileRequest.file().getInputStream();
           InputStream previewImageStream = fileRequest.previewImage().getInputStream();
           String fileName = fileRequest.file().getOriginalFilename();
           String previewImageName = fileRequest.previewImage().getOriginalFilename();

           s3Service.uploadFile(fileName, fileStream);
           s3Service.uploadFile(previewImageName, previewImageStream);

           File file = File.builder()
                   .fileName(fileRequest.file().getOriginalFilename())
                   .previewImageName(fileRequest.previewImage().getOriginalFilename())
                   .build();
           fileRepository.save(file);
           return new FileResponse(file.getId(), fileName, previewImageName, baseUrl + fileName, baseUrl + previewImageName);

       } catch (Exception e) {
           throw new InternalError("File creation failed: " + e.getMessage(), e);
       }
    }

    private File getFile(long id) {
        return fileRepository.findById(id).orElseThrow(() -> new RuntimeException("File not found"));
    }

    public byte[] downloadFile(long id) {
        File file = getFile(id);
        String fileName = file.getFileName();
        try {
            InputStream fileStream = s3Service.downloadFile(fileName);
            return fileStream.readAllBytes();
        } catch (Exception e) {
            throw new InternalError("File download failed: " + e.getMessage(), e);
        }
    }

    public byte[] getPreviewImageByFileId(Long id) {
        File file = getFile(id);
        String previewImageName = file.getPreviewImageName();
        try {
            InputStream previewImageStream = s3Service.downloadFile(previewImageName);
            return previewImageStream.readAllBytes();
        } catch (Exception e) {
            throw new InternalError("Preview image download failed: " + e.getMessage(), e);
        }
    }
}
