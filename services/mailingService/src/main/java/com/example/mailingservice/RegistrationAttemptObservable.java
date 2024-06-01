package com.example.mailingservice;

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

    public void notifyObservers(String message){
        for (var observer :
                observers) {
            observer.update(message);
        }
    }
}
