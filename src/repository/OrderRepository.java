package repository;

import entity.Orders;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class OrderRepository {

    public OrderRepository() {}

    public void save(Connection conn, Orders orders) {
        String insertSQL = "insert into orders (member_id, phone_id, quantity, order_regular_price, order_discount_amount) values (?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {
            pstmt.setLong(1, orders.getMemberId());
            pstmt.setLong(2, orders.getPhoneId());
            pstmt.setInt(3, orders.getQuantity());
            pstmt.setInt(4, orders.getOrderRegularPrice());
            pstmt.setInt(5, orders.getOrderDiscountAmount());

            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
