package com.example.Festpass.Controller;

import java.util.List;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.Festpass.Model.Modelclass;
import com.example.Festpass.Service.Serviceclass;

@RestController
@RequestMapping("/events")
public class Controllerclass {

    private final Serviceclass serviceclass;

    public Controllerclass(Serviceclass serviceclass) {
        this.serviceclass = serviceclass;
    }

    // Create event
    @PostMapping
    public Modelclass createEvent(@RequestBody Modelclass event) {
        return serviceclass.createEvent(event);
    }

    // Get all events
    @GetMapping
    public List<Modelclass> getAllEvents() {
        return serviceclass.getAllEvents();
    }

    // Get event by ID
    @GetMapping("/{id}")
    public Modelclass getEventById(@PathVariable Long id) {
        return serviceclass.getEventById(id);
    }
}