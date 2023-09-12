package com.example.goodreads.repository;

import com.example.goodreads.entity.Book;
import com.example.goodreads.entity.BookGenre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    Optional<Book> findAllByBookGenre(BookGenre bookGenre);

    @Query(
            nativeQuery = true,
            value = "select * from Book where isbn like %?1% or title like %?1% or author like %?1%"
    )
    Optional<Book> findAllByIsbnOrTitleOrAuthor(String keyword);
}
