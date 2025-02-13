package org.javaacademy.cinema.mapper;

import lombok.RequiredArgsConstructor;
import org.javaacademy.cinema.dto.SessionDto;
import org.javaacademy.cinema.entity.Movie;
import org.javaacademy.cinema.entity.Session;
import org.javaacademy.cinema.repository.MovieRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SessionMapper {
        private final MovieRepository movieRepository;

    public Session convertToEntity(SessionDto sessionDto) {
        Integer movieId = sessionDto.getMovieId();
        Movie movie = movieRepository.findById(sessionDto.getMovieId())
                .orElseThrow(() -> new RuntimeException("Билет с id " + movieId + " не найден"));
        return Session.builder()
                .id(sessionDto.getId())
                .movie(movie)
                .dateTime(sessionDto.getDateTime())
                .price(sessionDto.getPrice())
                .build();
    }

    public SessionDto convertToDto(Session session) {
        return SessionDto.builder()
                .id(session.getId())
                .movieId(session.getMovie().getId())
                .dateTime(session.getDateTime())
                .price(session.getPrice())
                .build();
    }
}
