package com.example.mailingservice.model;

public record RegistrationAttempt(
        String nickname,
        String email,
        String id
) {
}
