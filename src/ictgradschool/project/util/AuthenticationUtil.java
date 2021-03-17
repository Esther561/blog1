package ictgradschool.project.util;

import ictgradschool.project.DAO.UserDAO;
import ictgradschool.project.model.UserData;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Random;

public class AuthenticationUtil {

    private static final String logInToken = "loggedInUserName";

    public static UserData createUserData(String userName, String password) {
        Random random = new Random();
        int iteration = Math.abs(random.nextInt() % 100_000) + 1;
        int salt_length = Math.abs(random.nextInt() % 100) + 1;
        byte[] salt = PasswordUtil.getNextSalt(salt_length);
        byte[] passwordHash = PasswordUtil.hash(password.toCharArray(), salt, iteration);
        return new UserData(
                userName,
                PasswordUtil.base64Encode(passwordHash),
                PasswordUtil.base64Encode(salt),
                iteration
        );
    }

    public static boolean authenticate(UserData user, String password) {
        if (user == null)
            return false;

        return PasswordUtil.isExpectedPassword(
                password.toCharArray(),
                PasswordUtil.base64Decode(user.getSalt()),
                user.getIteration(),
                PasswordUtil.base64Decode(user.getPasswordHash())
        );
    }

    public static void signIn(HttpServletRequest req, String userName) {
        req.getSession(true).setAttribute(logInToken, userName);
    }

    public static void signOut(HttpServletRequest req) {
        HttpSession session = req.getSession(false);
        if (session != null) {
            session.removeAttribute(logInToken);
        }
    }

    public static String getLoggedInUserName(HttpServletRequest req) {
        return (String)req.getSession().getAttribute(logInToken);
    }

    public static boolean checkLogInStatus(HttpServletRequest req) throws IOException {
        boolean isUserLoggedIn;
        if (req.getSession() == null || req.getSession().getAttribute(logInToken) == null) {
            req.setAttribute("isUserLoggedIn", false);
            isUserLoggedIn = false;
        } else {
            req.setAttribute("isUserLoggedIn", true);
            isUserLoggedIn = true;
            String loggedInUserName = getLoggedInUserName(req);
            try {
                req.setAttribute("user", UserDAO.getUserFromUserName(loggedInUserName));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return isUserLoggedIn;
    }
}
