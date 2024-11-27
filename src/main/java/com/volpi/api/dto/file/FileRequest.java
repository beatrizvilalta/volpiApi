package com.volpi.api.dto.file;

import org.springframework.web.multipart.MultipartFile;

public record FileRequest(
        MultipartFile previewImage,
        MultipartFile file
) {}
