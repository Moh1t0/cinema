package org.javaacademy.cinema.dto.ticket;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(description = "DTO при покупке билета")
public class BookingTicketDto {

    @Schema(description = "id сеанса")
    @JsonProperty("session_id")
    private Integer sessionId;

    @Schema(description = "номер места")
    @JsonProperty("place_number")
    private String placeName;
}
