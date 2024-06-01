package com.example.mailingservice;

import com.example.mailingservice.model.RegistrationAttempt;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class RegistrationAttemptTopicListener {
    @KafkaListener(id = "RegistrationAttemptTopicListener", topics = "kyt.registration.registration-attempted")
    public void listen(RegistrationAttempt registrationAttempt) {
        System.out.println(registrationAttempt);
    }
}
