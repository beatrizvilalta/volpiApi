package com.volpi.api.controller;

import com.volpi.api.dto.PostRequest;
import com.volpi.api.dto.PostResponse;
import com.volpi.api.model.Post;
import com.volpi.api.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<PostResponse> createPost(@ModelAttribute @Valid PostRequest postRequest) {
        Post post = postService.createPost(postRequest);

        return ResponseEntity.ok(postService.getPostResponse(post, post.getUser().getId()));
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<PostResponse> editPost(@PathVariable Long id, @ModelAttribute PostRequest post) {
        return ResponseEntity.ok(postService.editPost(post, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        postService.deletePost(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> getPost(@PathVariable Long id, @RequestParam(required = false) Long userId) {
        Post post = postService.getPost(id);
        return ResponseEntity.ok(postService.getPostResponse(post, userId));
    }

    @GetMapping
    public ResponseEntity<List<PostResponse>> getAllPosts(@RequestParam(required = false) Long userId) {
        return ResponseEntity.ok(postService.getAllPosts(userId));
    }

    @GetMapping("/search")
    public ResponseEntity<List<PostResponse>> searchPosts(@RequestParam String query, @RequestParam Long  userId) {
        return ResponseEntity.ok(postService.searchPosts(query, userId));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<PostResponse>> getPostsByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(postService.getPostsByUser(userId));
    }

    @GetMapping("/saved/{userId}")
    public ResponseEntity<List<PostResponse>> getSavedPosts(@PathVariable Long userId) {
        return ResponseEntity.ok(postService.getSavedPosts(userId));
    }
}
