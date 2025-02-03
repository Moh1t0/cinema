package org.javaacademy.cinema.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO для создания фильма")
public class MovieDto {

    @Schema(description = "Название фильма")
    private String name;

    @Schema(description = "Описание фильма")
    private String description;
}
