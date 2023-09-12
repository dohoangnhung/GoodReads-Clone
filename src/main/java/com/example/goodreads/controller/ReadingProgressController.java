package com.example.goodreads.controller;

import com.example.goodreads.entity.Book;
import com.example.goodreads.entity.ReadingStatus;
import com.example.goodreads.service.ReadingProgressService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/my-books")
@AllArgsConstructor
public class ReadingProgressController {
    private final ReadingProgressService readingProgressService;

    @GetMapping("/want-to-read/{userId}")
    public ResponseEntity<List<Book>> getWantToReadBooks(@PathVariable Long userId) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(readingProgressService.getWantToReadBooks(userId));
    }

    @GetMapping("/currently-reading/{userId}")
    public ResponseEntity<List<Book>> getCurrentlyReadingBooks(@PathVariable Long userId) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(readingProgressService.getCurrentlyReadingBooks(userId));
    }

    @GetMapping("/read/{userId}")
    public ResponseEntity<List<Book>> getReadBooks(@PathVariable Long userId) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(readingProgressService.getReadBooks(userId));
    }

    @PostMapping("/{bookId}")
    public ResponseEntity<Void> updateReadingProgress(
            @PathVariable Long bookId,
            @RequestParam ReadingStatus readingStatus) {
        readingProgressService.updateReadingProgress(bookId, readingStatus);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{bookId}")
    public ResponseEntity<Void> deleteReadingProgress(@PathVariable Long bookId) {
        readingProgressService.deleteReadingProgress(bookId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
