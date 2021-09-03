package com.servlet;

import com.dao.UserDAO;
import com.dataBase.DBConnection;
import com.dto.ValidationResults.IErrors;
import com.dto.ValidationResults.RegistrationErrors;
import com.dto.User;
import com.exeptions.UserNotFoundException;
import com.model.UserService;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "RegisterServlet", value = "/admin/register")
public class RegisterServlet extends HttpServlet {
    private UserService userService;

    public RegisterServlet() throws SQLException {
        this.userService = new UserService(new UserDAO(DBConnection.getDBConnection().getConnection()));
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/reg.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getSession().setAttribute("regErrors", null);
        User user = User.builder()
                .setName(request.getParameter("name"))
                .setEmail(request.getParameter("email"))
                .setPassword(request.getParameter("password"))
                .setLogin(request.getParameter("login"));
        String passwordRepeat = request.getParameter("repeatPass");
        try {
            IErrors errors = RegistrationErrors.builder()
                    .setNameEmpty(user.getName().isEmpty())
                    .setNameInvalid(!user.getName().matches("^[A-ZА-Я][a-zа-я]+"))
                    .setEmailEmpty(user.getEmail().isEmpty())
                    .setEmailInvalid(!user.getEmail().matches("[\\w\\.]{1,50}@[a-zA-Z0-9]{1,20}\\.[a-zA-Z0-9]{1,10}"))
                    .setEmailExist(userService.hasEmail(user.getEmail()))
                    .setLoginEmpty(user.getLogin().isEmpty())
                    .setLoginExist(userService.hasUsername(user.getLogin()))
                    .setPasswordInvalid(!user.getPassword().matches("\\w{5,}"))
                    .setRepPassword(!user.getPassword().equals(passwordRepeat));
            if (errors.hasErrors()){
                request.getSession().setAttribute("regErrors", errors);
                response.sendRedirect("/admin/register");
            } else {
                userService.saveUser(user);
                response.sendRedirect("/admin");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (UserNotFoundException e) {
            e.printStackTrace();
        }
    }
}
