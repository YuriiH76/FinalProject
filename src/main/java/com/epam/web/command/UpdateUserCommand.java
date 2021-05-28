package com.epam.web.command;

import com.epam.web.db.UserDao;
import com.epam.web.entity.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

//import org.apache.log4j.Logger;

public class UpdateUserCommand extends Command {
    private static final long serialVersionUID = 4599404511784649366L;
//    private static final Logger log = Logger.getLogger(LoginCommand.class);

    @Override
    public String execute(HttpServletRequest request,
                          HttpServletResponse response) throws IOException, ServletException {

//        log.debug("Command starts");

        HttpSession session = request.getSession();

        User user = new User();
        user.setLogin(request.getParameter("login"));
        user.setName(request.getParameter("name"));
        user.setEmail(request.getParameter("email"));
        user.setActive(request.getParameterValues("active") != null);
        user.setAdmin(request.getParameterValues("admin") != null);
        user.setLanguage(request.getParameterValues("language")[0]);

        if (session.getAttribute("isAdmin") != null && (boolean) session.getAttribute("isAdmin")) {
            new UserDao().updateUser(user, "data");
//        log.trace("Found in DB: user --> " + user);
        }

        String forward = "/ip/controller?command=userlist";

//        log.debug("Command finished");
        return forward;
    }
}
