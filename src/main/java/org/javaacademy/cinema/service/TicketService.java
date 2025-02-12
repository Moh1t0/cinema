package org.javaacademy.cinema.service;

import lombok.RequiredArgsConstructor;
import org.javaacademy.cinema.dto.ticket.BookingTicketDto;
import org.javaacademy.cinema.dto.ticket.TicketDto;
import org.javaacademy.cinema.entity.Ticket;
import org.javaacademy.cinema.mapper.TicketMapper;
import org.javaacademy.cinema.repository.TicketRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TicketService {
    private final TicketRepository ticketRepository;
    private final TicketMapper mapper;

    public TicketDto booking(BookingTicketDto bookingTicketDto) {
        Integer sessionId = bookingTicketDto.getSessionId();
        String placeName = bookingTicketDto.getPlaceName();

        Ticket ticket = ticketRepository.findTicketBySessionAndPlaceName(sessionId, placeName);

        ticketRepository.buyTicketById(ticket.getId());
        return mapper.convertToDto(ticket);
    }

    public List<Ticket> getSaledTicket() {
        return ticketRepository.selectBoughtTicket();
    }



















}
