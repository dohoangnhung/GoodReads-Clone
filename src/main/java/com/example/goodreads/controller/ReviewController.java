package com.example.goodreads.controller;

import com.example.goodreads.dto.ReviewRequest;
import com.example.goodreads.dto.ReviewResponse;
import com.example.goodreads.service.ReviewService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/review")
@AllArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    @GetMapping("/{reviewId}")
    public ResponseEntity<ReviewResponse> getReview(@PathVariable Long reviewId) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(reviewService.getReview(reviewId));
    }

    @PostMapping
    public ResponseEntity<Void> createReview(@RequestBody ReviewRequest reviewRequest) {
        reviewService.createReview(reviewRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/by-book/{bookId}")
    public ResponseEntity<List<ReviewResponse>> getAllReviewsForBook(@PathVariable Long bookId) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(reviewService.getAllReviewsForBook(bookId));
    }

    @GetMapping("/by-user/{userId}")
    public ResponseEntity<List<ReviewResponse>> getAllReviewsOfUser(@PathVariable Long userId) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(reviewService.getAllReviewsOfUser(userId));
    }
}
