package com.example.api.service;

import com.example.api.controller.dto.MovieDTO;
import com.example.api.controller.dto.RateMovieDTO;
import com.example.api.exception.ResourceNotFoundException;
import com.example.api.mapper.MovieMapper;
import com.example.api.model.MovieEntity;
import com.example.api.repository.MovieRepository;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import static java.util.function.Predicate.not;

@Service
@RequiredArgsConstructor
public class MovieService {

    private final MovieRepository movieRepository;

    private final MovieMapper movieMapper;

    public Optional<MovieEntity> saveMovie(final MovieDTO movieDTO) {

        return Optional.ofNullable(movieDTO)
            .map(movieMapper::toEntity)
            .map(movieRepository::save);

    }

    public Optional<MovieEntity> updateMovie(@NotNull final Long id, final MovieDTO movieDTO) {

        return Optional.ofNullable(id)
            .filter(movieRepository::existsById)
            .map(movieId -> movieMapper.toEntity(id, movieDTO))
            .map(movieRepository::save);
    }

    public void deleteMovieById(@NotNull final Long id) {
        Optional.ofNullable(id)
            .map(this::findMovieByIdOrThrow)
            .ifPresent(movieRepository::delete);
    }

    public MovieEntity findMovieByIdOrThrow(@NotNull final Long id) {
        return this.findMovieById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Movie not found"));
    }


    public List<MovieEntity> findAllByParams(final MovieDTO movieDTO) {

        return Optional.ofNullable(movieDTO)
            .map(movieMapper::toEntity)
            .map(entity -> Example.of(entity, ExampleMatcher.matchingAll().withIgnoreCase()))
            .map(movieRepository::findAll)
            .filter(not(CollectionUtils::isEmpty))
            .orElseThrow(() -> new ResourceNotFoundException("Movie not found"));
    }

    public Optional<MovieEntity> rateMovie(@NotNull final Long id, final RateMovieDTO rateMovieDTO) {

        return Optional.ofNullable(id)
            .map(this::findMovieByIdOrThrow)
            .map(movieEntity -> {
                movieEntity.setRating(rateMovieDTO.getRating());
                return movieRepository.save(movieEntity);
            });
    }

    public MovieEntity findOneNonRatingMovie(final List<Long> idList) {
        return getNonRatedMovies(idList)
            .orElseThrow(() -> new ResourceNotFoundException("Movies not found"));
    }

    private Optional<MovieEntity> getNonRatedMovies(List<Long> idList) {
        return idList.stream()
            .map(this::findMovieByIdOrNull)
            .filter(Objects::nonNull)
            .filter(movieEntity -> Objects.isNull(movieEntity.getRating()))
            .findAny();
    }


    private MovieEntity findMovieByIdOrNull(@NotNull final Long id) {
        return this.findMovieById(id).orElse(null);
    }

    private Optional<MovieEntity> findMovieById(@NotNull final Long id) {
        return movieRepository.findById(id);
    }


}
