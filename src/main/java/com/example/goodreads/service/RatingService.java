package com.example.goodreads.service;

import com.example.goodreads.dto.RatingDto;
import com.example.goodreads.entity.Book;
import com.example.goodreads.entity.Rating;
import com.example.goodreads.repository.BookRepository;
import com.example.goodreads.repository.RatingRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
public class RatingService {
    private final RatingRepository ratingRepository;
    private final BookRepository bookRepository;

    private final AuthService authService;

    public void rating(RatingDto ratingDto) {
        Book book = bookRepository.findById(ratingDto.getBookId())
                .orElseThrow(() -> new IllegalStateException("No book found with id " + ratingDto.getBookId()));
        Optional<Rating> ratings = ratingRepository.findByUserAndBook(authService.getCurrentUser(), book);

        if (ratings.isPresent()) {
            Rating rating = ratings.get();
            rating.setRating(ratingDto.getRating());
            rating.setCreatedDate(LocalDateTime.now());
            ratingRepository.save(rating);
        } else {
            Rating newRating = Rating.builder()
                    .rating(ratingDto.getRating())
                    .createdDate(LocalDateTime.now())
                    .user(authService.getCurrentUser())
                    .book(book)
                    .build();
            ratingRepository.save(newRating);
        }
    }
}
