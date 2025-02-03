package org.javaacademy.cinema.mapper;


import lombok.RequiredArgsConstructor;
import org.javaacademy.cinema.dto.MovieDto;
import org.javaacademy.cinema.entity.Movie;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MovieMapper {

    public Movie convertToEntity(MovieDto movieDto) {
        return new Movie(null, movieDto.getName(), movieDto.getDescription());
    }
    public MovieDto convertToDto(Movie movie) {
        return new MovieDto(movie.getName(), movie.getDescription());
    }
}
