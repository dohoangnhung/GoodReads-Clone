package com.example.goodreads.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ChallengeRequest {
    private Long id;
    private Integer goal;
}
