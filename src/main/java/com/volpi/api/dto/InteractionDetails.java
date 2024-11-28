package com.volpi.api.dto;

public record InteractionDetails(
        boolean isSaved,
        boolean isSupported,
        int saveCount,
        int supportCount
) {}
