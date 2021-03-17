package ictgradschool.project.servlet.ajax;

import ictgradschool.project.DAO.UserDAO;
import ictgradschool.project.JSON.ValidationResult;
import ictgradschool.project.util.JSONUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "ValidateUserName", urlPatterns = {"/validateUserName"})
public class ValidateUserName extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userName = req.getParameter("userName");
        try {
            if (UserDAO.getUserFromUserName(userName) == null) {
                JSONUtil.send(resp, new ValidationResult("success"));
            } else {
                JSONUtil.send(resp, new ValidationResult("failure"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
