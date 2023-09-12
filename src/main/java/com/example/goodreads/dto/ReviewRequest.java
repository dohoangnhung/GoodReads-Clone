package com.example.goodreads.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReviewRequest {
    private Long id;
    private String review;
    private boolean hasSpoilers;
    private Long bookId;
}
