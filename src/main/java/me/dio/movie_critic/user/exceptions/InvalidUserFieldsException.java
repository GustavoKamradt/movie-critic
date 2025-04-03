package me.dio.movie_critic.user.exceptions;

public class InvalidUserFieldsException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    public InvalidUserFieldsException(String message) {
        super(message);
    }
}
