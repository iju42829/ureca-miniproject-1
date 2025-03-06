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
        String selectSQL = "select * from phone where is_deleted = false";

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

    public void updateStock(Connection conn, Long phoneId, int newStock) {
        String sql = "update phone set stock = ? where phone_id = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, newStock);
            pstmt.setLong(2, phoneId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Phone findByName(Connection conn, String name) {
        String selectSQL = "select * from phone where name = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(selectSQL)) {
            pstmt.setString(1, name);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return new Phone(
                        rs.getLong("phone_id"),
                        rs.getString("name"),
                        rs.getString("brand"),
                        rs.getInt("regular_price"),
                        rs.getInt("discount_amount"),
                        rs.getInt("stock"),
                        rs.getBoolean("is_deleted")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void deleteById(Connection conn, Long phoneId) {
        String deleteSQL = "update phone set is_deleted = true where phone_id = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(deleteSQL)) {
            pstmt.setLong(1, phoneId);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
