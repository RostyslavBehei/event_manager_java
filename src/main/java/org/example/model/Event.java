package org.example.model;

import java.time.LocalDateTime;
import java.util.Objects;

public class Event {
    private int id;
    private String name;
    private LocalDateTime start;
    private LocalDateTime end;

    public Event(int id, String name, LocalDateTime start, LocalDateTime end) {
        this.id = id;
        this.name = name;
        this.start = start;
        this.end = end;
    }

    public Event(String name, LocalDateTime start, LocalDateTime end) {
        this.name = name;
        this.start = start;
        this.end = end;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return Objects.equals(name, event.name) && Objects.equals(start, event.start) && Objects.equals(end, event.end);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, start, end);
    }

    @Override
    public String toString() {
        return "Event: " +
                "Id = " + id +
                ", name = '" + name + '\'' +
                ", start = " + start.toLocalDate() +
                ", end = " + end.toLocalDate();
    }
}