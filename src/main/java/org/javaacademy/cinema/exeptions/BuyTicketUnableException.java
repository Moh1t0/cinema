package org.javaacademy.cinema.exeptions;

public class BuyTicketUnableException extends RuntimeException {
    public BuyTicketUnableException(String message) {
        super(message);
    }
}
