package com.servlet;

import com.dao.ServiceDAO;
import com.dataBase.DBConnection;
import com.model.ServicesService;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "ServiceRemoveServlet", value = "/admin/remove")
public class ServiceRemoveServlet extends HttpServlet {
    private ServicesService service;

    public ServiceRemoveServlet() throws SQLException {
        this.service = new ServicesService(new ServiceDAO(DBConnection.getDBConnection().getConnection()));
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("delete_service_id"));
        try {
            service.deleteById(id);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        response.sendRedirect("/admin");
    }
}
