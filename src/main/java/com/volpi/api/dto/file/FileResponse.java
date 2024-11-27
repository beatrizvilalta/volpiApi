package com.volpi.api.dto.file;

public record FileResponse(
        long id,
        String fileName,
        String previewImageName,
        String fileUrl,
        String previewImageUrl
) {
}
