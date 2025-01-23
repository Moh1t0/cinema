package org.javaacademy.cinema.repository;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.javaacademy.cinema.entity.SessionEntity;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.sql.ResultSet;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class SessionRepository {
    private final JdbcTemplate jdbcTemplate;
    private final MovieRepository movieRepository;


    public Optional<SessionEntity> findById(Integer id) {
        String sql = "select * from session where id = ?";
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, this::mapToSession, id));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @SneakyThrows
    private SessionEntity mapToSession(ResultSet rs, int rowNum) {
        SessionEntity sessionEntity = new SessionEntity();
        sessionEntity.setId(rs.getInt("id"));
        sessionEntity.setPrice(rs.getBigDecimal("price"));
        sessionEntity.setDateTime(rs.getTimestamp("time").toLocalDateTime());
        if (rs.getString("movie_id") != null) {
            Integer movieId = Integer.valueOf(rs.getString("movie_id"));
            sessionEntity.setMovie(movieRepository.findById(movieId).orElse(null));
        }
        return sessionEntity;
    }





























}
