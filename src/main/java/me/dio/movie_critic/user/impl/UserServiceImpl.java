package me.dio.movie_critic.user.impl;

import lombok.AllArgsConstructor;
import me.dio.movie_critic.comment.CommentRepository;
import me.dio.movie_critic.comment.CommentService;
import me.dio.movie_critic.user.User;
import me.dio.movie_critic.user.UserRepository;
import me.dio.movie_critic.user.UserService;
import me.dio.movie_critic.user.exceptions.InvalidCredentials;
import me.dio.movie_critic.user.exceptions.InvalidUserFieldsException;
import me.dio.movie_critic.user.exceptions.UserNotFoundException;
import me.dio.movie_critic.user.utils.AddressValidator;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final CommentRepository commentRepository;
    private UserRepository userRepository;

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    @Override
    public User findByEmail(String email) {
        User user = userRepository.findByEmail(email);
        if(user != null){
            return user;
        }else{
            throw new UserNotFoundException("User not found");
        }
    }

    @Override
    public void save(User user) {
        if(user.getId() != null){
            throw new InvalidUserFieldsException("User ID must be null");
        }
        if(user.getEmail().isEmpty() || user.getPassword().isEmpty() || user.getUsername().isEmpty()){
            throw new InvalidUserFieldsException("User fields must not be empty");
        }
        AddressValidator validator = new AddressValidator();
        if(!validator.isValid(user.getEmail())){
            throw new InvalidUserFieldsException("Email is not valid");
        }

        if(!user.getComments().isEmpty()){
            throw new InvalidUserFieldsException("User must not have comments");
        }

        if(userRepository.existsByEmail(user.getEmail())){
            throw new InvalidUserFieldsException("User email already exists");
        }

        if(userRepository.existsByUsername(user.getUsername())){
            throw new InvalidUserFieldsException("User username already exists");
        }
        userRepository.save(user);

    }

    @Override
    public void delete(Long id,User user) {
        User userRequested = userRepository.findById(id).orElseThrow(() -> new  IllegalArgumentException("User not found"));
        if(!user.getEmail().equals(userRequested.getEmail())
                || !user.getPassword().equals(userRequested.getPassword())){
            throw new InvalidCredentials("credentials don't match");
        }
        for(int i = 0; i < userRequested.getComments().size(); i++){
            commentRepository.deleteById(userRequested.getComments().get(i).getId());
        }
        userRepository.delete(findById(id));
    }

    @Override
    public void update(Long id, User user) {
        User userRequested = userRepository.findById(id).orElseThrow(() ->
                new UserNotFoundException("User not found"));
        if(!user.getEmail().equals(userRequested.getEmail())
                && !user.getPassword().equals(userRequested.getPassword())){
            throw new UserNotFoundException("credentials don't match");
        }
        userRequested.setEmail(user.getEmail());
        userRequested.setPassword(user.getPassword());
        userRequested.setUsername(user.getUsername());
        userRepository.save(userRequested);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findByUsername(String username) {
        User user = userRepository.findByUsername(username);
        if(user != null){
            return user;
        }else{
            throw new UserNotFoundException("User not found");
        }
    }
}
