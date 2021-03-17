package ictgradschool.project.servlet.page;

import ictgradschool.project.DAO.ArticleDAO;
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

@WebServlet(name = "ArticlesPage", urlPatterns = {"/articlesPage"})
public class ArticlesPage extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        AuthenticationUtil.checkLogInStatus(req);
        String userName = req.getParameter("userName");
        if (userName == null) {
            resp.sendRedirect("./indexPage");
            return;
        }
        try {
            User author = UserDAO.getUserFromUserName(userName);
            if (author == null) {
                resp.sendRedirect("./indexPage");
                return;
            } else {
                req.setAttribute("author", author);
            }
            req.setAttribute("articleSummaries", ArticleDAO.getArticleSummariesByUserName(userName));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        req.getRequestDispatcher("/WEB-INF/jsp/blog.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
