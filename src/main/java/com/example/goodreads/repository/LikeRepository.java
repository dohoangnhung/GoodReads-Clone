package com.example.goodreads.repository;

import com.example.goodreads.entity.Like;
import com.example.goodreads.entity.Review;
import com.example.goodreads.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {
    Optional<Like> findAllByReview(Review review);

    Optional<Like> findByUserAndReview(User user, Review review);
}
