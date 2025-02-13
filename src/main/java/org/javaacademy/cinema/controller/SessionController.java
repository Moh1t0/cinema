package org.javaacademy.cinema.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.javaacademy.cinema.dto.SessionDto;
import org.javaacademy.cinema.mapper.SessionMapper;
import org.javaacademy.cinema.service.AdminAuthService;
import org.javaacademy.cinema.service.SessionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/session")
@Tag(name = "Сеанс", description = "Контроллер для работы с сеансами")
public class SessionController {
    private final SessionService sessionService;
    private final AdminAuthService adminAuthService;
    private final SessionMapper mapper;

    @Operation(summary = "Список всех сеансов", description = "Возвращает список всех сеансов",
            responses = @ApiResponse(responseCode = "200", description = "Список сеансов",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = SessionDto.class)))))
    @GetMapping
    public List<SessionDto> getAll() {
        return sessionService.getAll();
    }

    @Operation(summary = "Показ всех свободных мест на сеанс",
            description = "Возврощает список всех свободных мест на сеанс по id",
            responses = @ApiResponse(responseCode = "200", description = "Список свободных мест",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = String.class)))))
    @GetMapping("/{sessionId}/free-place")
    public List<String> getFreePlace(@PathVariable Integer sessionId) {
        return sessionService.getFreePlace(sessionId);
    }

    @Operation(summary = "Создание и сохранение сеанса в БД",
            description = "Создание сеанса по id фильма, цене билета, дате и сохранение сеанса в БД",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Сеанс сохранен",
                            content = @Content(schema = @Schema(implementation = SessionDto.class))),
                    @ApiResponse(responseCode = "403", description = "Нет доступа!")
            })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> save(@RequestHeader(value = "user-token") String userToken,
                                  @RequestBody SessionDto sessionDto) {

        if (adminAuthService.isAdmin(userToken)) {
            SessionDto savedSession = sessionService.save(sessionDto);
            return ResponseEntity.ok(savedSession);
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Нет доступа!");
    }
}
