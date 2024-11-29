package com.volpi.api.service;

import com.volpi.api.dto.InteractionDetails;
import com.volpi.api.dto.PostRequest;
import com.volpi.api.dto.PostResponse;
import com.volpi.api.dto.file.FileRequest;
import com.volpi.api.model.File;
import com.volpi.api.model.Post;
import com.volpi.api.model.User;
import com.volpi.api.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final FileService fileService;
    private final AuthService authService;
    private final InteractionService interactionService;

    public Post createPost(PostRequest postRequest) {
        File file = fileService.createFile(new FileRequest(postRequest.file(), postRequest.previewImage()));
        User user = authService.findById(postRequest.userId());
        Post post = new Post();
        post.postFromPostRequest(postRequest);
        post.setFile(file);
        post.setUser(user);

        return postRepository.save(post);
    }

    public PostResponse editPost(PostRequest postRequest, Long id) {
        Post post = getPost(id);
        post.postFromPostRequest(postRequest);
        File file = fileService.updateFile(post.getFile().getId(), new FileRequest(postRequest.previewImage(), postRequest.file()));
        post.setFile(file);
        post.setUpdatedAt(new java.sql.Timestamp(System.currentTimeMillis()));

        return getPostResponse(postRepository.save(post));
    }

    public void deletePost(Long id) {
        Post post = getPost(id);
        postRepository.delete(post);
    }

    public Post getPost(Long id) {
        return postRepository.findById(id).orElseThrow(() -> new RuntimeException("Post not found"));
    }

    public PostResponse getPostResponse(Post post) {
        InteractionDetails interactionDetailsInterface =  interactionService.getInteractionDetails(post.getUser().getId(),
                post.getId());

        return new PostResponse(post.getId(),
                post.getUser().getId(),
                post.getUser().getName(),
                post.getCreatedAt(),
                post.getTitle(),
                post.getDescription(),
                post.getSubject().name(),
                post.getSchoolLevel().name(),
                post.getGrade().name(),
                post.getFile().getPreviewImageUrl(),
                interactionDetailsInterface.isSupported(),
                interactionDetailsInterface.isSaved(),
                interactionDetailsInterface.supportCount(),
                interactionDetailsInterface.saveCount());
    }

    public List<PostResponse> getAllPosts() {
        return postRepository.findAllByOrderByCreatedAtDesc()
                .stream()
                .map(this::getPostResponse)
                .toList();
    }

    public List<PostResponse> searchPosts(String query) {
        return postRepository.findByTitleContaining(query)
                .stream()
                .map(this::getPostResponse)
                .toList();
    }

    public List<PostResponse> getPostsByUser(Long userId) {
        return postRepository.findAllByUserId(userId)
                .stream()
                .map(this::getPostResponse)
                .toList();
    }

    public List<PostResponse> getSavedPosts(Long userId) {
        return interactionService.getSavedPosts(userId)
                .stream()
                .map(this::getPostResponse)
                .toList();
    }

}
