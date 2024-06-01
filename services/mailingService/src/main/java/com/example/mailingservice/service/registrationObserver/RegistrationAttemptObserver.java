package com.example.mailingservice.service.registrationObserver;

import com.example.mailingservice.model.RegistrationAttempt;

public interface RegistrationAttemptObserver {
    void update(RegistrationAttempt message);
}
