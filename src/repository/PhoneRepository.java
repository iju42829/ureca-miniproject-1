package repository;

import entity.Phone;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

    public List<Phone> findAll(Connection conn) {
        List<Phone> phones = new ArrayList();
        String selectSQL = "select * from phone";

        try (PreparedStatement pstmt = conn.prepareStatement(selectSQL)) {
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Phone phone = new Phone();

                phone.setPhoneId(rs.getLong("phone_id"));
                phone.setName(rs.getString("name"));
                phone.setBrand(rs.getString("brand"));
                phone.setRegularPrice(rs.getInt("regular_price"));
                phone.setDiscountAmount(rs.getInt("discount_amount"));
                phone.setStock(rs.getInt("stock"));

                phones.add(phone);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return phones;
    }
}
