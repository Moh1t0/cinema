package org.javaacademy.cinema;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.common.mapper.TypeRef;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.javaacademy.cinema.dto.movie.CreateMovieDto;
import org.javaacademy.cinema.service.MovieService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class MovieControllerTest {

    private final Header header = new Header("user-token", "secretadmin123");
    private final RequestSpecification requestSpecification = new RequestSpecBuilder()
            .setBasePath("/movie")
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
    private MovieService movieService;

    @BeforeEach
    public void cleanDatabase() {
        jdbcTemplate.execute(DELETE_TABLES);
    }

    @DisplayName("Успешное создание фильма")
    @Test
    public void createSuccess() {
        CreateMovieDto expected = CreateMovieDto.builder()
                .name("test name")
                .description("test description")
                .build();

        CreateMovieDto result = given(requestSpecification)
                .header(header)
                .body(expected)
                .post()
                .then()
                .spec(responseSpecification)
                .statusCode(201)
                .extract()
                .as(CreateMovieDto.class);

        assertEquals(expected.getName(), result.getName());
        assertEquals(expected.getDescription(), result.getDescription());
    }

    @DisplayName("Успешное получение всех фильмов")
    @Test
    public void getAllSuccess() {
        String name = "test name";
        String description = "test description";
        CreateMovieDto firstMovieDto = CreateMovieDto.builder()
                .name(name)
                .description(description)
                .build();
        CreateMovieDto secondMovieDto = CreateMovieDto.builder()
                .name(name)
                .description(description)
                .build();
        movieService.save(firstMovieDto);
        movieService.save(secondMovieDto);

        List<CreateMovieDto> result = given(requestSpecification)
                .get()
                .then()
                .spec(responseSpecification)
                .statusCode(200)
                .extract()
                .as(new TypeRef<>() {
                });
        assertEquals(2, result.size());
        assertEquals(firstMovieDto.getName(), result.get(0).getName());
        assertEquals(firstMovieDto.getDescription(), result.get(0).getDescription());

    }
}
