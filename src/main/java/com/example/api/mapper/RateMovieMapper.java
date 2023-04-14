package com.example.api.mapper;

import com.example.api.controller.request.RateMovieRequest;
import com.example.api.controller.dto.RateMovieDTO;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
@Component
public abstract class RateMovieMapper {

    public abstract RateMovieDTO toDTO(RateMovieRequest movie);

}
