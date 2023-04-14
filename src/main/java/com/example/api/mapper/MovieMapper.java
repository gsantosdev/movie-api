package com.example.api.mapper;

import com.example.api.controller.request.MovieRequest;
import com.example.api.controller.request.RateMovieRequest;
import com.example.api.controller.response.MovieResponse;
import com.example.api.dto.MovieDTO;
import com.example.api.model.MovieEntity;
import java.util.List;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR, unmappedTargetPolicy = ReportingPolicy.IGNORE)
@Component
public abstract class MovieMapper {

    public abstract MovieEntity toEntity(MovieDTO movie);

    public abstract MovieEntity toEntity(Long id, MovieDTO movie);

    @Mapping(source = "rateMovieRequest.rating", target = "rating")
    public abstract MovieEntity toEntity(Long id, RateMovieRequest rateMovieRequest);


    public abstract MovieDTO toDTO(MovieRequest movie);

    public abstract MovieResponse toResponse(MovieEntity movie);

    public abstract List<MovieResponse> toResponseList(List<MovieEntity> movieEntityList);


}
