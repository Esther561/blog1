package ictgradschool.project.servlet.action;

import ictgradschool.project.DAO.UserDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "DeleteAccount", urlPatterns = {"/deleteAccount"})
public class DeleteAccount extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userName = req.getParameter("userName");
        try {
            UserDAO.deleteUserByUserName(userName);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        resp.sendRedirect("./signOut");
    }
}
