package org.javaacademy.cinema.service;

import lombok.RequiredArgsConstructor;
import org.javaacademy.cinema.dto.movie.CreateMovieDto;
import org.javaacademy.cinema.dto.movie.MovieDto;
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

    public List<CreateMovieDto> getAll() {
        return mapper.toDtos(movieRepository.selectAll());
    }

    public MovieDto save(CreateMovieDto movieDto) {
        Movie movie = movieRepository.createMovie(mapper.convertToEntity(movieDto));
        return mapper.convertToDto(movie);
    }
























}
