package com.servlet;

import com.dao.UserDAO;
import com.dataBase.DBConnection;
import com.dto.Role;
import com.dto.User;
import com.exeptions.UserNotFoundException;
import com.model.UserService;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "UserUnblockServlet", value = "/admin/unblock")
public class UserUnblockServlet extends HttpServlet {
    private UserService userService;

    public UserUnblockServlet() throws SQLException {
        this.userService = new UserService(new UserDAO(DBConnection.getDBConnection().getConnection()));
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("unblock_id"));
        try {
            User user = userService.getById(id);
            if (user.getRole() != Role.ADMIN) {
                userService.unblockUserById(id);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (UserNotFoundException e) {
            e.printStackTrace();
        }
        response.sendRedirect("/admin");
    }
}
