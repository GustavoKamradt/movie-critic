package me.dio.movie_critic.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import me.dio.movie_critic.user.dto.UserDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1/users")
@Tag(name = "Users Controller", description = "RESTful API for managing users.")
public class UserController {

    private final UserService userService;

    @PostMapping(value = "/save")
    @Operation(summary = "Create a new user", description = "Create a new user and return the created user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operation successful"),
            @ApiResponse(responseCode = "400", description = "Invalid user fields")
    })
    public ResponseEntity<UserDto> save(@RequestBody User user){
        userService.save(user);
        return ResponseEntity.ok(User.toDto(user));
    }

    @GetMapping(value = "{id}")
    @Operation(summary = "Find a user by it's id", description = "Find a user by it's id and return the user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operation successful"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "400", description = "Invalid user fields")
    })
    public ResponseEntity<UserDto> findById(@PathVariable Long id){
        User user = userService.findById(id);
        return ResponseEntity.ok(User.toDto(user));
    }

    @GetMapping(value = "/")
    @Operation(summary = "Find all users", description = "find all users and return a list with all of them.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operation successful")
    })
    public ResponseEntity<List<UserDto>> findAll(){
        List<User> users = userService.findAll();
        return ResponseEntity.ok(users.stream().map(User::toDto).collect(Collectors.toList()));
    }

    @PatchMapping(value = "/{id}")
    @Operation(summary = "Update a user", description = "Update a user and return the updated user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operation successful"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "400", description = "Invalid user fields")
    })
    public ResponseEntity<UserDto> update(@PathVariable Long id, @RequestBody User user){
        userService.update(id, user);
        return ResponseEntity.ok(User.toDto(user));
    }

    @DeleteMapping(value = "/{id}")
    @Operation(summary = "Delete an User", description = "Deletes an User from the database.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operation successful"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "400", description = "Invalid user fields")
    })
    public ResponseEntity<User> delete(@PathVariable Long id,@RequestBody User user){
        userService.delete(id, user);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/username/{username}")
    @Operation(summary = "Find a User by it's username", description = "Find a User by it's username and return the user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operation successful"),
            @ApiResponse(responseCode = "404", description = "User not found"),
    })
    public ResponseEntity<UserDto> findByUsername(@PathVariable String username){
        User user = userService.findByUsername(username);
        return ResponseEntity.ok(User.toDto(user));
    }

    @GetMapping(value = "/email/{email}")
    @Operation(summary = "Find an user by it's email", description = "Find an user by it's email and return the user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operation successful"),
            @ApiResponse(responseCode = "404", description = "User not found"),
    })
    public ResponseEntity<UserDto> findByEmail(@PathVariable String email){
        User user = userService.findByEmail(email);
        return ResponseEntity.ok(User.toDto(user));
    }

}
