package com.example.mailingservice;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class RegistrationAttemptTopicListener {
    @KafkaListener(id = "RegistrationAttemptTopicListener", topics = "kyt.registration.registration-attempted")
    public void listen(String id) {
        System.out.println(id);
    }
}
