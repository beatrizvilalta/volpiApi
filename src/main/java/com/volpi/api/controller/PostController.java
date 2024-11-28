package com.volpi.api.controller;

import com.volpi.api.dto.PostRequest;
import com.volpi.api.dto.PostResponse;
import com.volpi.api.model.Post;
import com.volpi.api.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<Post> createPost(@RequestBody PostRequest post) {
        return ResponseEntity.ok(postService.createPost(post));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Post> editPost(@PathVariable Long id, @RequestBody PostRequest post) {
        return ResponseEntity.ok(postService.editPost(post, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        postService.deletePost(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> getPost(@PathVariable Long id) {
        Post post = postService.getPost(id);
        return ResponseEntity.ok(postService.getPostResponse(post));
    }

    @GetMapping
    public ResponseEntity<List<PostResponse>> getAllPosts() {
        return ResponseEntity.ok(postService.getAllPosts());
    }

    @GetMapping("/search")
    public ResponseEntity<List<Post>> searchPosts(@RequestParam String query) {
        return ResponseEntity.ok(postService.searchPosts(query));
    }
}
