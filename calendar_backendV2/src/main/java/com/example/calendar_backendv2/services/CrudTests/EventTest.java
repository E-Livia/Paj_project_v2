package com.example.calendar_backendv2.services.CrudTests;

import com.example.calendar_backendv2.models.Event;
import com.example.calendar_backendv2.services.EventService;

import java.time.LocalDate;
import java.time.LocalTime;

public class EventTest {
    public static void main(String[] args) {
        EventService eventService = new EventService();

        try {
            // Exemplu de adăugare a unui eveniment
            LocalDate startDate = LocalDate.of(2024, 6, 25);
            LocalTime startTime = LocalTime.of(10, 0);
            LocalDate endDate = LocalDate.of(2024, 6, 25);
            LocalTime endTime = LocalTime.of(12, 0);

            Event newEvent = new Event();
            newEvent.setUserId(1); // Setează ID-ul utilizatorului
            newEvent.setTitle("Meeting");
            newEvent.setDescription("Team meeting for project planning");
            newEvent.setStartDate(java.sql.Date.valueOf(startDate));
            newEvent.setStartTime(java.sql.Time.valueOf(startTime));
            newEvent.setEndDate(java.sql.Date.valueOf(endDate));
            newEvent.setEndTime(java.sql.Time.valueOf(endTime));
            newEvent.setLocationId(1); // Setează ID-ul locației
            newEvent.setDocString(""); // Setează docString-ul

            int eventId = eventService.createEvent(newEvent);
            System.out.println("Event added successfully with ID: " + eventId);

            // Exemplu de actualizare a unui eveniment
            /*newEvent.setTitle("Updated Meeting");
            newEvent.setDescription("Updated team meeting");
            eventService.updateEvent(newEvent);
            System.out.println("Event updated successfully!");*/

            // Exemplu de ștergere a unui eveniment
            /*eventService.deleteEvent(eventId);
            System.out.println("Event deleted successfully!");*/

        } finally {
            // Închide EntityManager după utilizare (de preferat într-un context mai larg)
            eventService.close();
        }
    }
}
