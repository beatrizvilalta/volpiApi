package com.volpi.api.dto;

import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

public record PostRequest(
        @NotNull Long userId,
        @NotNull String title,
        String description,
        @NotNull String subject,
        @NotNull String schoolLevel,
        String grade,
        MultipartFile previewImage,
        MultipartFile file
) {
}
