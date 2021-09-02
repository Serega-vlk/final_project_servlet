package com.servlet;

import com.dao.ServiceDAO;
import com.dao.User_ServiceDAO;
import com.dataBase.DBConnection;
import com.exeptions.ServiceNotFoundException;
import com.model.ServicesService;
import com.model.User_ServiceService;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "ServiceRemoveServlet", value = "/admin/remove")
public class ServiceRemoveServlet extends HttpServlet {
    private ServicesService servicesService;
    private User_ServiceService user_serviceService;

    public ServiceRemoveServlet() throws SQLException {
        this.servicesService = new ServicesService(new ServiceDAO(DBConnection.getDBConnection().getConnection()));
        this.user_serviceService = new User_ServiceService(new User_ServiceDAO(DBConnection.getDBConnection().getConnection()));
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("delete_service_id"));
        try {
            servicesService.deleteById(id, user_serviceService);
        } catch (SQLException | ServiceNotFoundException throwables) {
            throwables.printStackTrace();
        }
        response.sendRedirect("/admin");
    }
}
