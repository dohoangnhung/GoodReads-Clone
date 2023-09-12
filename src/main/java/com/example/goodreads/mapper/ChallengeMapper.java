package com.example.goodreads.mapper;

import com.example.goodreads.dto.ChallengeRequest;
import com.example.goodreads.dto.ChallengeResponse;
import com.example.goodreads.entity.Book;
import com.example.goodreads.entity.ReadingChallenge;
import com.example.goodreads.entity.User;
import com.example.goodreads.repository.ReadingProgressRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public abstract class ChallengeMapper {
    @Autowired
    private ReadingProgressRepository readingProgressRepository;

    @Mapping(target = "id", source = "challengeRequest.id")
    @Mapping(target = "user", source = "user")
    @Mapping(target = "books", expression = "java(getReadBooks(user))")
    @Mapping(target = "year", expression = "java(java.time.LocalDate.now().getYear())")
    public abstract ReadingChallenge map(ChallengeRequest challengeRequest, User user);

    Set<Book> getReadBooks(User user) {
        Long userId = user.getId();
        Integer currentYear = LocalDate.now().getYear();
        return readingProgressRepository.findReadBooksByUserAndYear(userId, currentYear)
                .stream().collect(Collectors.toSet());
    }

    @Mapping(target = "noOfReadBooks", expression = "java(getNoOfReadBooks(readingChallenge))")
    @Mapping(target = "percentageProgress", expression = "java(getPercentageProgress(readingChallenge))")
    @Mapping(target = "frequency", expression = "java(getFrequency(readingChallenge))")
    public abstract ChallengeResponse mapToDto(ReadingChallenge readingChallenge);

    Integer getNoOfReadBooks(ReadingChallenge readingChallenge) {
        return readingChallenge.getBooks().size();
    }

    Integer getPercentageProgress(ReadingChallenge readingChallenge) {
        return readingChallenge.getBooks().size() / readingChallenge.getGoal();
    }

    Double getFrequency(ReadingChallenge readingChallenge) {
        int remainBooks = readingChallenge.getGoal() - readingChallenge.getBooks().size();
        int remainNoOfMonths = 12 - LocalDate.now().getMonth().getValue();
        return (double) (remainBooks / remainNoOfMonths);
    }
}
