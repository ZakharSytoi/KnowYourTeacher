package com.example.mailingservice.service;

import com.example.mailingservice.model.RegistrationAttempt;
import com.example.mailingservice.service.registrationObserver.RegistrationAttemptObservable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class RegistrationAttemptTopicListener {

    private final RegistrationAttemptObservable registrationAttemptObservable;

    @Autowired
    public RegistrationAttemptTopicListener(RegistrationAttemptObservable registrationAttemptObservable) {
        this.registrationAttemptObservable = registrationAttemptObservable;
    }

    @KafkaListener(id = "RegistrationAttemptTopicListener", topics = "kyt.registration.registration-attempted")
    public void listen(RegistrationAttempt registrationAttempt) {
        System.out.println(registrationAttempt);

        registrationAttemptObservable.notifyObservers(registrationAttempt);
    }
}
