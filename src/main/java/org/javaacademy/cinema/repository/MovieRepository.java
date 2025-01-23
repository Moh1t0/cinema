package org.javaacademy.cinema.repository;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.javaacademy.cinema.entity.MovieEntity;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.sql.ResultSet;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MovieRepository {
    private final JdbcTemplate jdbcTemplate;

    public Optional<MovieEntity> findById(Integer id) {
        String sql = "select * from movie where id = ?";
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, this::mapToMovie, id));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @SneakyThrows
    private MovieEntity mapToMovie(ResultSet rs, int rowNum) {
        MovieEntity movieEntity = new MovieEntity();
        movieEntity.setId(rs.getInt("id"));
        movieEntity.setName(rs.getString("name"));
        movieEntity.setDescription(rs.getString("description"));
        return movieEntity;
    }
}
