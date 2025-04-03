package me.dio.movie_critic.movie;

import jakarta.persistence.*;
import lombok.*;
import me.dio.movie_critic.comment.Comment;
import me.dio.movie_critic.comment.dto.CommentDto;
import me.dio.movie_critic.movie.dto.MovieDto;

import java.util.Date;
import java.util.List;

@Entity
@AllArgsConstructor
@Table(name = "tab_movies")
@NoArgsConstructor
@Getter
@Setter
public class Movie {

    public static MovieDto toDto(Movie movie) {
        return new MovieDto(
                movie.getId(),
                movie.getTitle(),
                movie.getDescription(),
                movie.getGenre(),
                movie.getGeneralRating(),
                movie.getReleaseDate(),
                movie.getComments().stream()
                        .map(Comment::toDto)
                        .toList());
    }

    public static Movie fromDto(MovieDto movie) {
        return new Movie(
                movie.id(),
                movie.title(),
                movie.description(),
                movie.releaseDate(),
                movie.genre(),
                movie.rating(),
                movie.comments().stream()
                        .map(Comment::fromDto)
                        .toList());
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;
    @Column(length = 50, nullable = false)
    private String title;
    @Column(length = 100, nullable = false)
    private String description;
    @Column(length = 50, nullable = false)
    private Date releaseDate;
    @Column(nullable = false)
    private String genre;
    @Column(length = 10, nullable = false)
    private int generalRating;

    @OneToMany(mappedBy = "movie")
    private List<Comment> comments;

    public void addComment(CommentDto comment) {
        comments.add(Comment.fromDto(comment));
    }
}
