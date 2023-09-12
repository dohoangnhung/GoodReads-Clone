package com.example.goodreads.repository;

import com.example.goodreads.entity.Book;
import com.example.goodreads.entity.ReadingProgress;
import com.example.goodreads.entity.ReadingStatus;
import com.example.goodreads.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReadingProgressRepository extends JpaRepository<ReadingProgress, Long> {
    @Query(
            nativeQuery = true,
            value = "select book from reading_progress where user_id = ?1 and reading_status = ?2"
    )
    Optional<Book> findBooksByUserAndReadingStatus(User userId, ReadingStatus readingStatus);

    Optional<ReadingProgress> findByUserAndBook(User user, Book book);

    @Query(
            nativeQuery = true,
            value = "select book from reading_progress where user_id = ?1 and year(marking_time) = ?2 and reading_status = 'READ'"
    )
    Optional<Book> findReadBooksByUserAndYear(Long userId, Integer year);
}
