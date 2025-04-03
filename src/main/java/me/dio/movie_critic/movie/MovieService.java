package me.dio.movie_critic.movie;

import java.util.List;

public interface MovieService {

    Movie save(Movie movie);

    List<Movie> findByTitle(String title);

    List<Movie> findByGenre(String genre);

    Movie findById(Long id);

    void deleteById(Long id);

    void update(Long id, Movie movie);

    List<Movie> findAllMovies();
}
