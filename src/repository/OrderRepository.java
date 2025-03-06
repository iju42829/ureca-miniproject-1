package repository;

import dto.order.OrderListDto;
import entity.Orders;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

    public List<OrderListDto> findOrdersByMemberId(Connection conn, Long memberId) {
        List<OrderListDto> orders = new ArrayList<>();

        String selectSQL = "select * from orders o join phone p on p.phone_id = o.phone_id where o.member_id = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(selectSQL)) {
            pstmt.setLong(1, memberId);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    orders.add(new OrderListDto(rs.getString("name"),
                            rs.getString("brand"),
                            rs.getTimestamp("order_date").toLocalDateTime(),
                            rs.getInt("quantity"),
                            rs.getInt("order_regular_price"),
                            rs.getInt("order_discount_amount")));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return orders;
    }
}
