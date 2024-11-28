package com.volpi.api.dto;

public record InteractionRequest(
        Long userId,
        Long postId,
        String interactionType
) {
}
