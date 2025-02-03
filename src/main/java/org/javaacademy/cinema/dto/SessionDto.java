package org.javaacademy.cinema.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO для создания сеанса")
public class SessionDto {

    @Schema(description = "id фильма")
    @JsonProperty("movie_id")
    private Integer movieId;

    @Schema(description = "Дата сеанса")
    @JsonProperty("data_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dateTime;

    @Schema(description = "Цена билета")
    private BigDecimal price;
}
