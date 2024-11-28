package com.volpi.api.service;

import com.volpi.api.dto.file.FileRequest;
import com.volpi.api.dto.file.FileResponse;
import com.volpi.api.dto.file.FileUrlResponse;
import com.volpi.api.dto.file.PreviewImageResponse;
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

    public File createFile(FileRequest fileRequest) {
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
                    .fileUrl(baseUrl + fileName)
                    .previewImageUrl(baseUrl + previewImageName)
                    .build();
            fileRepository.save(file);
            return file;
        } catch (Exception e) {
            throw new InternalError("File creation failed: " + e.getMessage(), e);
        }
    }

    public FileResponse createFileRequest(FileRequest fileRequest) {
        File file = createFile(fileRequest);
        return new FileResponse(file.getId(), file.getFileName(), file.getPreviewImageName(),
                baseUrl + file.getFileName(), baseUrl + file.getPreviewImageName());
    }

    private File getFile(long id) {
        return fileRepository.findById(id).orElseThrow(() -> new RuntimeException("File not found"));
    }

    public FileResponse getFileResponse(long id) {
        File file = getFile(id);
        return new FileResponse(file.getId(), file.getFileName(), file.getPreviewImageName(), file.getFileUrl(), file.getPreviewImageUrl());
    }

    public FileUrlResponse downloadFile(long id) {
        File file = getFile(id);
        return new FileUrlResponse(file.getFileUrl());
    }

    public PreviewImageResponse getPreviewImageUrlByFileId(Long id) {
        File file = getFile(id);
        return new PreviewImageResponse(file.getPreviewImageName(), file.getPreviewImageUrl());
    }

    public File updateFile(long id, FileRequest fileRequest) {
        File file = getFile(id);
        try {
            InputStream fileStream = fileRequest.file().getInputStream();
            InputStream previewImageStream = fileRequest.previewImage().getInputStream();
            String fileName = fileRequest.file().getOriginalFilename();
            String previewImageName = fileRequest.previewImage().getOriginalFilename();

            s3Service.uploadFile(fileName, fileStream);
            s3Service.uploadFile(previewImageName, previewImageStream);

            file.setFileName(fileName);
            file.setPreviewImageName(previewImageName);
            file.setFileUrl(baseUrl + fileName);
            file.setPreviewImageUrl(baseUrl + previewImageName);
            fileRepository.save(file);
            return file;
        } catch (Exception e) {
            throw new InternalError("File update failed: " + e.getMessage(), e);
        }
    }
}
