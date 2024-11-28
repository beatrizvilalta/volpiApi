package com.volpi.api.service;

import com.volpi.api.dto.InteractionDetails;
import com.volpi.api.model.Interaction;
import com.volpi.api.model.Post;
import com.volpi.api.model.User;
import com.volpi.api.model.enums.InteractionType;
import com.volpi.api.repository.InteractionRepository;
import com.volpi.api.repository.PostRepository;
import com.volpi.api.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public Interaction createInteraction(Long userId, Long postId, InteractionType type) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Post not found"));

        Interaction interaction = Interaction.builder()
                .type(type)
                .user(user)
                .post(post)
                .build();
        return interactionRepository.save(interaction);
    }

    public InteractionDetails  getInteractionDetails(Long userId, Long postId) {
        Object[] result = interactionRepository.findPostInteractionDetails(userId, postId);
        boolean isSaved = (boolean) result[0];
        boolean isSupported = (boolean) result[1];
        int saveCount = (int) result[2];
        int supportCount = (int) result[3];

        return new InteractionDetails(isSaved, isSupported, saveCount, supportCount);
    }
}

