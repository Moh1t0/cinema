package org.javaacademy.cinema.dto.movie;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "DTO для создания фильма")
public class CreateMovieDto {

    @Schema(description = "Название фильма")
    private String name;

    @Schema(description = "Описание фильма")
    private String description;
}
