package com.example.calendar_backendv2.services;

import com.example.calendar_backendv2.models.Event;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.List;

public class EventService {

    private EntityManager entityManager;

    public EventService() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("myPersistenceUnit");
        this.entityManager = emf.createEntityManager();
    }

    public int createEvent(Event event) {
        entityManager.getTransaction().begin();
        entityManager.persist(event);
        entityManager.getTransaction().commit();
        return event.getId();
    }

    public Event getEventById(int eventId) {
        return entityManager.find(Event.class, eventId);
    }

    public List<Event> getAllEvents() {
        TypedQuery<Event> query = entityManager.createQuery("SELECT e FROM Event e", Event.class);
        return query.getResultList();
    }

    public void updateEvent(Event event) {
        entityManager.getTransaction().begin();
        entityManager.merge(event);
        entityManager.getTransaction().commit();
    }

    public void deleteEvent(int eventId) {
        Event event = entityManager.find(Event.class, eventId);
        if (event != null) {
            entityManager.getTransaction().begin();
            entityManager.remove(event);
            entityManager.getTransaction().commit();
        }
    }

    // Alte metode specifice necesare

    public void close() {
        entityManager.close();
    }
}
