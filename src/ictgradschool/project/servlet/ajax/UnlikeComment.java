package ictgradschool.project.servlet.ajax;

import ictgradschool.project.DAO.LikeDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "UnlikeComment", urlPatterns = {"/unlikeComment"})
public class UnlikeComment extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int user = Integer.parseInt(req.getParameter("user"));
        int comment = Integer.parseInt(req.getParameter("comment"));
        try {
            LikeDAO.unlikeComment(user, comment);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
