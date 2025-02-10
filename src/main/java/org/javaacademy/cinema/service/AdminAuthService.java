package org.javaacademy.cinema.service;

import org.springframework.stereotype.Service;

@Service
public class AdminAuthService {
    private static final String SECRET_TOKEN = "secretadmin123";

    public boolean isAdmin(String userToken) {
        return SECRET_TOKEN.equals(userToken);
    }
}
