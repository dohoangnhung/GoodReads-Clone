package com.example.goodreads.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReviewResponse {
    private Long id;
    private String review;
    private boolean hasSpoilers;
    private Integer noOfLikes;
    private Integer noOfComments;
    private LocalDateTime createdDate;
    private String username;
    private Long bookId;
}
