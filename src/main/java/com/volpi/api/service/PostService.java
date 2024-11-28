package com.volpi.api.service;

import com.volpi.api.model.Post;
import com.volpi.api.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public Post createPost(Post post) {
        return postRepository.save(post);
    }

    public Post editPost(Post post) {
        post.setUpdatedAt(new java.sql.Timestamp(System.currentTimeMillis()));
        return postRepository.save(post);
    }

    public void deletePost(String id) {
        Post post = getPost(id);
        postRepository.delete(post);
    }

    public Post getPost(String id) {
        return postRepository.findById(id).orElseThrow(() -> new RuntimeException("Post not found"));
    }

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    public List<Post> searchPosts(String query) {
        return postRepository.findByTitleContaining(query);
    }

}
