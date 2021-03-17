package ictgradschool.project.servlet.ajax;

import ictgradschool.project.DAO.FollowDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "Unfollow", urlPatterns = {"/unfollow"})
public class Unfollow extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int followee = Integer.parseInt(req.getParameter("followee"));
        int follower = Integer.parseInt(req.getParameter("follower"));
        try {
            FollowDAO.unfollow(followee, follower);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

