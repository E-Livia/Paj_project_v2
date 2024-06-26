package com.example.calendar_backendv2.services.CrudTests;

import com.example.calendar_backendv2.models.User;
import com.example.calendar_backendv2.services.UserService;

import java.sql.SQLException;
import java.time.LocalDate;

public class UserTest {
    public static void main(String[] args) {

        //FOLOSIRE PROCEDURI STOCATE USER !!!

        UserService userService = new UserService();

        LocalDate birthday = LocalDate.of(2001, 7, 17);
        User newUser = new User(
                0, // va fi setat automat in baza de date
                "madalina_serban000",
                "password123",
               "madalina_serban002@example.com",
                null, // createdAt va fi setat automat de baza de date
                "Madalina",
                "Serban",
                java.sql.Date.valueOf(birthday)
         );

        try {

            // Verifică dacă username-ul este unic folosind procedura stocată
            if (!userService.isUsernameUnique(newUser.getUsername())) {
                System.out.println("Username is not unique!");
                return;
            }

            // Verifică dacă adresa de email este unică folosind procedura stocată
            if (!userService.isEmailUnique(newUser.getEmail())) {
                System.out.println("Email is not unique!");
                return;
            }


            // Adaugă utilizatorul în baza de date folosind procedura stocată
              int userId = userService.createUser(newUser);
              System.out.println("User added successfully with ID: " + userId);

            // Citește utilizatorul din baza de date folosind procedura stocată
            User retrievedUser = userService.getUserByUsername(newUser.getUsername());
            System.out.println("Retrieved User: " + retrievedUser);

            // Actualizează utilizatorul în baza de date folosind procedura stocată
            retrievedUser.setPassword("newpassword123");
            //userService.updateUserByUsername(newUser.getUsername(), retrievedUser);
            System.out.println("User updated successfully!");

            // Citește utilizatorul din nou pentru a verifica actualizarea
            User updatedUser = userService.getUserByUsername(newUser.getUsername());
            System.out.println("Updated User: " + updatedUser);

            // Șterge utilizatorul din baza de date folosind procedura stocată
            userService.deleteUserByUsername(newUser.getUsername());
            System.out.println("User deleted successfully!");

            // Încearcă citirea utilizatorul șters pentru a verifica ștergerea
            User deletedUser = userService.getUserByUsername(newUser.getUsername());
            if (deletedUser == null) {
                System.out.println("User not found, deletion confirmed!");
            } else {
                System.out.println("Error: User still exists!");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }





    }
}
