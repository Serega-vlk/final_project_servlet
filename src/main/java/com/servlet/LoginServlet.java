package com.servlet;

import com.dao.UserDAO;
import com.dataBase.DBConnection;
import com.dto.ValidationResults.LoginErrors;
import com.exeptions.UserNotFoundException;
import com.model.UserService;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "LoginServlet", value = "/login")
public class LoginServlet extends HttpServlet {
    private UserService userService;

    public LoginServlet() throws SQLException {
        userService = new UserService(new UserDAO(DBConnection.getDBConnection().getConnection()));
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        session.setAttribute("logErrors", null);
        String login = request.getParameter("username");
        String password = request.getParameter("password");
        try {
            LoginErrors errors = LoginErrors.builder()
                    .setLogin(!userService.hasUsername(login));
            if (errors.hasErrors()){
                session.setAttribute("logErrors", errors);
                response.sendRedirect("/login");
                return;
            }
            errors.setPassword(!userService.isCorrectPassword(login, password));
            if (errors.hasErrors()){
                session.setAttribute("logErrors", errors);
                response.sendRedirect("/login");
                return;
            }
            session.setAttribute("user", userService.getUserByUsername(login));
            session.setAttribute("logErrors", null);
            response.sendRedirect("/user");
        } catch (UserNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
}
