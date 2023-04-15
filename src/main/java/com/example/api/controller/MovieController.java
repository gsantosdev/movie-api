package com.example.api.controller;

import com.example.api.controller.request.MovieRequest;
import com.example.api.controller.request.RateMovieRequest;
import com.example.api.controller.request.SearchMovieRequest;
import com.example.api.controller.response.MovieResponse;
import com.example.api.mapper.MovieMapper;
import com.example.api.mapper.RateMovieMapper;
import com.example.api.service.MovieService;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static java.util.Optional.ofNullable;

@RestController
@RequiredArgsConstructor
@RequestMapping("/movie")
public class MovieController {

    private final MovieService movieService;

    private final MovieMapper movieMapper;

    private final RateMovieMapper rateMovieMapper;


    @GetMapping("/{id}")
    public ResponseEntity<MovieResponse> findMovieById(@PathVariable("id") Long id) {
        final Optional<MovieResponse> movieResponse = Optional.ofNullable(id)
            .map(movieService::findMovieByIdOrThrow)
            .map(movieMapper::toResponse);


        return ResponseEntity.of(movieResponse);
    }

    @GetMapping
    public ResponseEntity<List<MovieResponse>> findMovieByParams(SearchMovieRequest movieRequest) {
        final Optional<List<MovieResponse>> movieResponseList = ofNullable(movieRequest)
            .map(movieMapper::toDTO)
            .map(movieService::findAllByParams)
            .map(movieMapper::toResponseList);

        return ResponseEntity.of(movieResponseList);
    }

    @PostMapping
    public ResponseEntity<MovieResponse> registerMovie(@Valid @RequestBody final MovieRequest movieRequest) {
        final Optional<MovieResponse> movieResponse = ofNullable(movieRequest)
            .map(movieMapper::toDTO)
            .flatMap(movieService::saveMovie)
            .map(movieMapper::toResponse);

        return ResponseEntity.of(movieResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MovieResponse> updateMovie(@PathVariable("id") final Long id,
                                                     @Valid @RequestBody final MovieRequest movieRequest) {
        final Optional<MovieResponse> movieResponse = ofNullable(movieRequest)
            .map(movieMapper::toDTO)
            .flatMap(movieDto -> movieService.updateMovie(id, movieDto))
            .map(movieMapper::toResponse);

        return ResponseEntity.of(movieResponse);
    }

    @PatchMapping("/{id}/rating")
    public ResponseEntity<MovieResponse> rateMovie(@PathVariable("id") final Long id,
                                                   @Valid @RequestBody final RateMovieRequest rateMovieRequest) {

        final Optional<MovieResponse> movieResponse = ofNullable(rateMovieRequest)
            .map(rateMovieMapper::toDTO)
            .flatMap(rateMovieDTO -> movieService.rateMovie(id, rateMovieDTO))
            .map(movieMapper::toResponse);

        return ResponseEntity.of(movieResponse);
    }

    @GetMapping("/non-rated")
    public ResponseEntity<List<MovieResponse>> returnNonRatedMoviesFromList(@RequestParam List<Long> movieIdList) {
        final Optional<List<MovieResponse>> movieResponseList = ofNullable(movieIdList)
            .map(movieService::findAllNonRatingMovies)
            .map(movieMapper::toResponseList);

        return ResponseEntity.of(movieResponseList);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMovieById(@PathVariable("id") Long id) {
        Optional.ofNullable(id).ifPresent(movieService::deleteMovieById);

        return ResponseEntity.ok().build();
    }

}
