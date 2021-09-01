package com.servlet;

import com.dao.ServiceDAO;
import com.dao.UserDAO;
import com.dataBase.DBConnection;
import com.dto.Role;
import com.dto.User;
import com.model.ServicesService;
import com.model.UserService;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

@WebServlet(name = "UserServlet", value = "/user")
public class UserServlet extends HttpServlet {
    private UserService userService;
    private ServicesService servicesService;

    public UserServlet() throws SQLException {
        this.userService = new UserService(new UserDAO(DBConnection.getDBConnection().getConnection()));
        this.servicesService = new ServicesService(new ServiceDAO(DBConnection.getDBConnection().getConnection()));
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");
        if (user.getRole() == Role.ADMIN){
            response.sendRedirect("/admin");
        } else if (user.getRole() == Role.BLOCKED){
            response.sendRedirect("/blocked");
        } else {
            try {
                request.setAttribute("connectedServices", servicesService.getUserServices(user));
                request.setAttribute("availableServices", servicesService.getServicesWithOutUser(
                        servicesService.getAllSorted(Optional.ofNullable(request.getParameter("sort")).orElse("id")),
                        user)
                );
                request.getRequestDispatcher("/user.jsp").forward(request, response);
            } catch (SQLException e){
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
