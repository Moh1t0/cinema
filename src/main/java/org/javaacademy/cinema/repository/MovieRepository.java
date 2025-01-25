package org.javaacademy.cinema.repository;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.javaacademy.cinema.entity.Movie;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.sql.ResultSet;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MovieRepository {
    private final JdbcTemplate jdbcTemplate;

    public Optional<Movie> findById(Integer id) {
        String sql = "select * from movie where id = ?";
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, this::mapToMovie, id));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public Movie createMovie(Movie movie) {
        String sql = "insert into movie (name, description) values(?, ?) returning id";
        Integer id = jdbcTemplate.queryForObject(sql, Integer.class, movie.getName(), movie.getDescription());
        movie.setId(id);
       return movie;
    }

    public List<Movie> selectAll() {
        String sql = "select * from movie";
        return jdbcTemplate.query(sql, this::mapToMovie);
    }

    @SneakyThrows
    private Movie mapToMovie(ResultSet rs, int rowNum) {
        Movie movie = new Movie();
        movie.setId(rs.getInt("id"));
        movie.setName(rs.getString("name"));
        movie.setDescription(rs.getString("description"));
        return movie;
    }






























}
