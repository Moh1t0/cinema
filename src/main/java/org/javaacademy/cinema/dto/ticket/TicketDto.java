package org.javaacademy.cinema.dto.ticket;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "DTO билета при покупке")
@JsonIgnoreProperties(ignoreUnknown = true)
public class TicketDto {

    @Schema(description = "id билета")
    private Integer id;

    @Schema(description = "номер места")
    @JsonProperty("place_number")
    private String placeNumber;

    @Schema(description = "Название фильма")
    @JsonProperty("movie_name")
    private String movieName;

    @Schema(description = "Дата сеанса")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime date;
}
