package ictgradschool.project.DAO;

import ictgradschool.project.util.DBConnectionUtils;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TagDAO {

    public static List<String> getTagsByArticleId(Connection conn, int articleId) throws IOException, SQLException{
        try (PreparedStatement ps = conn.prepareStatement(
                "SELECT tag FROM tag WHERE article = ?")) {
            ps.setInt(1, articleId);
            try(ResultSet rs = ps.executeQuery()){
                List<String> tags = new ArrayList<>();
                while(rs.next()){
                    tags.add(rs.getString("tag"));
                }
                return tags;
            }
        }
    }

    public static boolean insertTags(Connection conn, int article, List<String> tags) throws IOException, SQLException {
        boolean result = false;
        removeAllTagsByArticle(conn, article);
        for (String tag : tags) {
            result = insertTag(conn, article, tag) || result;
        }
        return result;
    }

    private static void removeAllTagsByArticle(Connection conn, int article) throws IOException, SQLException {
            try (PreparedStatement ps = conn.prepareStatement("DELETE FROM tag WHERE article = ?")) {
                ps.setInt(1, article);
                ps.executeUpdate();
            }
    }

    private static boolean insertTag(Connection conn, int article, String tag) throws IOException, SQLException {
            try (PreparedStatement ps = conn.prepareStatement("INSERT INTO tag VALUES (?, ?)")) {
                ps.setInt(1, article);
                ps.setString(2, tag);
                return ps.executeUpdate() != 0;
            }

    }
}
