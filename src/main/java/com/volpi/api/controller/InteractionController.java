package com.volpi.api.controller;

import com.volpi.api.dto.InteractionRequest;
import com.volpi.api.model.enums.InteractionType;
import com.volpi.api.service.InteractionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/interaction")
@RequiredArgsConstructor
public class InteractionController {

    private final InteractionService interactionService;

    @PostMapping()
    public void createNewInteraction(@RequestBody InteractionRequest interactionRequest) {
        interactionService.createInteraction(interactionRequest.userId(),
                interactionRequest.postId(),
                InteractionType.valueOf(interactionRequest.interactionType()));
    }

    @DeleteMapping()
    public void disable(@RequestBody InteractionRequest interactionRequest) {
        interactionService.deleteInteraction(interactionRequest.userId(),
                interactionRequest.postId(),
                InteractionType.valueOf(interactionRequest.interactionType()));
    }
}
