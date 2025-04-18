package org.example.service;

import org.example.api.EventManager;
import org.example.model.DatabaseHelper;
import org.example.model.Event;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class EventManagerImpl implements EventManager {

    @Override
    public void addEvent(Event event) {
        String insertSQL = "INSERT INTO events(name, start, end) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseHelper.connect();
             PreparedStatement pstmt = conn.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, event.getName());
            pstmt.setString(2, event.getStart().toString());
            pstmt.setString(3, event.getEnd().toString());
            pstmt.executeUpdate();

            // Отримуємо згенерований id
            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int generatedId = generatedKeys.getInt(1);
                    event.setId(generatedId); // ⬅ Ось тут ми його ставимо в об'єкт
                }
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    @Override
    public void removeEventsByName(String name) {
        String format = "DELETE FROM events WHERE LOWER(name) = LOWER(?)";

        try (Connection connection = DatabaseHelper.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(format)) {

            preparedStatement.setString(1, name);
            int rows = preparedStatement.executeUpdate();

            System.out.println("\uD83D\uDDD1\uFE0F Deleted events with the name " + "\"" +name + "\": " + rows);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void removeAllEvents() {
        String format = "DELETE FROM events";

        try (Connection connection = DatabaseHelper.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(format)) {
            int rows = preparedStatement.executeUpdate();
            System.out.println("\uD83D\uDDD1\uFE0F Deleted all events: " + rows);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void removeEventById(int id) {
        String format = "DELETE FROM events WHERE id = ?";

        try (Connection connection = DatabaseHelper.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(format)) {
            preparedStatement.setInt(1, id);

            int rows = preparedStatement.executeUpdate();
            if (rows == 0) {
                System.out.println("⚠\uFE0F Event with Id = " + id + "not found!");
            } else {
                System.out.println("✅ Event with Id = " + id + " deleted.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Event> getEventBetween(LocalDateTime from, LocalDateTime to) {
        List<Event> events = new ArrayList<>();
        String format = "SELECT * FROM events WHERE START BETWEEN ? AND ?";

        try (Connection connection = DatabaseHelper.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(format);) {
            preparedStatement.setString(1, String.valueOf(from));
            preparedStatement.setString(2, String.valueOf(to));
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                events.add(mapResultSetToEvent(resultSet));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return events;
    }

    @Override
    public List<Event> getEventByName(String name) {
        List<Event> events = new ArrayList<>();
        String format = "SELECT * FROM events WHERE LOWER(name) = LOWER(?)";

        try (Connection connection = DatabaseHelper.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(format)) {

            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                events.add(mapResultSetToEvent(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return events;

    }

    @Override
    public List<Event> getEvents() {
        List<Event> events = new ArrayList<>();
        String format = "SELECT * FROM events";

        try (Connection connection = DatabaseHelper.connect();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(format)) {
            while (resultSet.next()) {
                events.add(mapResultSetToEvent(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (events.isEmpty()) {
            System.out.println("Events list is empty.");
        }
        return events;
    }

    @Override
    public void updateEventById(int id, Event event) {
        String format = "UPDATE events SET name = ?, start = ?, end = ? WHERE id = ?";

        try (Connection connection = DatabaseHelper.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(format)) {
            preparedStatement.setString(1, event.getName());
            preparedStatement.setString(2, String.valueOf(event.getStart()));
            preparedStatement.setString(3, String.valueOf(event.getEnd()));
            preparedStatement.setInt(4, id);

            int affectedRows = preparedStatement.executeUpdate();

            System.out.println(affectedRows == 0 ? "⚠️ Event with Id = " + id + " not found!" : "✅ Event updated.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Event mapResultSetToEvent(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String name = rs.getString("name");

        LocalDateTime start = LocalDateTime.parse(rs.getString("start"));
        LocalDateTime end = LocalDateTime.parse(rs.getString("end"));

        return new Event(id, name, start, end);
    }



}