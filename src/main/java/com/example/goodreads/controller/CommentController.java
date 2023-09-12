package com.example.goodreads.controller;

import com.example.goodreads.dto.CommentDto;
import com.example.goodreads.entity.Comment;
import com.example.goodreads.service.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comment")
@AllArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<Void> createComment(@RequestBody CommentDto commentDto) {
        commentService.createComment(commentDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/by-review/{reviewId}")
    public ResponseEntity<List<CommentDto>> getAllCommentsForReview(@PathVariable Long reviewId) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(commentService.getAllCommentsForReview(reviewId));
    }

    @GetMapping("/by-user/{userId}")
    public ResponseEntity<List<CommentDto>> getAllCommentsByUser(@PathVariable Long userId) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(commentService.getAllCommentsByUser(userId));
    }
}
