package me.dio.movie_critic.movie.impl;

import lombok.AllArgsConstructor;
import me.dio.movie_critic.comment.Comment;
import me.dio.movie_critic.comment.CommentRepository;
import me.dio.movie_critic.movie.Movie;
import me.dio.movie_critic.movie.MovieRepository;
import me.dio.movie_critic.movie.MovieService;
import me.dio.movie_critic.movie.exceptions.InvalidMovieFieldsException;
import me.dio.movie_critic.movie.exceptions.MovieNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;
    private final CommentRepository commentRepository;

    @Override
    public Movie save(Movie movie) {
        if(movie.getId() != null){
            throw new InvalidMovieFieldsException("Movie ID must be null");
        }
        if(movie.getTitle().isEmpty() || movie.getGenre().isEmpty() || movie.getDescription().isEmpty()){
            throw new InvalidMovieFieldsException("Movie fields must not be empty");
        }
        if(movie.getReleaseDate() == null){
            throw new InvalidMovieFieldsException("Movie must have a release date");
        }
        if(movie.getReleaseDate().after(new Date())){
            throw new InvalidMovieFieldsException("Movie must have a release date in the past");
        }
        return movieRepository.save(movie);
    }

    @Override
    public List<Movie> findByTitle(String title) {
        return  movieRepository.findAll().stream().filter(movie ->
                movie.getTitle().toLowerCase().contains(title.toLowerCase())).collect(Collectors.toList());
    }

    @Override
    public List<Movie> findByGenre(String genre) {
        return movieRepository.findAll().stream().filter(movie ->
                movie.getGenre().equalsIgnoreCase(genre)).collect(Collectors.toList());
    }

    @Override
    public Movie findById(Long id) {
        return movieRepository.findById(id).orElseThrow(() ->
                new MovieNotFoundException("Movie not found"));
    }

    @Override
    public void deleteById(Long id) {
        Movie movie = movieRepository.findById(id).orElseThrow(() -> new MovieNotFoundException("Movie not found"));
        if(movie.getComments() != null){
            movie.getComments().forEach(comment -> commentRepository.deleteById(comment.getId()));
        }
        movieRepository.delete(movie);
    }

    @Override
    public void update(Long id, Movie movie) {
        Movie movieToUpdate = findById(id);
        if(movie == null){
            throw new InvalidMovieFieldsException("Movie must not be null");
        }
        if(movieToUpdate == null){
            throw new MovieNotFoundException("Movie not found");
        }
        if(movie.getTitle() != null){
            movieToUpdate.setTitle(movie.getTitle());
        }
        if(movie.getGenre() != null){
            movieToUpdate.setGenre(movie.getGenre());
        }
        if(movie.getDescription() != null){
            movieToUpdate.setDescription(movie.getDescription());
        }
        if(movie.getReleaseDate() != null){
            movieToUpdate.setReleaseDate(movie.getReleaseDate());
        }
        if(!movie.getComments().isEmpty()){
            movieToUpdate.setComments(movie.getComments());
            movieToUpdate.setGeneralRating(
                    (int) movie.getComments().stream().mapToDouble(Comment::getRating).average().orElse(0));
        }
        movieRepository.save(movieToUpdate);
    }

    @Override
    public List<Movie> findAllMovies() {
        return movieRepository.findAll();
    }
}
