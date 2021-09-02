package com.servlet;

import com.dao.UserDAO;
import com.dataBase.DBConnection;
import com.dto.ValidationResults.ChangePasswordErrors;
import com.dto.ValidationResults.IErrors;
import com.dto.User;
import com.exeptions.UserNotFoundException;
import com.model.UserService;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "ChangeServlet", value = "/user/change")
public class ChangeServlet extends HttpServlet {
    private UserService userService;

    public ChangeServlet() throws SQLException {
        this.userService = new UserService(new UserDAO(DBConnection.getDBConnection().getConnection()));
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/changePassword.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getSession().setAttribute("changeErrors", null);
        User user = (User) request.getSession().getAttribute("user");
        String oldPass = request.getParameter("oldPass");
        String newPass = request.getParameter("newPass");
        IErrors errors = ChangePasswordErrors.builder()
                .setOldPassEmpty(oldPass.isEmpty())
                .setOldPassIncorrect(!user.getPassword().equals(oldPass))
                .setNewPassEmpty(newPass.isEmpty())
                .setNewPassInvalid(!newPass.matches("\\w{5,}"));
        if (errors.hasErrors()){
            request.getSession().setAttribute("changeErrors", errors);
            response.sendRedirect("/user/change");
        } else {
            try {
                request.getSession().setAttribute("user", userService.updatePassword(user.getLogin(), newPass));
            } catch (UserNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            response.sendRedirect("/user");
        }
    }
}
