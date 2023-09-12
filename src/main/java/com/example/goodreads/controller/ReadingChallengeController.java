package com.example.goodreads.controller;

import com.example.goodreads.dto.ChallengeRequest;
import com.example.goodreads.dto.ChallengeResponse;
import com.example.goodreads.service.ReadingChallengeService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reading-challenge")
@AllArgsConstructor
public class ReadingChallengeController {
    private final ReadingChallengeService readingChallengeService;

    @GetMapping("/{userId}")
    public ResponseEntity<List<ChallengeResponse>> getReadingChallengesByUser(@PathVariable Long userId) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(readingChallengeService.getAllChallengesByUser(userId));
    }

    @GetMapping("/{userId}/{year}")
    public ResponseEntity<ChallengeResponse> getReadingChallengeByUserAndYear(
            @PathVariable(name = "userId") Long userId,
            @PathVariable(name = "year") Integer year) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(readingChallengeService.getReadingChallengeByUserAndYear(userId, year));
    }

    @PostMapping
    public ResponseEntity<Void> createReadingChallenge(@RequestBody ChallengeRequest challengeRequest) {
        readingChallengeService.createReadingChallenge(challengeRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
