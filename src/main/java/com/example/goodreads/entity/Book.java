package com.example.goodreads.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String isbn;

    private String title;

    private String author;

    private LocalDate publishDate;

    @Enumerated(EnumType.STRING)
    private BookGenre bookGenre;

    @Lob
    private String description;

    private Integer noOfPages;

    private String bookCover;
}
