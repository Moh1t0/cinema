package org.javaacademy.cinema.repository;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.javaacademy.cinema.entity.Session;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.sql.ResultSet;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Slf4j
public class SessionRepository {
    private final JdbcTemplate jdbcTemplate;
    private final MovieRepository movieRepository;

    public Optional<Session> findById(Integer id) {
        String sql = "select * from session where id = ?";
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, this::mapToSession, id));
        } catch (EmptyResultDataAccessException e) {
            log.error("Сессия с id {} не найдена", id);
            return Optional.empty();
        }
    }

    public List<Session> selectAll() {
        String sql = "select * from session";
        return jdbcTemplate.query(sql, this::mapToSession);
    }

    public Session createSession(Session session) {
        String sql = "insert into session (movie_id, time, price) values(?, ?, ?) returning id";

        Integer id = jdbcTemplate.queryForObject(sql, Integer.class,
                session.getMovie().getId(),
                session.getDateTime(),
                session.getPrice());
        session.setId(id);
        log.info("Сессия сохранена с id {}", id);
        return session;
    }

    @SneakyThrows
    private Session mapToSession(ResultSet rs, int rowNum) {
        Session session = new Session();
        session.setId(rs.getInt("id"));
        session.setPrice(rs.getBigDecimal("price"));
        session.setDateTime(rs.getTimestamp("time").toLocalDateTime());

        log.info("Загруженная сессия с id: {}, dateTime: {}", session.getId(), session.getDateTime());

        if (rs.getString("movie_id") != null) {
            Integer movieId = Integer.valueOf(rs.getString("movie_id"));
            session.setMovie(movieRepository.findById(movieId).orElse(null));
        }
        return session;
    }
}
