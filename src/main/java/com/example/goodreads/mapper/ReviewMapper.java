package com.example.goodreads.mapper;

import com.example.goodreads.dto.ReviewRequest;
import com.example.goodreads.dto.ReviewResponse;
import com.example.goodreads.entity.*;
import com.example.goodreads.repository.CommentRepository;
import com.example.goodreads.repository.LikeRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

@Mapper(componentModel = "spring")
public abstract class ReviewMapper {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private LikeRepository likeRepository;

    @Mapping(target = "id", source = "reviewRequest.id")
    @Mapping(target = "createdDate", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "user", source = "user")
    @Mapping(target = "book", source = "book")
    public abstract Review map(ReviewRequest reviewRequest, Book book, User user);

    @Mapping(target = "noOfLikes", expression = "java(getNoOfLikes(review))")
    @Mapping(target = "noOfComments", expression = "java(getNoOfComments(review))")
    @Mapping(target = "username", expression = "java(getUsername(review))")
    @Mapping(target = "bookId", source = "book.id")
    public abstract ReviewResponse mapToDto(Review review);

    int getNoOfLikes(Review review) {
        Optional<Like> likes = likeRepository.findAllByReview(review);
        if (likes.isEmpty()) {
            return 0;
        } else {
            return (int) likes.stream().count();
        }
    }

    int getNoOfComments(Review review) {
        Optional<Comment> comments = commentRepository.findAllByReview(review);
        if (comments.isEmpty()) {
            return 0;
        } else {
            return (int) comments.stream().count();
        }
    }

    String getUsername(Review review) {
        return review.getUser().getFirstName() + review.getUser().getLastName();
    }
}
