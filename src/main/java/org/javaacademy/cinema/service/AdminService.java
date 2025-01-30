package org.javaacademy.cinema.service;

import lombok.RequiredArgsConstructor;
import org.javaacademy.cinema.repository.MovieRepository;
import org.javaacademy.cinema.repository.PlaceRepository;
import org.javaacademy.cinema.repository.SessionRepository;
import org.javaacademy.cinema.repository.TicketRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final MovieRepository movieRepository;
    private final PlaceRepository placeRepository;
    private final SessionRepository sessionRepository;
    private final TicketRepository ticketRepository;
}
