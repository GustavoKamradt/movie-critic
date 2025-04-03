package me.dio.movie_critic;

import lombok.AllArgsConstructor;
import me.dio.movie_critic.comment.Comment;
import me.dio.movie_critic.comment.CommentRepository;
import me.dio.movie_critic.movie.Movie;
import me.dio.movie_critic.movie.MovieRepository;
import me.dio.movie_critic.user.User;
import me.dio.movie_critic.user.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;

@Component
@AllArgsConstructor
public class StartUp implements CommandLineRunner {

    private UserRepository userRepository;
    private MovieRepository movieRepository;
    private CommentRepository commentRepository;


    @Override
    public void run(String... args) throws Exception {
        User user = new User();
        user.setUsername("user");
        user.setPassword("password");
        user.setEmail("user@email.com");
        user.setComments(null);
        userRepository.save(user);

        Movie movie = new Movie();
        movie.setTitle("Movie Title");
        movie.setGenre("Action");
        movie.setDescription("Movie Description");
        movie.setReleaseDate(new Date(2023, Calendar.MARCH,2025 ));
        movie.setComments(null);
        movieRepository.save(movie);

        Movie movie2 = new Movie();
        movie2.setTitle("Movie Title 2");
        movie2.setGenre("Comedy");
        movie2.setDescription("Movie Description 2");
        movie2.setReleaseDate(new Date(2023, Calendar.MARCH,2025 ));
        movie2.setComments(null);
        movieRepository.save(movie2);

        Comment comment = new Comment();
        comment.setText("Comment");
        comment.setRating(5);
        comment.setCreationDate(new Date());
        comment.setOwner(user);
        comment.setMovie(movie);
        comment.setOwnerId(user.getId());
        comment.setMovieId(movie.getId());
        commentRepository.save(comment);
    }
}
