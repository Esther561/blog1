package ictgradschool.project.DAO;

import ictgradschool.project.model.Article;
import ictgradschool.project.model.ArticleSummary;
import ictgradschool.project.util.DBConnectionUtils;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ArticleDAO {
    public static List<ArticleSummary> getAllArticleSummaries() throws IOException, SQLException {
        try (Connection conn = DBConnectionUtils.getConnectionFromClasspath("connection.properties")) {
            try (PreparedStatement ps = conn.prepareStatement(
                    "select distinct a.id as id,title,content,cover,user,time,likes,isDeleted\n" +
                            "    from article as a\n" +
                            "    left join (select article, count(*) as likes from likeArticle group by article) as l on a.id = l.article\n" +
                            "    where isDeleted = false order by time desc")) {
                return assembleArticleSummaries(conn, ps);
            }
        }
    }

    public static List<ArticleSummary> getArticleSummariesByUserName(String userName)throws IOException, SQLException  {
        try (Connection conn = DBConnectionUtils.getConnectionFromClasspath("connection.properties")) {
            try (PreparedStatement ps = conn.prepareStatement(
                    "select distinct a.id as id,title,content,cover,user,userName,time,likes,isDeleted\n" +
                            "from article as a\n" +
                            "inner join user as u on a.user = u.id\n" +
                            "left join (select article, count(*) as likes from likeArticle group by article) as l on a.id = l.article\n" +
                            "where userName = ? and isDeleted = false order by time desc")) {
                ps.setString(1, userName);
                return assembleArticleSummaries(conn, ps);
            }
        }
    }

    public static List<ArticleSummary> getArticleSummariesByUserLike(String userName) throws IOException, SQLException {
        try (Connection conn = DBConnectionUtils.getConnectionFromClasspath("connection.properties")) {
            try (PreparedStatement ps = conn.prepareStatement(
                    "select distinct a.id as id,title,content,cover,a.user as user, userName, time,likes,isDeleted\n" +
                            "from article as a\n" +
                            "inner join likeArticle as la on a.id = la.article\n" +
                            "inner join user as u on la.user = u.id\n" +
                            "left join (select article, count(*) as likes from likeArticle group by article) as l on a.id = l.article\n" +
                            "where userName = ? and isDeleted = false order by time desc")) {
                ps.setString(1, userName);
                return assembleArticleSummaries(conn, ps);
            }
        }
    }

    public static List<ArticleSummary> getArticleSummariesByTag(String tag) throws IOException, SQLException {
        try (Connection conn = DBConnectionUtils.getConnectionFromClasspath("connection.properties")) {
            try (PreparedStatement ps = conn.prepareStatement(
                    "select distinct a.id as id,title,content,cover,user,time,likes,isDeleted\n" +
                            "from article as a\n" +
                            "inner join tag as t on a.id = t.article\n" +
                            "left join (select article, count(*) as likes from likeArticle group by article) as l on a.id = l.article\n" +
                            "where tag = ? and isDeleted = false order by time desc")) {
                ps.setString(1, tag);
                return assembleArticleSummaries(conn, ps);
            }
        }
    }

    private static List<ArticleSummary> assembleArticleSummaries(Connection conn, PreparedStatement ps) throws SQLException, IOException {
        try(ResultSet rs = ps.executeQuery()){
            List<ArticleSummary> articleSummaries = new ArrayList<>();
            while(rs.next()){
                String content = rs.getString("content");
                String contentSummary = content.length() > 300 ? content.substring(0,299) + "..." : content;
                ArticleSummary summary = new ArticleSummary(
                        rs.getInt("id"),
                        rs.getString("title"),
                        contentSummary,
                        rs.getString("cover"),
                        UserDAO.getUserFromId(conn, rs.getInt("user")),
                        rs.getTimestamp("time"),
                        rs.getInt("likes"),
                        TagDAO.getTagsByArticleId(conn, rs.getInt("id"))
                );
                articleSummaries.add(summary);
            }
            return articleSummaries;
        }
    }

    public static Article getArticleByArticleId(int id) throws IOException, SQLException  {
        try (Connection conn = DBConnectionUtils.getConnectionFromClasspath("connection.properties")) {
            try (PreparedStatement ps = conn.prepareStatement(
                    "select distinct a.id as id,title,content,time,cover,user,likes,isDeleted\n" +
                            "from article as a\n" +
                            "left join (select article, count(*) as likes from likeArticle group by article) as l on a.id = l.article\n" +
                            "where a.id = ? and isDeleted = false")) {
                ps.setInt(1,id);
                try(ResultSet rs = ps.executeQuery()){
                    if(rs.next()) return new Article(
                            rs.getInt("id"),
                            rs.getString("title"),
                            rs.getString("content"),
                            rs.getTimestamp("time"),
                            rs.getString("cover"),
                            UserDAO.getUserFromId(rs.getInt("user")),
                            rs.getInt("likes"),
                            TagDAO.getTagsByArticleId(conn, id),
                            CommentDAO.getCommentsByArticleId(conn, id)
                    );
                    else return null;
                }
            }
        }
    }

    public static boolean insertOrEditArticle(Article article) throws IOException, SQLException {
        if (article.getId() == null) {
            return insertArticle(article);
        } else {
            return editArticle(article);
        }
    }

    private static boolean insertArticle(Article article) throws IOException, SQLException  {
        try (Connection conn = DBConnectionUtils.getConnectionFromClasspath("connection.properties")) {
            //insert article into table `article`
            boolean articleInsert = false;
            try (PreparedStatement ps = conn.prepareStatement(
                    "INSERT article VALUES (null,?,?,?,?,?,false)", Statement.RETURN_GENERATED_KEYS)) {
                ps.setString(1,article.getTitle());
                ps.setString(2,article.getContent());
                ps.setTimestamp(3,article.getTime());
                ps.setString(4,article.getCover());
                ps.setInt(5,article.getUser().getId());

                int rowAffected = ps.executeUpdate();
                if(rowAffected != 0) {
                    try(ResultSet rs = ps.getGeneratedKeys()){
                        rs.next();
                        article.setId(rs.getInt(1));
                        articleInsert = true;
                    }
                }
            }
            //insert tags into table `tag`
            return articleInsert && TagDAO.insertTags(conn, article.getId(), article.getTags());
        }
    }

    private static boolean editArticle(Article article) throws IOException, SQLException  {
        boolean articleUpdate;
        boolean tagUpdate;
        try (Connection conn = DBConnectionUtils.getConnectionFromClasspath("connection.properties")) {
            //insert article into table `article`
            try (PreparedStatement ps = conn.prepareStatement(
                    "UPDATE article set title = ?, content = ?, cover = ? WHERE id = ?")) {
                ps.setString(1,article.getTitle());
                ps.setString(2,article.getContent());
                ps.setString(3,article.getCover());
                ps.setInt(4,article.getId());
                articleUpdate = (ps.executeUpdate() == 1);
            }
            tagUpdate = TagDAO.insertTags(conn, article.getId(), article.getTags());
        }

        return articleUpdate && tagUpdate;
    }

    public static boolean deleteArticleByArticleId(int id) throws IOException, SQLException  {
        try (Connection conn = DBConnectionUtils.getConnectionFromClasspath("connection.properties")) {
            try (PreparedStatement ps = conn.prepareStatement(
                    "UPDATE article SET isDeleted = true WHERE id = ?")) {
                ps.setInt(1,id);
                return ps.executeUpdate() == 1;
            }
        }
    }

}
