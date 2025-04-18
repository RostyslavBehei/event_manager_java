package org.example.api;

import org.example.model.Event;

import java.time.LocalDateTime;
import java.util.List;

public interface EventManager {
    void addEvent(Event event);
    void removeEventsByName(String name);
    void removeAllEvents();
    void removeEventById(int id);

    List<Event> getEventBetween(LocalDateTime from, LocalDateTime to);
    List<Event> getEventByName(String name);
    List<Event> getEvents();

    void updateEventById(int id, Event event);
}
