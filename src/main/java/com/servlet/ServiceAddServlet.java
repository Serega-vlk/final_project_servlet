package com.servlet;

import com.dao.ServiceDAO;
import com.dataBase.DBConnection;
import com.dto.Service;
import com.dto.ValidationResults.IErrors;
import com.dto.ValidationResults.ServiceAddErrors;
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
        request.getSession().setAttribute("addErrors", null);
        String name = request.getParameter("name");
        int price = Integer.parseInt(request.getParameter("price"));
        try {
            IErrors errors = ServiceAddErrors.builder()
                    .setNameEmpty(name.isEmpty())
                    .setServiceNameExist(servicesService.hasServiceName(name))
                    .setPriceLessThanZero(servicesService.priceLessThanZero(price));
            if (errors.hasErrors()){
                request.getSession().setAttribute("addErrors", errors);
                response.sendRedirect("/admin/add");
            } else {
                servicesService.saveService(new Service(name, price));
                response.sendRedirect("/admin");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
