package me.dio.movie_critic.comment.dto;

import java.util.Date;

public record CommentDto (
        Long id,
        String text,
        Long movieId,
        int rating,
        Date creationDate,
        Long ownerId) {
}