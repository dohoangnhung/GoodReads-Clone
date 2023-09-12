package com.example.goodreads.service;

import com.example.goodreads.dto.CommentDto;
import com.example.goodreads.entity.Comment;
import com.example.goodreads.entity.Review;
import com.example.goodreads.entity.User;
import com.example.goodreads.mapper.CommentMapper;
import com.example.goodreads.repository.CommentRepository;
import com.example.goodreads.repository.ReviewRepository;
import com.example.goodreads.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;

    private final CommentMapper commentMapper;
    private final AuthService authService;

    public void createComment(CommentDto commentDto) {
        Review review = reviewRepository.findById(commentDto.getReviewId())
                .orElseThrow(() -> new IllegalStateException("No review found"));
        Comment newComment = commentMapper.map(commentDto, authService.getCurrentUser(), review);
        commentRepository.save(newComment);
    }

    public List<CommentDto> getAllCommentsForReview(Long reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalStateException("No review found with id " + reviewId));
        return commentRepository.findAllByReview(review)
                .stream()
                .map(commentMapper::mapToDto)
                .collect(Collectors.toList());
    }

    public List<CommentDto> getAllCommentsByUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalStateException("No user found with id " + userId));
        return commentRepository.findAllByUser(user)
                .stream()
                .map(commentMapper::mapToDto)
                .collect(Collectors.toList());
    }
}
