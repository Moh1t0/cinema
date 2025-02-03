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
            Movie movie = movieRepository.findById(sessionDto.getMovieId()).orElseThrow();
            return new Session(null, movie, sessionDto.getDateTime(), sessionDto.getPrice());
        }

        public SessionDto convertToDto(Session session) {
            Movie movie = movieRepository.findById(session.getMovie().getId()).orElseThrow();
            return new SessionDto(session.getId(), session.getDateTime(), session.getPrice());
        }
}
