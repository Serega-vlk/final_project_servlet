package com.servlet;

import com.dao.ServiceDAO;
import com.dao.UserDAO;
import com.dataBase.DBConnection;
import com.dto.Role;
import com.dto.User;
import com.exeptions.ServiceNotFoundException;
import com.exeptions.UserNotFoundException;
import com.model.ServicesService;
import com.model.UserService;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "BalanceServlet", value = "/user/balance")
public class BalanceServlet extends HttpServlet {
    private UserService userService;
    private ServicesService servicesService;

    public BalanceServlet() throws SQLException {
        this.userService = new UserService(new UserDAO(DBConnection.getDBConnection().getConnection()));
        this.servicesService = new ServicesService(new ServiceDAO(DBConnection.getDBConnection().getConnection()));
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/balance.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");
        int money = Integer.parseInt(request.getParameter("moneyToAdd"));
        if (money < 20){
            response.sendRedirect("/user/balance");
            return;
        }
        try {
            user = userService.addMoneyByUsername(user.getLogin(), money).orElse(null);
            request.getSession().setAttribute("user", user);
            if (user.getRole() == Role.BLOCKED && userService.checkEnoughMoney(user, servicesService.getCheapestService())){
                request.getSession().setAttribute("user", userService.unblockUserById(user.getId()).orElse(null));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (UserNotFoundException e) {
            e.printStackTrace();
        } catch (ServiceNotFoundException e) {
            e.printStackTrace();
        }
        response.sendRedirect("/user");
    }
}
