package com.example.goodreads.service;

import com.example.goodreads.entity.Book;
import com.example.goodreads.entity.ReadingProgress;
import com.example.goodreads.entity.ReadingStatus;
import com.example.goodreads.entity.User;
import com.example.goodreads.repository.BookRepository;
import com.example.goodreads.repository.ReadingProgressRepository;
import com.example.goodreads.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ReadingProgressService {
    private final ReadingProgressRepository readingProgressRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;

    private final AuthService authService;

    public List<Book> getWantToReadBooks(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalStateException("User with id " + userId + "does not exists"));
        return readingProgressRepository.findBooksByUserAndReadingStatus(user, ReadingStatus.WANT_TO_READ)
                .stream().toList();
    }

    public List<Book> getCurrentlyReadingBooks(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalStateException("User with id " + userId + "does not exists"));
        return readingProgressRepository.findBooksByUserAndReadingStatus(user, ReadingStatus.CURRENTLY_READING)
                .stream().toList();
    }

    public List<Book> getReadBooks(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalStateException("User with id " + userId + "does not exists"));
        return readingProgressRepository.findBooksByUserAndReadingStatus(user, ReadingStatus.READ)
                .stream().toList();
    }

    public void updateReadingProgress(Long bookId, ReadingStatus readingStatus) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new IllegalStateException("No book found"));
        User user = authService.getCurrentUser();
        Optional<ReadingProgress> progressFound = readingProgressRepository.findByUserAndBook(user, book);

        if (progressFound.isEmpty()) {
            ReadingProgress newProgress = ReadingProgress.builder()
                    .user(user)
                    .book(book)
                    .readingStatus(readingStatus)
                    .markingTime(LocalDateTime.now())
                    .build();
            readingProgressRepository.save(newProgress);
        } else {
            ReadingProgress existingProgress = progressFound.get();
            existingProgress.setReadingStatus(readingStatus);
            existingProgress.setMarkingTime(LocalDateTime.now());
            readingProgressRepository.save(existingProgress);
        }
    }

    public void deleteReadingProgress(Long bookId) {
        User user = authService.getCurrentUser();
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new IllegalStateException("No book found"));
        Optional<ReadingProgress> existingProgress = readingProgressRepository.findByUserAndBook(user, book);
        existingProgress.ifPresent(readingProgressRepository::delete);
    }
}
