package com.example.goodreads.service;

import com.example.goodreads.dto.LikeDto;
import com.example.goodreads.entity.Like;
import com.example.goodreads.entity.Review;
import com.example.goodreads.repository.LikeRepository;
import com.example.goodreads.repository.ReviewRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class LikeService {
    private final ReviewRepository reviewRepository;
    private final LikeRepository likeRepository;

    private final AuthService authService;

    @Transactional
    public void like(LikeDto likeDto) {
        Review review = reviewRepository.findById(likeDto.getReviewId())
                .orElseThrow(() -> new IllegalStateException("No review found with id " + likeDto.getReviewId()));
        Optional<Like> alreadyLike = likeRepository.findByUserAndReview(authService.getCurrentUser(), review);

        if (alreadyLike.isPresent()) {
            likeRepository.delete(alreadyLike.get());
        } else {
            Like like = Like.builder()
                    .review(review)
                    .user(authService.getCurrentUser())
                    .build();
            likeRepository.save(like);
        }
    }
}
