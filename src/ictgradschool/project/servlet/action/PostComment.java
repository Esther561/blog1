package ictgradschool.project.servlet.action;

import ictgradschool.project.DAO.CommentDAO;
import ictgradschool.project.DAO.UserDAO;
import ictgradschool.project.model.Comment;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;

@WebServlet(name = "PostComment", urlPatterns = {"/postComment"})
public class PostComment extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String content = req.getParameter("content");
        String parentString = req.getParameter("parent");
        Integer parent = null;
        String articleString = req.getParameter("article");
        Integer article = null;
        if (parentString != null && !parentString.isEmpty()) {
            parent = Integer.parseInt(parentString);
        } else {
            article = Integer.parseInt(articleString);
        }
        String userName = req.getParameter("userName");
        Timestamp time = new Timestamp(System.currentTimeMillis());
        Comment comment = new Comment();
        try {
            comment = new Comment(null, content, time, UserDAO.getUserFromUserName(userName), article, parent);
            CommentDAO.insertComment(comment);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        resp.sendRedirect("./articlePage?id=" + articleString + "#comment_" + comment.getId());
    }
}
