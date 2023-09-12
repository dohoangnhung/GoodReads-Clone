package com.example.goodreads.service;

import com.example.goodreads.dto.ReviewRequest;
import com.example.goodreads.dto.ReviewResponse;
import com.example.goodreads.entity.Book;
import com.example.goodreads.entity.Review;
import com.example.goodreads.entity.User;
import com.example.goodreads.mapper.ReviewMapper;
import com.example.goodreads.repository.BookRepository;
import com.example.goodreads.repository.ReviewRepository;
import com.example.goodreads.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    private final ReviewMapper reviewMapper;
    private final AuthService authService;

    public ReviewResponse getReview(Long reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalStateException("No review found"));
        return reviewMapper.mapToDto(review);
    }

    public void createReview(ReviewRequest reviewRequest) {
        Book book = bookRepository.findById(reviewRequest.getBookId())
                .orElseThrow(() -> new IllegalStateException("No book found"));
        Review newReview = reviewMapper.map(reviewRequest, book, authService.getCurrentUser());
        reviewRepository.save(newReview);
    }

    public List<ReviewResponse> getAllReviewsForBook(Long bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new IllegalStateException("No book found"));
        return reviewRepository.findAllByBook(book)
                .stream()
                .map(reviewMapper::mapToDto)
                .collect(Collectors.toList());
    }

    public List<ReviewResponse> getAllReviewsOfUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalStateException("No user found"));
        return reviewRepository.findAllByUser(user)
                .stream()
                .map(reviewMapper::mapToDto)
                .collect(Collectors.toList());
    }
}
