package com.example.course_project_2023.service.kafka;

import com.example.course_project_2023.service.dto.kafka.RegistrationAttempt;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaService {
    private final KafkaTemplate<String, RegistrationAttempt> kafkaTemplate;

    public void sendRegistrationAttemptMessage(final RegistrationAttempt registrationAttempt) {
        final ProducerRecord<String, RegistrationAttempt> record =
                new ProducerRecord<>("kyt.registration.registration-attempted", registrationAttempt);

        kafkaTemplate.send(record);
    }
}