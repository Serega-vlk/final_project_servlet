package com.servlet;

import com.dao.ServiceDAO;
import com.dao.UserDAO;
import com.dataBase.DBConnection;
import com.model.ServicesService;
import com.model.UserService;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

@WebServlet(name = "AdminServlet", value = "/admin")
public class AdminServlet extends HttpServlet {
    private UserService userService;
    private ServicesService servicesService;

    public AdminServlet() throws SQLException {
        userService = new UserService(new UserDAO(DBConnection.getDBConnection().getConnection()));
        servicesService = new ServicesService(new ServiceDAO(DBConnection.getDBConnection().getConnection()));
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String sort = Optional.ofNullable(request.getParameter("sort")).orElse("id");
        try {
            request.setAttribute("allUsers", userService.getAllUsers());
            request.setAttribute("services", servicesService.getAllSorted(sort));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        request.getRequestDispatcher("/admin.jsp").forward(request, response);
    }
}
