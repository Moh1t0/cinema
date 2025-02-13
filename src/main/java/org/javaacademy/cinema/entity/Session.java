package org.javaacademy.cinema.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Session {
    private Integer id;
    private Movie movie;
    private LocalDateTime dateTime;
    private BigDecimal price;

    public Session(Movie movie, LocalDateTime dateTime, BigDecimal price) {
        this.movie = movie;
        this.dateTime = dateTime;
        this.price = price;
    }
}
