package com.example.goodreads.service;

import com.example.goodreads.dto.BookDto;
import com.example.goodreads.entity.Book;
import com.example.goodreads.entity.BookGenre;
import com.example.goodreads.mapper.BookMapper;
import com.example.goodreads.repository.BookRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class BookService {
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    public List<BookDto> getAllBooks() {
        return bookRepository.findAll()
                .stream()
                .map(bookMapper::mapToDto)
                .toList();
    }

    public List<BookDto> getBooksByGenre(BookGenre bookGenre) {
        Optional<Book> books = bookRepository.findAllByBookGenre(bookGenre);
        books.orElseThrow(() -> new IllegalStateException("No book of the genre " + bookGenre + " found"));
        return books
                .stream()
                .map(bookMapper::mapToDto)
                .toList();
    }

    public List<BookDto> findBooksByIsbnOrTitleOrAuthor(String keyword) {
        Optional<Book> books = bookRepository.findAllByIsbnOrTitleOrAuthor(keyword);
        books.orElseThrow(() -> new IllegalStateException("No book found with keyword " + keyword));
        return books
                .stream()
                .map(bookMapper::mapToDto)
                .toList();
    }
}
