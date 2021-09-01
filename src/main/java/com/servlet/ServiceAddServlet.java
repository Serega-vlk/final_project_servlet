package com.servlet;

import com.dao.ServiceDAO;
import com.dataBase.DBConnection;
import com.dto.Service;
import com.model.ServicesService;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "ServiceAddServlet", value = "/admin/add")
public class ServiceAddServlet extends HttpServlet {
    private final ServicesService servicesService;

    public ServiceAddServlet() throws SQLException {
        this.servicesService = new ServicesService(new ServiceDAO(DBConnection.getDBConnection().getConnection()));
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/add.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String price = request.getParameter("price");
        Service service = new Service(name, Integer.parseInt(price));
        try {
            if (!servicesService.hasService(service)){
                servicesService.saveService(service);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        response.sendRedirect("/admin");
    }
}
