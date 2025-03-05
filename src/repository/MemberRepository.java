package repository;

import entity.Member;

import java.sql.*;

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

    public Member findByEmailAndPassword(Connection conn, String email, String password) {
        String selectSQL = "select * from member where email = ? and password = ?";
        Member member = null;

        try (PreparedStatement pstmt = conn.prepareStatement(selectSQL)) {
            pstmt.setString(1, email);
            pstmt.setString(2, password);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    member = new Member();

                    member.setMemberId(rs.getLong("member_id"));
                    member.setName(rs.getString("name"));
                    member.setEmail(rs.getString("email"));
                    member.setPassword(rs.getString("password"));
                    member.setRole(rs.getString("role"));
                    Timestamp ts = rs.getTimestamp("created_at");

                    if (ts != null) {
                        member.setCreatedAt(ts.toLocalDateTime());
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return member;
    }

    public void updatePassword(Connection conn, Long memberId, String newPassword) {
        String updateSQL = "update member set password = ? where member_id = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(updateSQL)) {
            pstmt.setString(1, newPassword);
            pstmt.setLong(2, memberId);

            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
