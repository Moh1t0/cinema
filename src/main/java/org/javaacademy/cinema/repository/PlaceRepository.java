package org.javaacademy.cinema.repository;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.javaacademy.cinema.entity.PlaceEntity;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class PlaceRepository {
    private final JdbcTemplate jdbcTemplate;

    public Optional<PlaceEntity> findById(Integer id) {
        String sql = "select * from place where id = ?";
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, this::mapToPlace, id));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @SneakyThrows
    private PlaceEntity mapToPlace(ResultSet rs, int rowNum) {
        PlaceEntity placeEntity = new PlaceEntity();
        placeEntity.setId(rs.getInt("id"));
        placeEntity.setNumber(rs.getInt("number"));
        return placeEntity;
    }
























}
