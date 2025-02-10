package org.javaacademy.cinema;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.javaacademy.cinema.dto.MovieDto;
import org.javaacademy.cinema.dto.SessionDto;
import org.javaacademy.cinema.service.MovieService;
import org.javaacademy.cinema.service.SessionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;

import java.math.BigDecimal;
import java.time.LocalDateTime;

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
            delete from place;
            delete from session;
            delete from movie;
            """;

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private SessionService sessionService;
    @Autowired
    private MovieService movieService;

    @BeforeEach
    public void cleanDatabase() {
        jdbcTemplate.execute(DELETE_TABLES);
    }

    @DisplayName("Успешное создание сеанса")
    @Test
    public void createSuccess() {
        MovieDto movieDto = MovieDto.builder()
                .name("test name")
                .description("test description")
                .build();
        int movieId = movieService.save(movieDto).getId();
        LocalDateTime date = LocalDateTime.of(
                2025,
                2,
                12,
                16,
                58,
                54);
        SessionDto sessionDto = SessionDto.builder()
                .dateTime(date)
                .movieId(movieId)
                .price(BigDecimal.TEN)
                .build();
        SessionDto result = given(requestSpecification)
                .header(header)
                .body(sessionDto)
                .post()
                .then()
                .spec(responseSpecification)
                .statusCode(200)
                .extract()
                .as(SessionDto.class);
        assertEquals(sessionDto.getPrice(), result.getPrice());
        assertEquals(sessionDto.getDateTime(), result.getDateTime());
        assertEquals(sessionDto.getMovieId(), result.getMovieId());
    }


























}
