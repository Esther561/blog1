package ictgradschool.project.DAO;

import ictgradschool.project.model.User;
import ictgradschool.project.util.DBConnectionUtils;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class FollowDAO {
    public static boolean checkFollowStatus(int followeeId, int followerId) throws IOException, SQLException {
        try (Connection conn = DBConnectionUtils.getConnectionFromClasspath("connection.properties")) {
            try (PreparedStatement ps = conn.prepareStatement("SELECT COUNT(*) AS count FROM follow WHERE followee = ? AND follower = ?")) {
                ps.setInt(1, followeeId);
                ps.setInt(2, followerId);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        int count = rs.getInt(1);
                        return count != 0;
                    }
                }
            }
        }
        return false;
    }

    public static boolean follow(int followeeId, int followerId) throws IOException, SQLException {
        if (checkFollowStatus(followeeId, followerId))
            return false;
        try (Connection conn = DBConnectionUtils.getConnectionFromClasspath("connection.properties")) {
            try (PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO follow VALUES (?, ?)"
            )) {
                ps.setInt(1, followeeId);
                ps.setInt(2, followerId);
                return ps.executeUpdate() != 0;
            }
        }
    }

    public static boolean unfollow(int followeeId, int followerId) throws IOException, SQLException {
        try (Connection conn = DBConnectionUtils.getConnectionFromClasspath("connection.properties")) {
            try (PreparedStatement ps = conn.prepareStatement("DELETE FROM follow WHERE followee = ? AND follower = ?")) {
                ps.setInt(1, followeeId);
                ps.setInt(2, followerId);

                return ps.executeUpdate() != 0;
            }
        }
    }
}
