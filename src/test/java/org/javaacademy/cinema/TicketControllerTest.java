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
import org.javaacademy.cinema.dto.ticket.BookingTicketDto;
import org.javaacademy.cinema.dto.ticket.TicketDto;
import org.javaacademy.cinema.mapper.SessionMapper;
import org.javaacademy.cinema.repository.PlaceRepository;
import org.javaacademy.cinema.service.MovieService;
import org.javaacademy.cinema.service.SessionService;
import org.javaacademy.cinema.service.TicketService;
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
public class TicketControllerTest {
    private final Header header = new Header("user-token", "secretadmin123");
    private final RequestSpecification requestSpecification = new RequestSpecBuilder()
            .setBasePath("/ticket")
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

    private static final   BigDecimal SESSION_PRICE = BigDecimal.valueOf(500);
    private static final LocalDateTime LOCAL_DATE_TIME =
            LocalDateTime.of(2025, 11, 11, 11, 11);
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private SessionService sessionService;
    @Autowired
    private MovieService movieService;
    @Autowired
    private PlaceRepository placeRepository;
    @Autowired
    private TicketService ticketService;
    @Autowired
    private SessionMapper sessionMapper;

    @BeforeEach
    public void cleanDatabase() {
        jdbcTemplate.execute(DELETE_TABLES);
    }

    @DisplayName("Успешная покупка билета")
    @Test
    public void bookingSuccess() {
        String expName = "test name";
        String expDesc = "test description";
        String expPlaceName = "A3";
        LocalDateTime dateTime = LOCAL_DATE_TIME;

        BookingTicketDto bookingTicketDto = createTicket(expName, expDesc, expPlaceName, dateTime);

        TicketDto ticketDto = given(requestSpecification)
                .body(bookingTicketDto)
                .post("/booking")
                .then()
                .spec(responseSpecification)
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(TicketDto.class);

        assertEquals(bookingTicketDto.getPlaceName(), ticketDto.getPlaceNumber());

    }

    @DisplayName("Успешное получение всех билетов")
    @Test
    public void getAllSuccess() {
        String expName = "test name";
        String expDesc = "test description";
        String expPlaceName = "A3";
        LocalDateTime dateTime = LOCAL_DATE_TIME;

        BookingTicketDto bookingTicketDto = createTicket(expName, expDesc, expPlaceName, dateTime);

        ticketService.booking(bookingTicketDto);

        List<TicketDto> ticketDtos = given(requestSpecification)
                .header(header)
                .get("/saled")
                .then()
                .spec(responseSpecification)
                .statusCode(HttpStatus.ACCEPTED.value())
                .extract()
                .as(new TypeRef<List<TicketDto>>() {
                });
        assertEquals(1, ticketDtos.size());
    }

    private BookingTicketDto createTicket(String name, String desc, String place, LocalDateTime date) {
        CreateMovieDto createMovieDto = CreateMovieDto.builder()
                .name(name)
                .description(desc)
                .build();
        int movieId = movieService.save(createMovieDto).getId();

        SessionDto createSessionDto = SessionDto.builder()
                .movieId(movieId)
                .dateTime(date)
                .price(SESSION_PRICE)
                .build();

        SessionDto sessionDto = sessionService.save(createSessionDto);

        sessionService.save(sessionDto);

        return BookingTicketDto.builder()
                .sessionId(sessionDto.getId())
                .placeName(place)
                .build();
    }
}
