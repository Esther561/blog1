package ictgradschool.project.servlet.ajax;

import ictgradschool.project.DAO.FollowDAO;
import ictgradschool.project.JSON.ValidationResult;
import ictgradschool.project.util.JSONUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "CheckFollowStatus", urlPatterns = {"/checkFollowStatus"})
public class CheckFollowStatus extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int followee = Integer.parseInt(req.getParameter("followee"));
        int follower = Integer.parseInt(req.getParameter("follower"));

        try {
            if (FollowDAO.checkFollowStatus(followee, follower)) {
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
