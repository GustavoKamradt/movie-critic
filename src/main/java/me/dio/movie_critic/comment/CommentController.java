package me.dio.movie_critic.comment;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import me.dio.movie_critic.comment.dto.CommentDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/comments")
@AllArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @GetMapping(value = "/")
    @Operation(summary = "Finds all comments", description = "Finds all comments and returns a list with all of them.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operation successful"),
    })
    public ResponseEntity<List<CommentDto>> getComments() {
        List<Comment> comments = commentService.getAllComments();
        return ResponseEntity.ok(comments.stream().map(Comment::toDto).toList());
    }

    @GetMapping(value = "/{id}")
    @Operation(summary = "Finds a comment it's id", description = "Finds a comment by it's id and returns the comment.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operation successful"),
            @ApiResponse(responseCode = "404", description = "Comment not found"),
    })
    public ResponseEntity<CommentDto> getCommentById(@PathVariable Long id) {
        Comment comment = commentService.getCommentById(id);
        return ResponseEntity.ok(Comment.toDto(comment));
    }

    @PostMapping(value = "/save")
    @Operation(summary = "Creates a comment", description = "Creates a comment and returns the created comment.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operation successful"),
            @ApiResponse(responseCode = "400", description = "Invalid comment fields")
    })
    public ResponseEntity<CommentDto> saveComment(@RequestBody Comment comment) {
        commentService.createComment(comment);
        return ResponseEntity.ok(Comment.toDto(comment));
    }

    @PatchMapping(value = "/{id}")
    public ResponseEntity<CommentDto> updateComment(@PathVariable Long id, @RequestBody Comment comment) {
        commentService.updateComment(id,comment);
        return ResponseEntity.ok(Comment.toDto(comment));
    }

    @DeleteMapping(value = "/{id}")
    @Operation(summary = "Deletes a comment", description = "Deletes a comment by it's id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operation successful"),
            @ApiResponse(responseCode = "404", description = "Movie not found"),
    })
    public ResponseEntity<Void> deleteComment(@PathVariable Long id) {
        commentService.deleteComment(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/movie/{id}")
    @Operation(summary = "Finds a comment by it's movie id", description = "Finds a comment by it's movie id and returns the comment.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operation successful"),
            @ApiResponse(responseCode = "404", description = "Movie not found"),
    })
    public ResponseEntity<List<CommentDto>> getCommentsByMovieId(@PathVariable Long id) {
        List<Comment> comments = commentService.getCommentsByMovieId(id);
        return ResponseEntity.ok(comments.stream().map(Comment::toDto).toList());
    }
}
