package service;

import repository.DatabaseConnection;
import model.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class CartService {
    private Map<Product, Integer> cart = new HashMap<>();

    public void addToCart(Product product, int quantity) {
        cart.put(product, cart.getOrDefault(product, 0) + quantity);
    }

    public void purchase() {
        try (Connection conn = DatabaseConnection.getConnection()) {
            for (Map.Entry<Product, Integer> entry : cart.entrySet()) {
                Product product = entry.getKey();
                int quantity = entry.getValue();

                try (PreparedStatement stmt = conn.prepareStatement(
                        "UPDATE products SET quantity = quantity - ? WHERE id = ?")) {
                    stmt.setInt(1, quantity);
                    stmt.setInt(2, product.getId());
                    stmt.executeUpdate();
                }
            }
            cart.clear();
            System.out.println("Zakup zakończony sukcesem!");
        } catch (SQLException e) {
            System.out.println("Błąd zakupu: " + e.getMessage());
        }
    }
}
