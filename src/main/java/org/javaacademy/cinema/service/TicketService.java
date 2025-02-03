package org.javaacademy.cinema.service;

import lombok.RequiredArgsConstructor;
import org.javaacademy.cinema.dto.BookingTicketDto;
import org.javaacademy.cinema.dto.TicketDto;
import org.javaacademy.cinema.entity.Ticket;
import org.javaacademy.cinema.mapper.TicketMapper;
import org.javaacademy.cinema.repository.TicketRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class TicketService {
    private final TicketRepository ticketRepository;
    private final TicketMapper mapper;

    public TicketDto booking(BookingTicketDto bookingTicketDto) {
        Integer sessionId = bookingTicketDto.getSessionId();
        String placeName = bookingTicketDto.getPlaceName();

        Ticket ticket = Stream.concat(ticketRepository.selectNotBoughtTicket().stream(),
                        ticketRepository.selectBoughtTicket().stream())
                .filter(t -> t.getSession().getId().equals(sessionId))
                .filter(t -> t.getPlace().getName().equals(placeName))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Билет не найден!"));

        ticketRepository.buyTicketById(ticket.getId());
        return mapper.convertToDto(ticket);
    }

    public List<Ticket> getSaledTicket() {
        return ticketRepository.selectBoughtTicket();
    }
}
