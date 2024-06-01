package com.example.mailingservice;

import com.example.mailingservice.model.RegistrationAttempt;

import java.util.LinkedList;
import java.util.List;

public class RegistrationAttemptObservable {
    List<RegistrationAttemptObserver> observers = new LinkedList<>();
    public void subscribe(RegistrationAttemptObserver registrationAttemptObserver){
        observers.add(registrationAttemptObserver);
    }

    public void unsubscribe(RegistrationAttemptObserver registrationAttemptObserver){
        observers.remove(registrationAttemptObserver);
    }

    public void notifyObservers(RegistrationAttempt message){
        for (var observer :
                observers) {
            observer.update(message);
        }
    }
}
