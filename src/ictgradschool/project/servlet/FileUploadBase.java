package ictgradschool.project.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import java.io.File;
import java.util.Arrays;
import java.util.List;

public abstract class FileUploadBase extends HttpServlet {
    protected File uploadsFolder;
    protected File tempFolder;
    protected final List<String> acceptableMimeTypes = Arrays.asList("image/png", "image/jpeg", "image/jpg", "image/gif");
    protected abstract String getRelativePath();

    @Override
    public void init() throws ServletException {
        super.init();

        this.uploadsFolder = new File(getServletContext().getRealPath(getRelativePath()));
        if (!uploadsFolder.exists()) {
            uploadsFolder.mkdirs();
        }

        this.tempFolder = new File(getServletContext().getRealPath("/WEB-INF/temp"));
        if (!tempFolder.exists()) {
            tempFolder.mkdirs();
        }
    }
}
