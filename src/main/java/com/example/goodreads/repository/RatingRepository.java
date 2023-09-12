package com.example.goodreads.repository;

import com.example.goodreads.entity.Book;
import com.example.goodreads.entity.Rating;
import com.example.goodreads.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Long> {
    Optional<Rating> findAllByBook(Book book);

    Optional<Rating> findByUserAndBook(User user, Book book);
}
