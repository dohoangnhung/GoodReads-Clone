package com.example.goodreads.controller;

import com.example.goodreads.dto.LikeDto;
import com.example.goodreads.service.LikeService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/like")
@AllArgsConstructor
public class LikeController {
    private final LikeService likeService;

    @PostMapping
    public ResponseEntity<Void> like(@RequestBody LikeDto likeDto) {
        likeService.like(likeDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
