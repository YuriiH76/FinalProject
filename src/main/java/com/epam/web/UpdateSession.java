package com.epam.web;

import com.epam.web.db.UserDao;
import com.epam.web.entity.User;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebFilter("/*")
public class UpdateSession implements Filter {
    public void destroy() {
    }

    public void init(FilterConfig config) throws ServletException {
        System.out.println("Filter #init()");
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        System.out.println("Filter --->>> before doFilter()");
        // handle data
        HttpServletRequest req = (HttpServletRequest) request;
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        if (user != null) {
            user = new UserDao().findUserByLogin(user.getLogin());
            session.setAttribute("user", user);
            session.setAttribute("isAdmin", user.getAdmin());
            session.setAttribute("language", user.getLanguage().toLowerCase());
        }
       chain.doFilter(request, response);
        //handle data before show client
        System.out.println("Filter <<<--- after doFilter()");
    }
}