package ictgradschool.project.DAO;

import ictgradschool.project.model.Comment;
import ictgradschool.project.util.DBConnectionUtils;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CommentDAO {
    public static List<Comment> getCommentsByArticleId(Connection conn, int articleId) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(
                "select distinct c.id as id,content,time,parent,article,user,likes,c.isDeleted\n" +
                        "from comment as c\n" +
                        "    left join (select comment,count(*) as likes from likeComment group by comment) as l on c.id = l.comment\n" +
                        "    where c.article = ? and c.isDeleted = false" +
                        "    order by time desc;")) {
            ps.setInt(1, articleId);
            try (ResultSet rs = ps.executeQuery()) {
                List<Comment> comments = new ArrayList<>();
                while (rs.next()) {
                    int this_id = rs.getInt("id");
                    comments.add(new Comment(
                            this_id,
                            rs.getString("content"),
                            rs.getTimestamp("time"),
                            rs.getInt("article"),
                            rs.getInt("parent"),
                            UserDAO.getUserFromCommentId(conn, rs.getInt("parent")),
                            rs.getInt("likes"),
                            0, //level
                            UserDAO.getUserFromId(conn, rs.getInt("user")),
                            getCommentsByParentId(conn, this_id, 1)
                    ));
                }
                return comments;
            }
        }
    }

    private static List<Comment> getCommentsByParentId(Connection conn, int parentId, int currentLevel) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(
                "select distinct c.id as id,content,time,article,user,likes,c.isDeleted\n" +
                        "from comment as c\n" +
                        "    left join (select comment,count(*) as likes from likeComment group by comment) as l on c.id = l.comment\n" +
                        "    where c.parent = ? and c.isDeleted = false" +
                        "    order by time desc;")) {
            ps.setInt(1, parentId);
            try (ResultSet rs = ps.executeQuery()) {
                List<Comment> comments = new ArrayList<>();
                while (rs.next()) {
                    int this_id = rs.getInt("id");
                    comments.add(new Comment(
                            this_id,
                            rs.getString("content"),
                            rs.getTimestamp("time"),
                            rs.getInt("article"),
                            parentId,
                            UserDAO.getUserFromCommentId(conn, parentId),
                            rs.getInt("likes"),
                            currentLevel,
                            UserDAO.getUserFromId(conn, rs.getInt("user")),
                            getCommentsByParentId(conn, this_id, currentLevel + 1)
                    ));
                }
                return comments;
            }
        }
    }

    public static boolean deleteCommentByCommentId(int id) throws IOException, SQLException {
        try (Connection conn = DBConnectionUtils.getConnectionFromClasspath("connection.properties")) {
            try (PreparedStatement ps = conn.prepareStatement(
                    "UPDATE comment SET isDeleted = true WHERE id = ?")) {
                ps.setInt(1, id);
                return ps.executeUpdate() == 1;
            }
        }
    }

    public static boolean insertComment(Comment comment) throws IOException, SQLException {
        try (Connection conn = DBConnectionUtils.getConnectionFromClasspath("connection.properties")) {
            try (PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO comment VALUES (null,?,?,?,?,?,false)", Statement.RETURN_GENERATED_KEYS)) {
                ps.setString(1, comment.getContent());
                ps.setTimestamp(2, comment.getTime());
                if (comment.getParent() == null) {
                    ps.setNull(3, Types.INTEGER);
                } else {
                    ps.setInt(3, comment.getParent());
                }
                ps.setInt(4, comment.getUser().getId());
                if (comment.getArticle() == null) {
                    ps.setNull(5, Types.INTEGER);
                } else {
                    ps.setInt(5, comment.getArticle());
                }

                int rowAffected = ps.executeUpdate();
                if (rowAffected != 0) {
                    try (ResultSet rs = ps.getGeneratedKeys()) {
                        rs.next();
                        comment.setId(rs.getInt(1));
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
