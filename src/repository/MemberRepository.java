package repository;

import entity.Member;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MemberRepository {

    public MemberRepository() {
    }

    public void save(Connection conn, Member member) {
        String insertSQL = "insert into member (name, email, password, role) values (?, ?, ?, ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {
            pstmt.setString(1, member.getName());
            pstmt.setString(2, member.getEmail());
            pstmt.setString(3, member.getPassword());
            pstmt.setString(4, member.getRole());

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Failed to insert member");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
