package com.servlet;

import com.dao.ServiceDAO;
import com.dao.User_ServiceDAO;
import com.dataBase.DBConnection;
import com.dto.User;
import com.exeptions.ServiceNotFoundException;
import com.exeptions.UserNotFoundException;
import com.model.ServicesService;
import com.model.User_ServiceService;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "DeleteServiceServlet", value = "/user/deleteService")
public class DeleteServiceServlet extends HttpServlet {
    private ServicesService servicesService;
    private User_ServiceService user_serviceService;

    public DeleteServiceServlet() throws SQLException {
        servicesService = new ServicesService(new ServiceDAO(DBConnection.getDBConnection().getConnection()));
        user_serviceService = new User_ServiceService(new User_ServiceDAO(DBConnection.getDBConnection().getConnection()));
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");
        int service_id = Integer.parseInt(request.getParameter("delete_service_id"));
        try {
            user_serviceService.deleteServiceFromUser(servicesService.getById(service_id), user);
        } catch (SQLException | ServiceNotFoundException e){
            System.out.println(e);
        }
        response.sendRedirect("/user");
    }
}
