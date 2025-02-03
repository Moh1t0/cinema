package org.javaacademy.cinema.mapper;

import org.javaacademy.cinema.dto.TicketDto;
import org.javaacademy.cinema.entity.Ticket;
import org.springframework.stereotype.Component;

@Component
public class TicketMapper {
    public TicketDto convertToDto(Ticket ticket) {
        return new TicketDto(
                ticket.getId(),
                ticket.getPlace().getName(),
                ticket.getSession().getMovie().getName(),
                ticket.getSession().getDateTime());
    }
}
