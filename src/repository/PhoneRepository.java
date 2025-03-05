package repository;

import entity.Phone;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PhoneRepository {

    public PhoneRepository() {}

    public void save(Connection conn, Phone phone) {
        String insertSQL = "insert into phone (name, brand, regular_price, discount_amount, stock) values (?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {
            pstmt.setString(1, phone.getName());
            pstmt.setString(2, phone.getBrand());
            pstmt.setInt(3, phone.getRegularPrice());
            pstmt.setInt(4, phone.getDiscountAmount());
            pstmt.setInt(5, phone.getStock());

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Failed to insert phone");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
