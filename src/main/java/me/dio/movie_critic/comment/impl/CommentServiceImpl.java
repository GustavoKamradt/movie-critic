package me.dio.movie_critic.comment.impl;

import lombok.AllArgsConstructor;
import me.dio.movie_critic.comment.Comment;
import me.dio.movie_critic.comment.CommentRepository;
import me.dio.movie_critic.comment.CommentService;
import me.dio.movie_critic.comment.dto.CommentDto;
import me.dio.movie_critic.comment.exceptions.CommentNotFoundException;
import me.dio.movie_critic.comment.exceptions.InvalidCommentFieldsException;
import me.dio.movie_critic.movie.Movie;
import me.dio.movie_critic.movie.MovieService;
import me.dio.movie_critic.user.User;
import me.dio.movie_critic.user.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    private final MovieService movieService;

    private final UserService userService;

    @Override
    public void createComment(Comment comment) {
        if (comment.getRating() < 0 || comment.getRating() > 10) {
            throw new InvalidCommentFieldsException("Rating must be between 0 and 10");
        }
        if(comment.getId() != null){
            throw new InvalidCommentFieldsException("Comment ID must be null");
        }
        if (comment.getText() == null) {
            throw new InvalidCommentFieldsException("There must be a comment");
        }
        if (comment.getOwnerId() == null) {
            throw new InvalidCommentFieldsException("There must be an ownerId");
        }
        if(comment.getMovieId() == null) {
            throw new InvalidCommentFieldsException("There must be a movieId");
        }
        if (comment.getCreationDate() == null) {
            throw new InvalidCommentFieldsException("There must be a creation date");
        }
        if(comment.getCreationDate().after(new java.util.Date())) {
            throw new InvalidCommentFieldsException("Creation date must be in the past");
        }
        if(comment.getOwner() == null){
            throw new InvalidCommentFieldsException("There must be an owner");
        }

        Movie movie = movieService.findById(comment.getMovieId());
        User user = comment.getOwner();

        if(comment.getMovie() == null) {
            comment.setMovie(movie);
        }

        CommentDto commentDto = Comment.toDto(comment);
        movie.addComment(commentDto);
        movie.setGeneralRating(
                (movie.getGeneralRating() * movie.getComments().size() + comment.getRating()) /
                        (movie.getComments().size() + 1));
        movieService.update(comment.getMovie().getId(), movie);

        user.addComment(commentDto);
        userService.update(comment.getOwner().getId(), user);

        commentRepository.save(comment);
    }

    @Override
    public void updateComment(Long id, Comment comment) {
        Comment commentRequested = commentRepository.findById(id).orElseThrow(() ->
                new CommentNotFoundException("Comment not found"));
        if (comment.getRating() < 0 || comment.getRating() > 10) {
            throw new CommentNotFoundException("Rating must be between 0 and 10");
        }
        commentRequested.setText(comment.getText());
        commentRequested.setRating(comment.getRating());
        commentRequested.setCreationDate(comment.getCreationDate());

        CommentDto commentDto = Comment.toDto(comment);

        Movie movie = movieService.findById(commentRequested.getMovie().getId());
        movie.getComments().removeIf(c -> c.getId().equals(commentRequested.getId()));
        movie.getComments().add(comment);

        movie.setGeneralRating(
                (movie.getGeneralRating() * movie.getComments().size() - commentRequested.getRating()
                        + comment.getRating()) / movie.getComments().size());
        movieService.update(movie.getId(), movie);

        User user = comment.getOwner();
        user.addComment(commentDto);
        userService.update(comment.getOwner().getId(), user);

        commentRepository.save(commentRequested);
    }

    @Override
    public void deleteComment(Long id) {
        commentRepository.delete(commentRepository.findById(id).orElseThrow(() ->
                new CommentNotFoundException("Comment not found")));
    }

    @Override
    public Comment getCommentById(Long id) {
        return commentRepository.findById(id).orElseThrow(() ->
                new CommentNotFoundException("Comment not found"));
    }

    @Override
    public List<Comment> getAllComments() {
        return commentRepository.findAll();
    }

    @Override
    public List<Comment> getCommentsByMovieId(Long movieId) {
        return commentRepository.findAllByMovieId(movieId);
    }
}
