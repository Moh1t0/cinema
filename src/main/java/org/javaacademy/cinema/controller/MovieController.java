package org.javaacademy.cinema.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.javaacademy.cinema.dto.MovieDto;
import org.javaacademy.cinema.service.MovieService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/movie")
@Tag(name = "Фильм", description = "Контроллер для работы с фильмами")
public class MovieController {
    private static final String SECRET_TOKEN = "secretadmin123";
    private final MovieService movieService;


    @Operation(summary = "Список всех фильмов", description = "Возвращает список всех фильмов",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Список всех фильмов",
                            content = @Content(array = @ArraySchema(schema = @Schema(implementation = MovieDto.class))))
            })
    @GetMapping
    public List<MovieDto> getAll() {
        return movieService.getAll();
    }

    @Operation(summary = "Сохранение фильма в БД",
            description = "Создание фильма и сохранение в БД",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Фильм успешно сохранен",
                            content = @Content(schema = @Schema(implementation = MovieDto.class))),
                    @ApiResponse(responseCode = "403", description = "Нет доступа!")
            })
    @PostMapping
    public ResponseEntity<?> save(@RequestHeader(value = "user-token") String userToken,
                                   @RequestBody MovieDto movieDto) {
        if (SECRET_TOKEN.equals(userToken)) {
            return ResponseEntity.status(HttpStatus.CREATED).body(movieService.save(movieDto));
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Нет доступа!");
    }
}
