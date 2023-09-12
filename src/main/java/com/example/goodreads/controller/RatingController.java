package com.example.goodreads.controller;

import com.example.goodreads.dto.RatingDto;
import com.example.goodreads.service.RatingService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/rating")
@AllArgsConstructor
public class RatingController {
    private final RatingService ratingService;

    @PostMapping
    public ResponseEntity<Void> rating(@RequestBody RatingDto ratingDto) {
        ratingService.rating(ratingDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
