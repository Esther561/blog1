package ictgradschool.project.servlet.page;

import ictgradschool.project.DAO.ArticleDAO;
import ictgradschool.project.DAO.UserDAO;
import ictgradschool.project.model.Article;
import ictgradschool.project.util.AuthenticationUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "EditArticlePage", urlPatterns = {"/editArticlePage"})
public class EditArticlePage extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        boolean isUserLoggedIn = AuthenticationUtil.checkLogInStatus(req);
        if (!isUserLoggedIn) {
            resp.sendRedirect("./signInPage");
            return;
        }
        String idString = req.getParameter("id");
        if (idString == null) {
            String userName = AuthenticationUtil.getLoggedInUserName(req);
            try {
                req.setAttribute("article", new Article(UserDAO.getUserFromUserName(userName)));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            int id = Integer.parseInt(req.getParameter("id"));
            try {
                Article article = ArticleDAO.getArticleByArticleId(id);
                if (article == null || !article.getUser().getUserName().equals(AuthenticationUtil.getLoggedInUserName(req))) {
                    req.getRequestDispatcher("/WEB-INF/jsp/error.jsp").forward(req, resp);
                    return;
                }
                req.setAttribute("article", article);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        req.getRequestDispatcher("/WEB-INF/jsp/editArticle.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
