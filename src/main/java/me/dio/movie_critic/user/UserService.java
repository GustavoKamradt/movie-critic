package me.dio.movie_critic.user;

import java.util.List;

public interface UserService {

    User findById(Long id);

    User findByEmail(String email);

    void save(User user);

    void delete(Long id, User user);

    void update(Long id, User user);

    List<User> findAll();

    User findByUsername(String username);
}
