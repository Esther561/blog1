package ictgradschool.project.DAO;

import ictgradschool.project.model.User;
import ictgradschool.project.util.DBConnectionUtils;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class LikeDAO {
    public static boolean checkLikeArticleStatus(int userId, int articleId) throws IOException, SQLException {
        try (Connection conn = DBConnectionUtils.getConnectionFromClasspath("connection.properties")) {
            try (PreparedStatement ps = conn.prepareStatement("SELECT COUNT(*) AS count FROM likeArticle WHERE user = ? AND article = ?")) {
                ps.setInt(1, userId);
                ps.setInt(2, articleId);
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

    public static boolean checkLikeCommentStatus(int userId, int commentId) throws IOException, SQLException {
        try (Connection conn = DBConnectionUtils.getConnectionFromClasspath("connection.properties")) {
            try (PreparedStatement ps = conn.prepareStatement("SELECT COUNT(*) AS count FROM likeComment WHERE user = ? AND comment = ?")) {
                ps.setInt(1, userId);
                ps.setInt(2, commentId);
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

    public static boolean likeArticle(int userId, int articleId) throws IOException, SQLException {
        if (checkLikeArticleStatus(userId, articleId))
            return false;
        try (Connection conn = DBConnectionUtils.getConnectionFromClasspath("connection.properties")) {
            try (PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO likeArticle VALUES (?, ?)"
            )) {
                ps.setInt(1, userId);
                ps.setInt(2, articleId);
                return ps.executeUpdate() != 0;
            }
        }
    }

    public static boolean likeComment(int userId, int commentId) throws IOException, SQLException {
        if (checkLikeCommentStatus(userId, commentId))
            return false;
        try (Connection conn = DBConnectionUtils.getConnectionFromClasspath("connection.properties")) {
            try (PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO likeComment VALUES (?, ?)"
            )) {
                ps.setInt(1, userId);
                ps.setInt(2, commentId);
                return ps.executeUpdate() != 0;
            }
        }
    }

    public static boolean unlikeArticle(int userId, int articleId) throws IOException, SQLException {
        try (Connection conn = DBConnectionUtils.getConnectionFromClasspath("connection.properties")) {
            try (PreparedStatement ps = conn.prepareStatement("DELETE FROM likeArticle WHERE user = ? AND article = ?")) {
                ps.setInt(1, userId);
                ps.setInt(2, articleId);

                return ps.executeUpdate() != 0;
            }
        }
    }

    public static boolean unlikeComment(int userId, int commentId) throws IOException, SQLException {
        try (Connection conn = DBConnectionUtils.getConnectionFromClasspath("connection.properties")) {
            try (PreparedStatement ps = conn.prepareStatement("DELETE FROM likeComment WHERE user = ? AND comment = ?")) {
                ps.setInt(1, userId);
                ps.setInt(2, commentId);
                return ps.executeUpdate() != 0;
            }
        }
    }
}
