package com.example.course_project_2023.service.kafka;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaService {
    private final KafkaTemplate<String, String> kafkaTemplate;

    public void sendRegistrationAttemptMessage(final String message) {
        final ProducerRecord<String, String> record =
                new ProducerRecord<>("kyt.registration.registration-attempted", message);

        kafkaTemplate.send(record);
    }
}