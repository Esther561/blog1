package ictgradschool.project.servlet.page;

import ictgradschool.project.DAO.ArticleDAO;
import ictgradschool.project.model.Article;
import ictgradschool.project.model.Comment;
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

@WebServlet(name = "ArticlePage", urlPatterns = {"/articlePage"})
public class ArticlePage extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        AuthenticationUtil.checkLogInStatus(req);
        int id = 0;
        try {
            id = Integer.parseInt(req.getParameter("id"));
        } catch (Exception e) {
            resp.sendRedirect("./indexPage");
        }
        try {
            Article article = ArticleDAO.getArticleByArticleId(id);
            if (article == null) {
                resp.sendRedirect("./indexPage");
                return;
            }
            article.setComments(flattenCommentList(article.getComments()));
            req.setAttribute("article", article);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        req.getRequestDispatcher("/WEB-INF/jsp/article.jsp").forward(req, resp);
    }

    private List<Comment> flattenCommentList(List<Comment> comments) {
        if (comments.size() == 0)
            return new ArrayList<>();
        List<Comment> flattened = new ArrayList<>();
        for (Comment comment : comments) {
            flattened.add(comment);
            flattened.addAll(flattenCommentList(comment.getComments()));
        }
        return flattened;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
