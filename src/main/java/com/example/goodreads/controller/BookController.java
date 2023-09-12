package com.example.goodreads.controller;

import com.example.goodreads.dto.BookDto;
import com.example.goodreads.entity.BookGenre;
import com.example.goodreads.service.BookService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/book")
@AllArgsConstructor
public class BookController {
    private final BookService bookService;

    @GetMapping
    public ResponseEntity<List<BookDto>> getAllBooks() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(bookService.getAllBooks());
    }

    @GetMapping("/{bookGenre}")
    public ResponseEntity<List<BookDto>> getBooksByGenre(@PathVariable BookGenre bookGenre) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(bookService.getBooksByGenre(bookGenre));
    }

    @GetMapping("/search/{keyword}")
    public ResponseEntity<List<BookDto>> findBooksByIsbnTitleAuthor(@PathVariable String keyword) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(bookService.findBooksByIsbnOrTitleOrAuthor(keyword));
    }
}
