package org.javaacademy.cinema.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.relational.core.sql.In;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "DTO для создания фильма")
public class MovieDto {

    @Schema(description = "Название фильма")
    private String name;

    @Schema(description = "Описание фильма")
    private String description;

    @Schema(description = "Id фильма")
    private Integer id;
}
