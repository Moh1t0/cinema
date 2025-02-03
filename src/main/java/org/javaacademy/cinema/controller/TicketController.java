package org.javaacademy.cinema.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.javaacademy.cinema.dto.BookingTicketDto;
import org.javaacademy.cinema.dto.TicketDto;
import org.javaacademy.cinema.service.TicketService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/ticket")
@Tag(name = "Билет", description = "Контроллер для работы с билетами для администратора и пользователя")
public class TicketController {
    private static final String SECRET_TOKEN = "secretadmin123";
    private final TicketService ticketService;

    @Operation(summary = "Покупка билета",
            description = "EndPoint для покупки билета по id и номеру сеанса",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Успешная покупка билета",
                            content = @Content(schema = @Schema(implementation = BookingTicketDto.class)))})
    @PostMapping("/booking")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> bookingTicket(@RequestBody BookingTicketDto bookingTicketDto) {
        return ResponseEntity.ok(ticketService.booking(bookingTicketDto));
    }

    @Operation(summary = "Список купленных билетов",
            description = "Показывает список всех купленных билетов",
            responses = @ApiResponse(responseCode = "200", description = "Список все купленных билетов",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = TicketDto.class)))))
    @GetMapping("/saled")
    public ResponseEntity<?> getSaledTicket(@RequestHeader(value = "user-token") String userToken) {
        if (SECRET_TOKEN.equals(userToken)) {
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(ticketService.getSaledTicket());
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Нет доступа!");
    }
}
