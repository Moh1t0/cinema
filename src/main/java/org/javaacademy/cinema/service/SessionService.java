package org.javaacademy.cinema.service;

import lombok.RequiredArgsConstructor;
import org.javaacademy.cinema.dto.SessionDto;
import org.javaacademy.cinema.entity.Place;
import org.javaacademy.cinema.entity.Session;
import org.javaacademy.cinema.entity.Ticket;
import org.javaacademy.cinema.mapper.SessionMapper;
import org.javaacademy.cinema.repository.PlaceRepository;
import org.javaacademy.cinema.repository.SessionRepository;
import org.javaacademy.cinema.repository.TicketRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SessionService {
    private final SessionRepository sessionRepository;
    private final SessionMapper mapper;
    private final PlaceRepository placeRepository;
    private final TicketRepository ticketRepository;

    public List<SessionDto> getAll() {
        return sessionRepository.selectAll().stream().map(mapper::convertToDto).toList();
    }

    public List<String> getFreePlace(Integer sessionId) {
        return ticketRepository.selectFreePlaces(sessionId);
    }

    public Session save(SessionDto sessionDto) {
        Session session = mapper.convertToEntity(sessionDto);
        List<Place> places = placeRepository.selectAll();
        places.stream()
                .map(place -> new Ticket(null, place, session, false))
                .forEach(ticketRepository::createTicket);
        return sessionRepository.createSession(session);
    }




















}
