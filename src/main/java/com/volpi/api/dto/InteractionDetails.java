package com.volpi.api.dto;

public record InteractionDetails(
        Boolean isSaved,
        Boolean isSupported,
        int saveCount,
        int supportCount
) {}
