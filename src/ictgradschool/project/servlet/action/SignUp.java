package ictgradschool.project.servlet.action;

import ictgradschool.project.DAO.UserDAO;
import ictgradschool.project.model.UserData;
import ictgradschool.project.util.AuthenticationUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "SignUp", urlPatterns = {"/signUp"})
public class SignUp extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        AuthenticationUtil.signOut(req);

        String userName = req.getParameter("userName");
        String password = req.getParameter("password");
        UserData user = AuthenticationUtil.createUserData(userName, password);
        try {
            if (UserDAO.getUserDataFromUserName(userName) != null) {
                resp.sendRedirect("./signUpPage");
            } else {
                UserDAO.insertUser(user);
                AuthenticationUtil.signIn(req, userName);
                resp.sendRedirect("./editProfilePage");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
