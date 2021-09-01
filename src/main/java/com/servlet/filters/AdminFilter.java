package com.servlet.filters;

import com.dto.Role;
import com.dto.User;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(filterName = "AdminFilter", value = "/admin/*")
public class AdminFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpSession session = ((HttpServletRequest) request).getSession();
        User user = (User) session.getAttribute("user");
        if (user.getRole() == Role.ADMIN){
            chain.doFilter(request, response);
        } else {
            request.getRequestDispatcher("accessDenied.jsp").forward(request, response);
        }
    }
}
