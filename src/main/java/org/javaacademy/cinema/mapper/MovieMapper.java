package org.javaacademy.cinema.mapper;

import org.javaacademy.cinema.dto.movie.CreateMovieDto;
import org.javaacademy.cinema.dto.movie.MovieDto;
import org.javaacademy.cinema.entity.Movie;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MovieMapper {

    public Movie convertToEntity(CreateMovieDto dto) {
        return new Movie(dto.getName(), dto.getDescription());
    }
    public MovieDto convertToDto(Movie movie) {
        return MovieDto.builder()
                .id(movie.getId())
                .name(movie.getName())
                .description(movie.getDescription())
                .build();
    }

    public CreateMovieDto toCreatedMovieDto(Movie movie) {
        return CreateMovieDto.builder()
                .name(movie.getName())
                .description(movie.getDescription())
                .build();
    }

    public List<CreateMovieDto> toDtos(List<Movie> movies) {
        return movies.stream()
                .map(this::toCreatedMovieDto)
                .toList();
    }
}
