package com.example.mailingservice.config;

import com.example.mailingservice.service.registrationObserver.RegistrationAttemptObservable;
import com.example.mailingservice.service.registrationObserver.RegistrationAttemptObserver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ObservingConfig {
    @Bean
    public RegistrationAttemptObservable registrationAttemptObservable(RegistrationAttemptObserver mailingRegistrationAttemptObserver){
        RegistrationAttemptObservable registrationAttemptObservable = new RegistrationAttemptObservable();

        registrationAttemptObservable.subscribe(mailingRegistrationAttemptObserver);

        return registrationAttemptObservable;
    }
    
}
