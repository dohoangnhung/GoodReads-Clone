package com.example.goodreads.mapper;

import com.example.goodreads.dto.BookDto;
import com.example.goodreads.entity.Book;
import com.example.goodreads.entity.Rating;
import com.example.goodreads.entity.Review;
import com.example.goodreads.repository.RatingRepository;
import com.example.goodreads.repository.ReviewRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

@Mapper(componentModel = "spring")
public abstract class BookMapper {
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private RatingRepository ratingRepository;

    @Mapping(target = "rating", expression = "java(getRating(book))")
    @Mapping(target = "noOfRatings", expression = "java(getNoOfRatings(book))")
    @Mapping(target = "noOfReviews", expression = "java(getNoOfReviews(book))")
    public abstract BookDto mapToDto(Book book);

    double getRating(Book book) {
        Optional<Rating> ratings = ratingRepository.findAllByBook(book);
        if (ratings.isEmpty()) {
            return 0;
        } else {
            int totalRatingPoint = ratings.stream().map(Rating::getRating).mapToInt(r -> r).sum();
            return (double) totalRatingPoint / getNoOfRatings(book);
        }
    }

    int getNoOfRatings(Book book) {
        Optional<Rating> ratings = ratingRepository.findAllByBook(book);
        if (ratings.isEmpty()) {
            return 0;
        } else {
            return (int) ratings.stream().count();
        }
    }

    int getNoOfReviews(Book book) {
        Optional<Review> reviews = reviewRepository.findAllByBook(book);
        if (reviews.isEmpty()) {
            return 0;
        } else {
            return (int) reviews.stream().count();
        }
    }
}
