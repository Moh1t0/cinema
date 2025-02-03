package org.javaacademy.cinema.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO при покупке билета")
public class BookingTicketDto {

    @Schema(description = "id сеанса")
    @JsonProperty("session_id")
    private Integer sessionId;

    @Schema(description = "номер места")
    @JsonProperty("place_name")
    private String placeName;
}
