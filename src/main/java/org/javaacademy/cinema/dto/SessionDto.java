package org.javaacademy.cinema.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "DTO для создания сеанса")
@JsonIgnoreProperties(ignoreUnknown = true)
public class SessionDto {

    @Schema(description = "id фильма")
    @JsonProperty("movie_id")
    private Integer movieId;

    @Schema(description = "Дата сеанса")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @JsonProperty("date_time")
    private LocalDateTime dateTime;

    @Schema(description = "Цена билета")
    private BigDecimal price;
}



















