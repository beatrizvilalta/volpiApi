package com.volpi.api.service;

import com.volpi.api.dto.InteractionDetails;
import com.volpi.api.dto.InteractionDetailsInterface;
import com.volpi.api.model.Interaction;
import com.volpi.api.model.Post;
import com.volpi.api.model.User;
import com.volpi.api.model.enums.InteractionType;
import com.volpi.api.repository.InteractionRepository;
import com.volpi.api.repository.PostRepository;
import com.volpi.api.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class InteractionService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final InteractionRepository interactionRepository;

    public InteractionService(UserRepository userRepository, PostRepository postRepository, InteractionRepository interactionRepository) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.interactionRepository = interactionRepository;
    }

    public void createInteraction(Long userId, Long postId, InteractionType type) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Post not found"));

        Interaction interaction = Interaction.builder()
                .type(type)
                .user(user)
                .post(post)
                .isInteractionEnabled(true)
                .build();
        interactionRepository.save(interaction);
    }

    public void deleteInteraction(Long userId, Long postId, InteractionType type) {
        Interaction interaction = interactionRepository.findByUserIdAndPostIdAndType(userId, postId, type)
                .orElseThrow(() -> new IllegalArgumentException("Interaction not found"));

        interaction.setInteractionEnabled(false);
        interactionRepository.save(interaction);
    }

    public InteractionDetails getInteractionDetails(Long userId, Long postId) {
        InteractionDetailsInterface result = interactionRepository.findPostInteractionDetails(userId, postId);
        return new InteractionDetails(result.getIsSaved(), result.getIsSupported(), result.getSaveCount(), result.getSupportCount());
    }

    public List<Post> getSavedPosts(Long userId) {
        return interactionRepository.findSavedPostsByUserId(userId);
    }
}

