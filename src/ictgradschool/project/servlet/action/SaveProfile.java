package ictgradschool.project.servlet.action;

import ictgradschool.project.DAO.UserDAO;
import ictgradschool.project.model.User;
import ictgradschool.project.servlet.FileUploadBase;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

@MultipartConfig
@WebServlet(name = "SaveProfile", urlPatterns = {"/saveProfile"})
public class SaveProfile extends FileUploadBase {

    @Override
    protected String getRelativePath() {
        return "/images/avatar";
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        DiskFileItemFactory factory = new DiskFileItemFactory();
        factory.setSizeThreshold(4 * 1024);
        factory.setRepository(tempFolder);
        ServletFileUpload upload = new ServletFileUpload(factory);

        User user = new User();
        String userName = "";
        String originalUserName = "";
        String originalAvatar = "";
        String defaultAvatar = "";
        boolean avatarFileUploaded = false;

        try {
            List<FileItem> fileItems = upload.parseRequest(req);
            File file;

            for (FileItem item : fileItems) {
                if (item.isFormField()) {
                    String fieldName = item.getFieldName();
                    String fieldValue = item.getString();
                    if (fieldName.equals("userName")) {
                        userName = fieldValue;
                    } else if (fieldName.equals("originalAvatar")) {
                        originalAvatar = fieldValue;
                        continue;
                    } else if (fieldName.equals("defaultAvatar")) {
                        defaultAvatar = fieldValue;
                        continue;
                    } else if (fieldName.equals("originalUserName")) {
                        originalUserName = fieldValue;
                        continue;
                    }
                    user.setField(fieldName, fieldValue);
                } else if (!item.isFormField() && acceptableMimeTypes.contains(item.getContentType())) {
                    String fileName = UUID.randomUUID() + "." + FilenameUtils.getExtension(item.getName());
                    file = new File(uploadsFolder, fileName);
                    item.write(file);
                    user.setField("avatar", fileName);
                    avatarFileUploaded = true;
                }
            }
        } catch (Exception e) {
            throw new ServletException(e);
        }

        if (defaultAvatar.equals("0") && !avatarFileUploaded) {
            user.setAvatar(originalAvatar);
        } else if (!defaultAvatar.equals("0") && !avatarFileUploaded) {
            user.setAvatar(defaultAvatar);
        }

        if (user.getUserName() == null || user.getUserName().isEmpty() || user.getUserName().isBlank()) {
            user.setUserName(originalUserName);
        }

        if (user.getNickname() == null || user.getNickname().isEmpty() || user.getNickname().isBlank()) {
            user.setNickname(user.getUserName());
        }

        try {
            UserDAO.saveProfile(user);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (!userName.equals(originalUserName)) {
            resp.sendRedirect("./signOut");
        } else {
            resp.sendRedirect("./articlesPage?userName=" + user.getUserName());
        }
    }
}
