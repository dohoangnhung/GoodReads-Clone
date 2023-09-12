package com.example.goodreads.repository;

import com.example.goodreads.entity.ReadingChallenge;
import com.example.goodreads.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReadingChallengeRepository extends JpaRepository<ReadingChallenge, Long> {
    Optional<ReadingChallenge> findAllByUser(User user);

    Optional<ReadingChallenge> findAllByUserAndYear(User user, Integer year);
}
