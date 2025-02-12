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
public class MovieDto {
    @Schema(description = "Название фильма")
    private String name;
    @Schema(description = "Описание фильма")
    private String description;
    @Schema(description = "Id фильма")
    private Integer id;
}
