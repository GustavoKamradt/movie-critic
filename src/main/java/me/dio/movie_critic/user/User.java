package me.dio.movie_critic.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.dio.movie_critic.comment.Comment;
import me.dio.movie_critic.comment.dto.CommentDto;
import me.dio.movie_critic.user.dto.UserDto;

import java.util.List;

@Entity
@Table(name = "tab_users")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class User {

    public static User fromDto(UserDto user) {
        return new User(
                user.id(),
                user.username(),
                user.email(),
                null,
                user.comments().stream().map(Comment::fromDto).toList()
        );
    }

    public static UserDto toDto(User user) {
        return new UserDto(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getComments().stream().map(Comment::toDto).toList()
        );
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;
    @Column(unique = true, nullable = false)
    private String email;
    @Column(length = 50, nullable = false)
    private String password;
    @OneToMany(mappedBy = "owner")
    private List<Comment> comments;

    public void addComment(CommentDto comment) {
        comments.add(Comment.fromDto(comment));
    }
}
