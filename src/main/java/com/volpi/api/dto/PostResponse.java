package com.volpi.api.dto;

import java.util.Date;

public record PostResponse(
        Long id,
        Long userId,
        String username,
        Date dateCreated,
        String title,
        String description,
        String subject,
        String schoolLevel,
        String grade,
        String previewImageUrl,
        String fileUrl,
        boolean isSupported,
        boolean isSaved,
        int supportCount,
        int saveCount
) {
}
