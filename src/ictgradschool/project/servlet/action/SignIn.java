package ictgradschool.project.servlet.action;

import ictgradschool.project.DAO.UserDAO;
import ictgradschool.project.util.AuthenticationUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "SignIn", urlPatterns = {"/signIn"})
public class SignIn extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userName = req.getParameter("userName");
        String password = req.getParameter("password");
        try {
            if (AuthenticationUtil.authenticate(UserDAO.getUserDataFromUserName(userName), password)) {
                AuthenticationUtil.signIn(req, userName);
                resp.sendRedirect("./indexPage");
            } else {
                req.setAttribute("hasLogInFailed", true);
                req.getRequestDispatcher("/signInPage").forward(req, resp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
