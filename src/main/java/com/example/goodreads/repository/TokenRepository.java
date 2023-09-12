package com.example.goodreads.repository;

import com.example.goodreads.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token, Integer> {
    @Query(
            nativeQuery = true,
            value = "select * from Token where user_id = ?1 and (expired = false or revoked = false)"
    )
    List<Token> findAllValidTokenByUser(Long userId);

    Optional<Token> findByToken(String token);
}
