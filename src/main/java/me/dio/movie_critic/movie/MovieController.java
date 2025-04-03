package me.dio.movie_critic.movie;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import me.dio.movie_critic.movie.dto.MovieDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1/movies")
public class MovieController {

    private final MovieService movieService;

    @GetMapping(value = "/")
    @Operation(summary = "Finds all movies", description = "Finds all movies in the database and retrieves a list with all of them.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operation successful"),
            @ApiResponse(responseCode = "400", description = "Invalid movie fields")
    })
    public ResponseEntity<List<MovieDto>> findAll() {
        List<Movie> movies = movieService.findAllMovies();
        return ResponseEntity.ok(movies.stream().map(Movie::toDto).toList());
    }

    @GetMapping(value = "/title/{title}")
    @Operation(summary = "Finds movies by their titles", description = "Finds movies by their titles and retrieves a list with all of them.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operation successful"),
    })
    public ResponseEntity<List<MovieDto>> findByTitle(@PathVariable String title) {
        List<Movie> movies = movieService.findByTitle(title);
        return ResponseEntity.ok(movies.stream().map(Movie::toDto).toList());
    }

    @GetMapping(value = "/genre/{genre}")
    @Operation(summary = "Finds movies by their genres", description = "Find all movies of an genre and retrieve a list with all of them.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operation successful")
    })
    public ResponseEntity<List<MovieDto>> findByGenre(@PathVariable String genre) {
        List<Movie> movies = movieService.findByGenre(genre);
        return ResponseEntity.ok(movies.stream().map(Movie::toDto).toList());
    }

    @GetMapping(value = "/{id}")
    @Operation(summary = "Finds a movie by it's id", description = "Finds a movie by it's id and retrieves the movie.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operation successful")
            , @ApiResponse(responseCode = "404", description = "Movie not found")
    })
    public ResponseEntity<MovieDto> findById(@PathVariable Long id) {
        Movie movie = movieService.findById(id);
        return ResponseEntity.ok(Movie.toDto(movie));
    }

    @DeleteMapping(value = "/delete/{id}")
    @Operation(summary = "Deletes a movie", description = "Deletes a movie by it's id from the database.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operation successful")
            , @ApiResponse(responseCode = "404", description = "Movie not found")
    })
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        movieService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @PatchMapping(value = "/{id}")
    @Operation(summary = "Updates a movie", description = "Updates a movie by it's id and retrieves the updated movie.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operation successful"),
            @ApiResponse(responseCode = "404", description = "Movie not found"),
            @ApiResponse(responseCode = "400", description = "Invalid movie fields")
    })
    public ResponseEntity<MovieDto> update(@PathVariable Long id, @RequestBody Movie movie) {
        movieService.update(id, movie);
        return ResponseEntity.ok(Movie.toDto(movie));
    }

    @PostMapping(value = "/save")
    @Operation(summary = "Creates a movie", description = "Creates a movie and retrieves the created movie.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operation successful"),
            @ApiResponse(responseCode = "400", description = "Invalid movie fields")
    })
    public ResponseEntity<MovieDto> save(@RequestBody Movie movie) {
        movieService.save(movie);
        return ResponseEntity.ok(Movie.toDto(movie));
    }
}
