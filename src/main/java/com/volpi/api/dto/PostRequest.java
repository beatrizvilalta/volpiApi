package com.volpi.api.dto;

import org.springframework.web.multipart.MultipartFile;

public record PostRequest(
        Long userId,
        String title,
        String description,
        String subject,
        String schoolLevel,
        String grade,
        MultipartFile previewImage,
        MultipartFile file
) {
}
