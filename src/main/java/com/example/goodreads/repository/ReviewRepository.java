package com.example.goodreads.repository;

import com.example.goodreads.entity.Book;
import com.example.goodreads.entity.Review;
import com.example.goodreads.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    Optional<Review> findAllByBook(Book book);

    Optional<Review> findAllByUser(User user);
}
