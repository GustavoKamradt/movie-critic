package me.dio.movie_critic.movie.dto;

import me.dio.movie_critic.comment.dto.CommentDto;

import java.util.Date;
import java.util.List;

public record MovieDto(
        Long id,
        String title,
        String description,
        String genre,
        int rating,
        Date releaseDate,
        List<CommentDto> comments
) {
}
