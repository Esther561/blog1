package ictgradschool.project.servlet.action;

import ictgradschool.project.DAO.ArticleDAO;
import ictgradschool.project.model.Article;
import ictgradschool.project.servlet.FileUploadBase;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@MultipartConfig
@WebServlet(name = "PostArticle", urlPatterns = {"/postArticle"})
public class PostArticle extends FileUploadBase {

    @Override
    protected String getRelativePath() {
        return "/images/cover";
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        DiskFileItemFactory factory = new DiskFileItemFactory();
        factory.setSizeThreshold(4 * 1024);
        factory.setRepository(tempFolder);
        ServletFileUpload upload = new ServletFileUpload(factory);

        Article article = new Article();
        String originalCover = article.getCover();
        String newCover = null;

        try {
            List<FileItem> fileItems = upload.parseRequest(req);
            File file;

            for (FileItem item : fileItems) {
                if (item.isFormField()) {
                    String fieldName = item.getFieldName();
                    String fieldValue = item.getString();
                    if (fieldName.equals("originalCover") && !fieldValue.isEmpty()) {
                        originalCover = fieldValue;
                        continue;
                    }
                    article.setField(fieldName, fieldValue);
                } else if (!item.isFormField() && acceptableMimeTypes.contains(item.getContentType())) {
                    String fileName = UUID.randomUUID() + "." + FilenameUtils.getExtension(item.getName());
                    file = new File(uploadsFolder, fileName);
                    item.write(file);
                    article.setField("cover", fileName);
                    newCover = fileName;
                }
            }
        } catch (Exception e) {
            throw new ServletException(e);
        }

        article.setTime(new Timestamp(System.currentTimeMillis()));
        if (newCover == null || newCover.isEmpty()) {
            article.setCover(originalCover);
        }

        if (article.getTitle() == null || article.getTitle().isEmpty() || article.getTitle().isBlank()) {
            article.setTitle(article.getUser().getNickname() + "'s Article");
        }

        try {
            ArticleDAO.insertOrEditArticle(article);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        resp.sendRedirect("./articlePage?id=" + article.getId());
    }
}
