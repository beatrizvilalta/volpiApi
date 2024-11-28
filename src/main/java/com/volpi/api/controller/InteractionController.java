package com.volpi.api.controller;

import com.volpi.api.dto.InteractionRequest;
import com.volpi.api.model.enums.InteractionType;
import com.volpi.api.service.InteractionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/interaction")
@RequiredArgsConstructor
public class InteractionController {

    private final InteractionService interactionService;

    @PostMapping()
    public void save(InteractionRequest interactionRequest) {
        createInteraction(interactionRequest);
    }

    @PutMapping("/save")
    public void deleteSave(Long userId, Long postId) {
        interactionService.deleteInteraction(userId, postId, InteractionType.SAVE);
    }

    @PutMapping("/disable")
    public void disable(InteractionRequest interactionRequest) {
        interactionService.deleteInteraction(interactionRequest.userId(),
                interactionRequest.postId(),
                InteractionType.valueOf(interactionRequest.interactionType()));
    }

    @PutMapping("/enable")
    public void enable(InteractionRequest interactionRequest) {
        createInteraction(interactionRequest);
    }

    private void createInteraction(InteractionRequest interactionRequest) {
        interactionService.createInteraction(interactionRequest.userId(),
                interactionRequest.postId(),
                InteractionType.valueOf(interactionRequest.interactionType()));
    }
}
