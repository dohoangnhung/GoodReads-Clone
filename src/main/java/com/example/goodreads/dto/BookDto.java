package com.example.goodreads.dto;

import com.example.goodreads.entity.BookGenre;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookDto {
    private Long id;
    private String isbn;
    private String title;
    private String author;
    private LocalDate publishDate;
    private BookGenre bookGenre;
    private String description;
    private Integer noOfPages;
    private String bookCover;
    private Double rating;
    private Integer noOfRatings;
    private Integer noOfReviews;
}
