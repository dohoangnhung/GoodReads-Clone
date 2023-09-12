package com.example.goodreads.mapper;

import com.example.goodreads.dto.CommentDto;
import com.example.goodreads.entity.Comment;
import com.example.goodreads.entity.Review;
import com.example.goodreads.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public abstract class CommentMapper {
    @Mapping(target = "id", source = "commentDto.id")
    @Mapping(target = "createdDate", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "user", source = "user")
    @Mapping(target = "review", source = "review")
    public abstract Comment map(CommentDto commentDto, User user, Review review);

    @Mapping(target = "reviewId", source = "review.id")
    @Mapping(target = "username", expression = "java(getUsername(comment))")
    public abstract CommentDto mapToDto(Comment comment);

    String getUsername(Comment comment) {
        return comment.getUser().getFirstName() + comment.getUser().getLastName();
    }
}
