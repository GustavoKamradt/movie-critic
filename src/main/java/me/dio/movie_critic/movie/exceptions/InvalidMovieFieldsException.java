package me.dio.movie_critic.movie.exceptions;

public class InvalidMovieFieldsException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    public InvalidMovieFieldsException(String message) {
        super(message);
    }
}
