package org.javaacademy.cinema.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketEntity {
    private Integer id;
    private PlaceEntity place;
    private SessionEntity session;
    private Boolean isBought;
}
