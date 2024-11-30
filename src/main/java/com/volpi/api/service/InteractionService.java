package com.volpi.api.service;

import com.volpi.api.dto.InteractionControlInterface;
import com.volpi.api.dto.InteractionCountInterface;
import com.volpi.api.model.Interaction;
import com.volpi.api.model.Post;
import com.volpi.api.model.User;
import com.volpi.api.model.enums.InteractionType;
import com.volpi.api.repository.InteractionRepository;
import com.volpi.api.repository.PostRepository;
import com.volpi.api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class InteractionService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final InteractionRepository interactionRepository;

    public void createInteraction(Long userId, Long postId, InteractionType type) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Post not found"));

        Optional<Interaction> existingInteraction = interactionRepository.findByUserIdAndPostIdAndType(userId, postId, type);

        //if interaction already exist we don't need to save again
        if (existingInteraction.isPresent()) {
            return;
        }

        Interaction interaction = Interaction.builder()
                .type(type)
                .user(user)
                .post(post)
                .build();
        interactionRepository.save(interaction);
    }

    public void deleteInteraction(Long userId, Long postId, InteractionType type) {
        Interaction interaction = findByUserAndPostAndType(userId, postId, type);

        interactionRepository.delete(interaction);
    }

    private Interaction findByUserAndPostAndType(Long userId, Long postId, InteractionType type) {
        return interactionRepository.findByUserIdAndPostIdAndType(userId, postId, type)
                .orElseThrow(() -> new IllegalArgumentException("Interaction not found"));
    }

    public InteractionCountInterface getInteractionCount(Long postId) {
        return interactionRepository.getInteractionCount(postId);
    }

    public InteractionControlInterface getInteractionControl(Long userId, Long postId) {
        return interactionRepository.findInteractionControl(userId, postId);
    }

    public List<Post> getSavedPosts(Long userId) {
        return interactionRepository.findSavedPostsByUserId(userId);
    }
}

