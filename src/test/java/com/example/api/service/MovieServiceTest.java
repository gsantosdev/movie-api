package com.example.api.service;

import com.example.api.controller.dto.MovieDTO;
import com.example.api.exception.ResourceNotFoundException;
import com.example.api.mapper.MovieMapper;
import com.example.api.model.MovieEntity;
import com.example.api.repository.MovieRepository;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MovieServiceTest {

    @InjectMocks
    private MovieService movieService;

    @Mock
    private MovieRepository movieRepository;

    @Mock
    private MovieMapper movieMapper = Mappers.getMapper(MovieMapper.class);


    @Test
    void testSaveMovie() {
        // Given
        MovieDTO movieDTO = new MovieDTO();
        MovieEntity movieEntity = new MovieEntity();
        when(movieMapper.toEntity(movieDTO)).thenReturn(movieEntity);
        when(movieRepository.save(movieEntity)).thenReturn(movieEntity);

        // Act
        Optional<MovieEntity> result = movieService.saveMovie(movieDTO);

        // Assert
        assertEquals(movieEntity, result.orElse(null));
        verify(movieMapper).toEntity(movieDTO);
        verify(movieRepository).save(movieEntity);
    }

    @Test
    void testFindMovieById() {
        // Given
        Long id = 1L;
        MovieEntity movieEntity = new MovieEntity();
        when(movieRepository.findById(id)).thenReturn(Optional.of(movieEntity));

        // Act
        MovieEntity result = movieService.findMovieByIdOrThrow(id);

        // Assert
        assertEquals(movieEntity, result);
        verify(movieRepository).findById(id);
    }

    @Test
    void testFindAllByParams() {
        // Given
        MovieDTO movieDTO = new MovieDTO();
        MovieEntity movieEntity = new MovieEntity();
        when(movieMapper.toEntity(movieDTO)).thenReturn(movieEntity);
        when(movieRepository.findAll(any(Example.class))).thenReturn(Collections.emptyList());

        // Act
        List<MovieEntity> result = movieService.findAllByParams(movieDTO);

        // Assert
        assertEquals(0, result.size());
        verify(movieMapper).toEntity(movieDTO);
        verify(movieRepository).findAll(any(Example.class));
    }

    @Test
    public void testSaveMovieWithValidData() {
        // Given
        MovieDTO movieDTO = new MovieDTO();
        MovieEntity movieEntity = new MovieEntity();
        when(movieMapper.toEntity(movieDTO)).thenReturn(movieEntity);
        when(movieRepository.save(movieEntity)).thenReturn(movieEntity);

        // Act
        Optional<MovieEntity> result = movieService.saveMovie(movieDTO);

        // Assert
        assertEquals(movieEntity, result.orElse(null));
        verify(movieMapper).toEntity(movieDTO);
        verify(movieRepository).save(movieEntity);
    }

    @Test
    public void testSaveMovieWithNullData() {
        // Given
        MovieDTO movieDTO = null;

        // Act
        Optional<MovieEntity> result = movieService.saveMovie(movieDTO);

        // Assert
        assertFalse(result.isPresent());
        verifyNoMoreInteractions(movieMapper);
        verifyNoMoreInteractions(movieRepository);
    }

    @Test
    public void testFindMovieByIdWithExistingId() {
        // Given
        Long id = 1L;
        MovieEntity movieEntity = new MovieEntity();
        when(movieRepository.findById(id)).thenReturn(Optional.of(movieEntity));

        // Act
        MovieEntity result = movieService.findMovieByIdOrThrow(id);

        // Assert
        assertEquals(movieEntity, result);
        verify(movieRepository).findById(id);
    }

    @Test
    public void testFindMovieByIdWithNonExistingId() {
        // Given
        Long id = 1L;
        when(movieRepository.findById(id)).thenReturn(Optional.empty());

        // Act
        assertThrows(ResourceNotFoundException.class, () -> movieService.findMovieByIdOrThrow(id));

        // Assert
        verify(movieRepository).findById(id);
    }

    @Test
    public void testFindAllByParamsWithValidData() {
        // Given
        MovieDTO movieDTO = new MovieDTO();
        MovieEntity movieEntity = new MovieEntity();
        when(movieMapper.toEntity(movieDTO)).thenReturn(movieEntity);
        when(movieRepository.findAll(any(Example.class))).thenReturn(Collections.emptyList());

        // Act
        List<MovieEntity> result = movieService.findAllByParams(movieDTO);

        // Assert
        assertEquals(0, result.size());
        verify(movieMapper).toEntity(movieDTO);
        verify(movieRepository).findAll(any(Example.class));
    }

}
