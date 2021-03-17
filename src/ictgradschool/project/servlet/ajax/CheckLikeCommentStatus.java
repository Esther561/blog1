package ictgradschool.project.servlet.ajax;

import ictgradschool.project.DAO.LikeDAO;
import ictgradschool.project.JSON.ValidationResult;
import ictgradschool.project.util.JSONUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "CheckLikeCommentStatus", urlPatterns = {"/checkLikeCommentStatus"})
public class CheckLikeCommentStatus extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int user = Integer.parseInt(req.getParameter("user"));
        int comment = Integer.parseInt(req.getParameter("comment"));

        try {
            if (LikeDAO.checkLikeCommentStatus(user, comment)) {
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
