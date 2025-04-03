package me.dio.movie_critic.comment;

import java.util.List;

public interface CommentService {

    void createComment(Comment commentDTO);

    void updateComment(Long id, Comment commentDTO);

    void deleteComment(Long id);

    Comment getCommentById(Long id);

    List<Comment> getAllComments();

    List<Comment> getCommentsByMovieId(Long movieId);

}
