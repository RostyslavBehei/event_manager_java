package org.example.service;

import org.example.model.DatabaseHelper;
import org.example.model.Event;
import org.junit.jupiter.api.*;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EventManagerImplTest {
    private static EventManagerImpl eventManager;

    @BeforeAll
    static void setUp() throws SQLException {
        DatabaseHelper.overrideDbUrl();
        DatabaseHelper.createTable();
        eventManager = new EventManagerImpl();
    }

    @AfterEach
    void tearDown() throws SQLException {
        eventManager.removeAllEvents();
    }

    @Test
    void addEvent() {
        Event event = new Event("Test event", LocalDateTime.now(), LocalDateTime.now().plusHours(1));
        eventManager.addEvent(event);

        List<Event> events = eventManager.getEvents();
        assertEquals(1, events.size());
    }

    @Test
    void removeEventsByName() {
        eventManager.addEvent(new Event("Test event", LocalDateTime.now(), LocalDateTime.now().plusHours(1)));
        assertFalse(eventManager.getEvents().isEmpty());
        eventManager.removeEventsByName("Test event");
        assertEquals(0, eventManager.getEvents().size());
    }

    @Test
    void removeAllEvents() {
        eventManager.addEvent(new Event("Test event", LocalDateTime.now(), LocalDateTime.now().plusHours(1)));
        eventManager.addEvent(new Event("Test event 2", LocalDateTime.now(), LocalDateTime.now().plusHours(1)));

        eventManager.removeAllEvents();
        assertTrue(eventManager.getEvents().isEmpty());
    }

    @Test
    void removeEventById() {
        eventManager.addEvent(new Event("Test event", LocalDateTime.now(), LocalDateTime.now().plusHours(1)));
        List<Event> events = eventManager.getEvents();
        assertFalse(events.isEmpty());

        int id = events.getFirst().getId();
        eventManager.removeEventById(id);
        assertTrue(eventManager.getEvents().isEmpty());

    }

    @Test
    void getEventBetween() {
        eventManager.removeAllEvents(); // очищення перед тестом

        Event event1 = new Event("Test event1", LocalDateTime.of(2001, 12,1,12,5),
                LocalDateTime.of(2002, 7,6,12,5));
        Event event2 = new Event("Test event2", LocalDateTime.of(2005, 1,5,10,7),
                LocalDateTime.of(2006, 12,12,12,5));
        Event event3 = new Event("Out of range", LocalDateTime.of(1995, 1,1,12,5),
                LocalDateTime.of(1996, 1,1,12,5));

        eventManager.addEvent(event1);
        eventManager.addEvent(event2);
        eventManager.addEvent(event3);

        List<Event> eventsInRange = eventManager.getEventBetween(
                LocalDateTime.of(2000, 1,1,12,5),
                LocalDateTime.of(2007, 1,5,10,7)
        );

        assertEquals(2, eventsInRange.size());
        assertTrue(eventsInRange.stream().anyMatch(e -> e.getName().equals("Test event1")));
        assertTrue(eventsInRange.stream().anyMatch(e -> e.getName().equals("Test event2")));
    }


    @Test
    void getEventByName() {
        eventManager.removeAllEvents(); // на всяк випадок, чистимо

        Event event = new Event("Test event", LocalDateTime.now(), LocalDateTime.now().plusHours(1));
        eventManager.addEvent(event);

        List<Event> foundEvents = eventManager.getEventByName("Test event");

        assertEquals(1, foundEvents.size());
        Event found = foundEvents.get(0);
        assertEquals("Test event", found.getName());
        assertEquals(event.getStart(), found.getStart());
        assertEquals(event.getEnd(), found.getEnd());
    }


    @Test
    void getEvents() {
        Event event1 = new Event("Test event1", LocalDateTime.of(2001, 12,1,12,5),
                LocalDateTime.of(2002, 7,6,12,5));
        Event event2 = new Event("Test event2", LocalDateTime.of(2005, 1,5,10,7),
                LocalDateTime.of(2006, 12,12,12,5));
        Event event3 = new Event("Out of range", LocalDateTime.of(1995, 1,1,12,5),
                LocalDateTime.of(1996, 1,1,12,5));

        eventManager.addEvent(event1);
        eventManager.addEvent(event2);
        eventManager.addEvent(event3);

        assertEquals(3, eventManager.getEvents().size());
    }

    @Test
    void updateEvent() {
        Event originalEvent = new Event("Original Event", LocalDateTime.of(2023, 1, 1, 10, 0),
                LocalDateTime.of(2023, 1, 1, 12, 0));
        eventManager.addEvent(originalEvent);
        int id = eventManager.getEvents().getFirst().getId();

        Event updatedEvent = new Event("Updated Event", LocalDateTime.of(2023, 2, 2, 10, 0),
                LocalDateTime.of(2023, 2, 2, 12, 0));
        eventManager.updateEventById(id ,updatedEvent);

        Event result = eventManager.getEvents().getFirst();
        assertEquals("Updated Event", result.getName());
        assertEquals(LocalDateTime.of(2023, 2, 2, 10, 0), result.getStart());
        assertEquals(LocalDateTime.of(2023, 2, 2, 12, 0), result.getEnd());
    }

}