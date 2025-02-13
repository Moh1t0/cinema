package org.javaacademy.cinema.repository;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.javaacademy.cinema.entity.Ticket;
import org.javaacademy.cinema.exeptions.BuyTicketUnableException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.sql.ResultSet;
import java.util.List;
import java.util.Optional;

import static java.util.Optional.empty;

@Repository
@RequiredArgsConstructor
public class TicketRepository {
    private final JdbcTemplate jdbcTemplate;
    private final PlaceRepository placeRepository;
    private final SessionRepository sessionRepository;

    public Optional<Ticket> findById(Integer id) {
        String sql = "select * from ticket where id = ?";
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, this::mapToTicket, id));
        } catch (EmptyResultDataAccessException e) {
            return empty();
        }
    }

    public Ticket createTicket(Ticket ticket) {
        String sql = "insert into ticket (place_id, session_id, is_bought) values(?, ?, ?) returning id";

        Integer id = jdbcTemplate.queryForObject(sql, Integer.class,
                ticket.getPlace().getId(),
                ticket.getSession().getId(),
                ticket.getIsBought());
        ticket.setId(id);
        return ticket;
    }

    public List<Ticket> selectBoughtTicket() {
        String sql = "select * from ticket where is_bought = true";
        return jdbcTemplate.query(sql, this::mapToTicket);
    }

    public List<String> selectFreePlaces(Integer sessionId) {
        String sql = """
                select p.number
                from ticket t
                inner join place p on t.place_id = p.id
                where is_bought = false and t.session_id = ?
                """;
        return jdbcTemplate.queryForList(sql, String.class, sessionId);
    }

    public Ticket findTicketBySessionAndPlaceName(Integer sessionId, String placeName) {
        String sql = """
                select *
                from ticket t
                inner join place p on p.id = t.place_id
                where session_id = ? and p.number = ?
                """;
        return jdbcTemplate.queryForObject(sql, this::mapToTicket, sessionId, placeName);
    }

    public Ticket buyTicketById(Integer id) {
        Ticket ticket = findById(id).orElseThrow(() ->
                new BuyTicketUnableException("Билет не найден!")
        );
        if (ticket.getIsBought()) {
            throw new IllegalStateException("Данный билет куплен !");
        }
        String sql = "update ticket set is_bought = true where id = ?";
        jdbcTemplate.update(sql, id);
        ticket.setIsBought(true);
        return ticket;
    }

    @SneakyThrows
    private Ticket mapToTicket(ResultSet rs, int rowNum) {
        Ticket ticket = new Ticket();
        ticket.setId(rs.getInt("id"));
        if (rs.getString("session_id") != null) {
            Integer sessionId = Integer.valueOf(rs.getString("session_id"));
            ticket.setSession(sessionRepository.findById(sessionId).orElse(null));
        }
        if (rs.getString("place_id") != null) {
            Integer placeId = Integer.valueOf(rs.getString("place_id"));
            ticket.setPlace(placeRepository.findById(placeId).orElse(null));
        }
        ticket.setIsBought(rs.getBoolean("is_bought"));
        return ticket;
    }
}
