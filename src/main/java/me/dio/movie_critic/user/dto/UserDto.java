package me.dio.movie_critic.user.dto;

import me.dio.movie_critic.comment.Comment;
import me.dio.movie_critic.comment.dto.CommentDto;

import java.util.List;

public record UserDto (
        Long id,
        String username,
        String email,
        List<CommentDto> comments
){}