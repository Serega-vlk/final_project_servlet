package com.servlet;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

@WebServlet(name = "DownloadServlet", value = "/user/download")
public class DownloadServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/plain");
        response.setHeader("Content-Disposition",
                "attachment; filename=tarifs.txt");
        ServletContext ctx = getServletContext();
        InputStream is = ((ServletContext) ctx).getResourceAsStream("/tarifs.txt");

        int read = 0;
        byte[] bytes = new byte[1024];
        OutputStream os = response.getOutputStream();

        while ((read = is.read(bytes)) != -1) {
            os.write(bytes, 0, read);
        }
        os.flush();
        os.close();
    }
}
