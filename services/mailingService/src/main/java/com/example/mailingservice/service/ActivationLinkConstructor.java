package com.example.mailingservice.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ActivationLinkConstructor {
    @Value("${api.activate.url}")
    private String activationUrl;

    public String constructActivationLink(String id) {
        return activationUrl + id;
    }
}
