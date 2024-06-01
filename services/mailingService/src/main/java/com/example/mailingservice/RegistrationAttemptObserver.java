package com.example.mailingservice;

import com.example.mailingservice.model.RegistrationAttempt;

public interface RegistrationAttemptObserver {
    void update(RegistrationAttempt message);
}
