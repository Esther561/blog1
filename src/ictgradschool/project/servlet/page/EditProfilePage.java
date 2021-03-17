package ictgradschool.project.servlet.page;

import ictgradschool.project.DAO.UserDAO;
import ictgradschool.project.util.AuthenticationUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "EditProfilePage", urlPatterns = {"/editProfilePage"})
public class EditProfilePage extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        boolean isUserLoggedIn = AuthenticationUtil.checkLogInStatus(req);
        if (!isUserLoggedIn) {
            resp.sendRedirect("./signInPage");
            return;
        }

        int numberOfDefaultAvatars = 29;
        List<String> defaultAvatars = new ArrayList<>();
        for (int i = 1; i <= numberOfDefaultAvatars; i++) {
            defaultAvatars.add("default/" + i + ".png");
        }
        req.setAttribute("defaultAvatars", defaultAvatars);

        req.getRequestDispatcher("/WEB-INF/jsp/editProfile.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
