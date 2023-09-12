package com.example.goodreads.repository;

import com.example.goodreads.entity.Comment;
import com.example.goodreads.entity.Review;
import com.example.goodreads.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    Optional<Comment> findAllByReview(Review review);

    Optional<Comment> findAllByUser(User user);
}
