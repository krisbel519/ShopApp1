package service;

import model.User;
import repository.DatabaseConnection;
import org.mindrot.jbcrypt.BCrypt;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthService {
    private final Connection connection;

    public AuthService() {
        this.connection = DatabaseConnection.getConnection();
    }

    // Rejestracja użytkownika
    public boolean register(String username, String password) {
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        String sql = "INSERT INTO users (username, password) VALUES (?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, hashedPassword);
            stmt.executeUpdate();
            System.out.println("✅ Rejestracja zakończona sukcesem!");
            return true;
        } catch (SQLException e) {
            System.err.println("❌ Błąd podczas rejestracji: " + e.getMessage());
            return false;
        }
    }

    // Logowanie użytkownika
    public User login(String username, String password) {
        String sql = "SELECT id, username, password FROM users WHERE username = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String storedHash = rs.getString("password");
                if (BCrypt.checkpw(password, storedHash)) {
                    System.out.println("✅ Zalogowano pomyślnie!");
                    return new User(rs.getInt("id"), rs.getString("username"), storedHash);
                }
            }
        } catch (SQLException e) {
            System.err.println("❌ Błąd podczas logowania: " + e.getMessage());
        }
        return null;
    }
}
