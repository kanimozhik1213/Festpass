package com.example.Festpass.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.Festpass.Repository.Repository;
import com.example.Festpass.Model.Modelclass;

@Service
public class Serviceclass {

    private final Repository repository;

    public Serviceclass(Repository repository) {
        this.repository = repository;
    }

    // Create a new event
    public Modelclass createEvent(Modelclass event) {
        return repository.save(event);
    }

    // Get all events
    public List<Modelclass> getAllEvents() {
        return repository.findAll();
    }

    // Get event by ID
    public Modelclass getEventById(Long id) {
        return repository.findById(id).orElse(null);
    }
}