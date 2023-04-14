package com.example.api.controller.request;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RateMovieRequest {

    @NotNull(message = "Rating is mandatory")
    @Min(value = 0L, message = "The value must be at least 0")
    @Max(value = 5L, message = "The value must be at most 5")
    private Long rating;

}
