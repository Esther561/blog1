package ictgradschool.project.servlet.page;

import ictgradschool.project.DAO.UserDAO;
import ictgradschool.project.model.User;
import ictgradschool.project.util.AuthenticationUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "FollowersPage", urlPatterns = {"/followersPage"})
public class FollowersPage extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        AuthenticationUtil.checkLogInStatus(req);
        List<User> users = new ArrayList<>();
        int follower = 0;
        try {
            follower = Integer.parseInt(req.getParameter("follower"));
        } catch (Exception e) {
            resp.sendRedirect("./indexPage");
        }
        try {
            users = UserDAO.getFolloweeByFollower(follower);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        req.setAttribute("authors", users);
        req.getRequestDispatcher("/WEB-INF/jsp/authors.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
