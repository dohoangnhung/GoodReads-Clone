package com.example.goodreads.service;

import com.example.goodreads.dto.ChallengeRequest;
import com.example.goodreads.dto.ChallengeResponse;
import com.example.goodreads.entity.ReadingChallenge;
import com.example.goodreads.entity.User;
import com.example.goodreads.mapper.ChallengeMapper;
import com.example.goodreads.repository.ReadingChallengeRepository;
import com.example.goodreads.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ReadingChallengeService {
    private final ReadingChallengeRepository readingChallengeRepository;
    private final UserRepository userRepository;

    private final ChallengeMapper challengeMapper;
    private final AuthService authService;

    public List<ChallengeResponse> getAllChallengesByUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalStateException("No user found"));
        Optional<ReadingChallenge> challenges = readingChallengeRepository.findAllByUser(user);
        challenges.orElseThrow(() -> new IllegalStateException("No challenge found"));

        return challenges.stream()
                .map(challengeMapper::mapToDto)
                .collect(Collectors.toList());
    }

    public ChallengeResponse getReadingChallengeByUserAndYear(Long userId, Integer year) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalStateException("No user found"));
        ReadingChallenge readingChallenge = readingChallengeRepository.findAllByUserAndYear(user, year)
                .orElseThrow(() -> new IllegalStateException("No challenge of the year " + year + " found"));

        return challengeMapper.mapToDto(readingChallenge);
    }

    public void createReadingChallenge(ChallengeRequest challengeRequest) {
        User user = authService.getCurrentUser();
        Optional<ReadingChallenge> existingChallenge = readingChallengeRepository
                .findAllByUserAndYear(user, LocalDate.now().getYear());

        if (existingChallenge.isPresent()) {
            throw new IllegalStateException("Existing challenge");
        } else {
            ReadingChallenge newChallenge = challengeMapper.map(challengeRequest, user);
            readingChallengeRepository.save(newChallenge);
        }
    }
}
