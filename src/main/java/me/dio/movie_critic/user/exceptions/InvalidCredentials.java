package me.dio.movie_critic.user.exceptions;

public class InvalidCredentials extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public InvalidCredentials(String message) {
        super(message);
    }
}
