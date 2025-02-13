package org.javaacademy.cinema;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.common.mapper.TypeRef;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.javaacademy.cinema.dto.SessionDto;
import org.javaacademy.cinema.dto.movie.CreateMovieDto;
import org.javaacademy.cinema.dto.movie.MovieDto;
import org.javaacademy.cinema.repository.PlaceRepository;
import org.javaacademy.cinema.repository.TicketRepository;
import org.javaacademy.cinema.service.MovieService;
import org.javaacademy.cinema.service.SessionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class SessionControllerTest {
    private final Header header = new Header("user-token", "secretadmin123");
    private final RequestSpecification requestSpecification = new RequestSpecBuilder()
            .setBasePath("/session")
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();
    private final ResponseSpecification responseSpecification = new ResponseSpecBuilder()
            .log(LogDetail.ALL)
            .build();
    private static final String DELETE_TABLES = """
            delete from ticket;
            delete from session;
            delete from movie;
            """;
    private static final int SESSION_COUNT = 3;
    private static final LocalDateTime SESSION_TEST_LOCAL_DATE_TIME =
            LocalDateTime.of(2025, 2, 12, 22, 17);

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private SessionService sessionService;
    @Autowired
    private MovieService movieService;
    @Autowired
    private PlaceRepository placeRepository;
    @Autowired
    private TicketRepository ticketRepository;

    @BeforeEach
    public void cleanDatabase() {
        jdbcTemplate.execute(DELETE_TABLES);
    }

    @DisplayName("Успешное создание сеанса")
    @Test
    public void createSuccess() {
        CreateMovieDto movieDto = CreateMovieDto.builder()
                .name("test name")
                .description("test description")
                .build();
        int movieId = movieService.save(movieDto).getId();
        SessionDto sessionDto = SessionDto.builder()
                .dateTime(SESSION_TEST_LOCAL_DATE_TIME)
                .movieId(movieId)
                .price(BigDecimal.TEN)
                .build();
        SessionDto result = given(requestSpecification)
                .header(header)
                .body(sessionDto)
                .post()
                .then()
                .spec(responseSpecification)
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(SessionDto.class);
        assertEquals(sessionDto.getPrice(), result.getPrice());
        assertEquals(sessionDto.getDateTime(), result.getDateTime());
        assertEquals(sessionDto.getMovieId(), result.getMovieId());
    }

    @DisplayName("Успешное получение всех сеансов")
    @Test
    public void getAllSuccess() {
        generateSessionWithMovies("test", "test", BigDecimal.TEN);

        List<SessionDto> sessionDtos = given(requestSpecification)
                .get()
                .then()
                .spec(responseSpecification)
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(new TypeRef<>() {
                });
        assertEquals(SESSION_COUNT, sessionDtos.size());
    }

    @DisplayName("Успешное получение свободных мест")
    @Test
    public void getFreePlacesSuccess() {
        CreateMovieDto movieDto = CreateMovieDto.builder()
                .name("Test Movie")
                .description("Test description")
                .build();
        int movieId = movieService.save(movieDto).getId();

        LocalDateTime dateTime = LocalDateTime.now();
        SessionDto sessionDto = SessionDto.builder()
                .movieId(movieId)
                .price(BigDecimal.TEN)
                .dateTime(dateTime)
                .build();
        SessionDto savedSession = sessionService.save(sessionDto);

        given(requestSpecification)
                .header(header)
                .body(sessionDto)
                .post()
                .then()
                .spec(responseSpecification)
                .statusCode(HttpStatus.OK.value());
        String expected = "[\"A1\",\"A2\",\"A3\",\"A4\",\"A5\",\"B1\",\"B2\",\"B3\",\"B4\",\"B5\"]";

        String response = given(requestSpecification)
                .param("sessionId", savedSession.getId())
                .get("/" + savedSession.getId() + "/free-place")
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .body()
                .asString();
        assertEquals(expected, response);
    }

    private void generateSessionWithMovies(String movieName, String movieDescription, BigDecimal price) {
        for (int i = 1; i <= SESSION_COUNT; i++) {
            CreateMovieDto createMovieDto = CreateMovieDto.builder()
                    .name(movieName)
                    .description(movieDescription)
                    .build();
            MovieDto savedMovieDto = movieService.save(createMovieDto);
            LocalDateTime dateTime = LocalDateTime.now();
            SessionDto sessionDto = SessionDto.builder()
                    .movieId(savedMovieDto.getId())
                    .price(price)
                    .dateTime(dateTime)
                    .build();
            sessionService.save(sessionDto);
        }
    }
}



