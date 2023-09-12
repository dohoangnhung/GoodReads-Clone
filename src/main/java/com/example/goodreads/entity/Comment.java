package com.example.goodreads.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    private String comment;

    private LocalDateTime createdDate;

    @ManyToOne
    @JoinColumn(nullable = false, name = "review_id")
    private Review review;

    @ManyToOne
    @JoinColumn(nullable = false, name = "user_id")
    private User user;
}
