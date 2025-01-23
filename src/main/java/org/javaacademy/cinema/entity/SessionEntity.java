package org.javaacademy.cinema.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class SessionEntity {
    private Integer id;
    private MovieEntity movie;
    private LocalDateTime dateTime;
    private BigDecimal price;
}
