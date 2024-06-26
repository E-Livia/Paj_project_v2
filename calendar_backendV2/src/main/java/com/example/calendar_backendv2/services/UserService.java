package com.example.calendar_backendv2.services;

import com.example.calendar_backendv2.models.User;
import com.example.calendar_backendv2.services.Database.DatabaseConnection;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.sql.*;

@Service
@Transactional
public class UserService {
    @PersistenceContext
    private EntityManager entityManager;
    @Transactional
    public int createUser(User user) throws SQLException {

        //  Query stmt = entityManager.createNativeQuery("CALL add_user(?, ?, ?, ?, ?, ?)");
        String sql = "{CALL add_user(?, ?, ?, ?, ?, ?, ?)}";
        int userId = 0;

        try (Connection connection = DatabaseConnection.getConnection();
             CallableStatement stmt = connection.prepareCall(sql)) {

            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getFirstName());
            stmt.setString(5, user.getLastName());
            stmt.setDate(6, new Date(user.getBirthdate().getTime()));
            stmt.registerOutParameter(7, Types.INTEGER);

            stmt.executeUpdate();
            userId = stmt.getInt(7);
        }
        return userId;
    }

    public User getUserByUsername(String username) throws SQLException {
        String sql = "{CALL get_user_by_username(?)}";
        User user = null;

        try (Connection connection = DatabaseConnection.getConnection();
             CallableStatement stmt = connection.prepareCall(sql)) {

            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                user = new User(
                        rs.getInt("user_id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("email"),
                        rs.getTimestamp("created_at"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getDate("birthdate")
                );
            }
        }
        return user;
    }

    public void updateUserByUsername(String username, User user) throws SQLException {
        String sql = "{CALL update_user_by_username(?, ?, ?, ?, ?, ?)}";

        try (Connection connection = DatabaseConnection.getConnection();
             CallableStatement stmt = connection.prepareCall(sql)) {

            stmt.setString(1, username);
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getFirstName());
            stmt.setString(5, user.getLastName());
            stmt.setDate(6, new Date(user.getBirthdate().getTime()));

            stmt.executeUpdate();
        }
    }

    public void deleteUserByUsername(String username) throws SQLException {
        String sql = "{CALL delete_user_by_username(?)}";

        try (Connection connection = DatabaseConnection.getConnection();
             CallableStatement stmt = connection.prepareCall(sql)) {

            stmt.setString(1, username);
            stmt.executeUpdate();
        }
    }

    public boolean isUsernameUnique(String username) throws SQLException {
        String sql = "{CALL check_username_unique(?, ?)}";
        int isUnique = 0;

        try (Connection connection = DatabaseConnection.getConnection();
             CallableStatement stmt = connection.prepareCall(sql)) {

            stmt.setString(1, username);
            stmt.registerOutParameter(2, Types.INTEGER);
            stmt.execute();

            isUnique = stmt.getInt(2);
        }

        return isUnique == 1;
    }

    public boolean isEmailUnique(String email) throws SQLException {
        String sql = "{CALL check_email_unique(?, ?)}";
        int isUnique = 0;

        try (Connection connection = DatabaseConnection.getConnection();
             CallableStatement stmt = connection.prepareCall(sql)) {

            stmt.setString(1, email);
            stmt.registerOutParameter(2, Types.INTEGER);
            stmt.execute();

            isUnique = stmt.getInt(2);
            isUnique = stmt.getInt(2);
        }

        return isUnique == 1;
    }

    public int getUserIdByUsername(String username) throws SQLException {
        String sql = "{CALL get_id_by_username(?)}";
        int userId = 0;

        try (Connection connection = DatabaseConnection.getConnection();
             CallableStatement stmt = connection.prepareCall(sql)) {

            stmt.setString(1, username);
            stmt.execute();
            userId = stmt.getInt(1); // Obține id-ul utilizatorului din rezultatul procedurii
        }
        return userId;
    }
}
