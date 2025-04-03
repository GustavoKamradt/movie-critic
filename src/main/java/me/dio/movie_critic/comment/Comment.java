package me.dio.movie_critic.comment;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.dio.movie_critic.comment.dto.CommentDto;
import me.dio.movie_critic.movie.Movie;
import me.dio.movie_critic.user.User;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Entity
@Table(name = "tab_comments")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Comment {

    public static CommentDto toDto(Comment comment) {
        return new CommentDto(
                comment.getId(),
                comment.getText(),
                comment.getMovieId(),
                comment.getRating(),
                comment.getCreationDate(),
                comment.getOwnerId()
        );
    }

    public static Comment fromDto(CommentDto commentDto) {
        return new Comment(
                commentDto.id(),
                commentDto.text(),
                null,
                commentDto.rating(),
                commentDto.creationDate(),
                null,
                commentDto.ownerId(),
                commentDto.movieId()
        );
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;
    private String text;
    @ManyToOne()
    @JoinColumn(name = "movie", nullable = false)
    private Movie movie;
    @Column(length = 10, nullable = false)
    private int rating;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSX")
    private Date creationDate;
    @ManyToOne()
    @JoinColumn(name = "owner", nullable = false)
    private User owner;

    @Column(name = "movie_id")
    private Long movieId;

    @Column(name = "owner_id")
    private Long ownerId;


}
