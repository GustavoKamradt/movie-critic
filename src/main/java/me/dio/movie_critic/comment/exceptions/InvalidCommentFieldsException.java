package me.dio.movie_critic.comment.exceptions;

public class InvalidCommentFieldsException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    public InvalidCommentFieldsException(String message) {
        super(message);
    }
}
