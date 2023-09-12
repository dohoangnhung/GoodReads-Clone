package com.example.goodreads.dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class SignupRequest {
    private String firstName;
    private String lastName;
    private String password;
    private String email;
}
