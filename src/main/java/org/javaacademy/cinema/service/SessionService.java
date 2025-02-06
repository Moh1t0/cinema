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
        return ticketRepository.selectNotBoughtTicket()
                .stream()
                .filter(ticket -> ticket.getSession().getId().equals(sessionId))
                .map(Ticket::getPlace)
                .map(Place::getName)
                .toList();
    }

    public Session save(SessionDto sessionDto) {
        Session session = mapper.convertToEntity(sessionDto);
        List<Place> places = placeRepository.selectAll();
        places.stream()
                .map(Place::getId)
                .map(placeId -> new Ticket(null,
                        placeRepository.findById(placeId).orElse(null),
                        sessionRepository.findById(session.getId()).orElse(null),
                        false))
                .forEach(ticketRepository::createTicket);
        return session;
    }
}
