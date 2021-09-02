package com.servlet;

import com.dao.ServiceDAO;
import com.dao.UserDAO;
import com.dao.User_ServiceDAO;
import com.dataBase.DBConnection;
import com.dto.Service;
import com.dto.User;
import com.exeptions.ServiceNotFoundException;
import com.exeptions.UserNotFoundException;
import com.model.ServicesService;
import com.model.UserService;
import com.model.User_ServiceService;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "AddServiceServlet", value = "/user/addService")
public class AddServiceServlet extends HttpServlet {
    private ServicesService servicesService;
    private UserService userService;
    private User_ServiceService user_serviceService;

    public AddServiceServlet() throws SQLException {
        servicesService = new ServicesService(new ServiceDAO(DBConnection.getDBConnection().getConnection()));
        user_serviceService = new User_ServiceService(new User_ServiceDAO(DBConnection.getDBConnection().getConnection()));
        userService = new UserService(new UserDAO(DBConnection.getDBConnection().getConnection()));
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");
        int service_id = Integer.parseInt(request.getParameter("add_service_id"));
        try {
            Service service = servicesService.getById(service_id).orElseThrow(ServiceNotFoundException::new);
            if (userService.checkEnoughMoney(user, service)) {
                request.getSession().setAttribute("user", userService.payForService(user, service));
                user_serviceService.addServiceToUser(service, user);
            } else if (!userService.checkEnoughMoney(user, servicesService.getCheapestService())) {
                request.getSession().setAttribute("user", userService.blockUserById(user.getId()).orElse(null));
            }
        } catch (SQLException | ServiceNotFoundException | UserNotFoundException e){
            e.printStackTrace();
        }
        response.sendRedirect("/user");
    }
}
