package com.example.calendar_backendv2.services.CrudTests;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class DatabaseConnectionTest {
    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = null;
        EntityManager entityManager = null;

        try {
            // Obține EntityManagerFactory din persistence unit definit în persistence.xml
            entityManagerFactory = Persistence.createEntityManagerFactory("myPersistenceUnit");

            // Obține EntityManager din EntityManagerFactory
            entityManager = entityManagerFactory.createEntityManager();

            // Verifică dacă EntityManager este deschis și afișează un mesaj de confirmare
            if (entityManager.isOpen()) {
                System.out.println("Conexiunea la baza de date a fost stabilită cu succes!");
            } else {
                System.out.println("Eroare la stabilirea conexiunii la baza de date!");
            }
        } catch (Exception ex) {
            System.err.println("Eroare la inițializarea EntityManagerFactory: " + ex.getMessage());
        } finally {
            // Închide EntityManager și EntityManagerFactory în mod corect
            if (entityManager != null) {
                entityManager.close();
            }
            if (entityManagerFactory != null) {
                entityManagerFactory.close();
            }
        }
    }
}
