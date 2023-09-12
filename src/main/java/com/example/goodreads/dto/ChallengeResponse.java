package com.example.goodreads.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChallengeResponse {
    private Long id;
    private Integer year;
    private Integer goal;
    private Integer noOfReadBooks;
    private Integer percentageProgress;
    private Double frequency;
}
