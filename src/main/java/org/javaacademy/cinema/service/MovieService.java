package org.javaacademy.cinema.service;

import lombok.RequiredArgsConstructor;
import org.javaacademy.cinema.dto.MovieDto;
import org.javaacademy.cinema.entity.Movie;
import org.javaacademy.cinema.mapper.MovieMapper;
import org.javaacademy.cinema.repository.MovieRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MovieService {
    private final MovieRepository movieRepository;
    private final MovieMapper mapper;

    public List<MovieDto> getAll() {
        return movieRepository.selectAll().stream().map(mapper::convertToDto).toList();
    }

    public Movie save(MovieDto movieDto) {
        return movieRepository.createMovie(mapper.convertToEntity(movieDto));
    }
}
