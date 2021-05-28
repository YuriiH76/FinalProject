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
import java.util.Locale;

//import org.apache.log4j.Logger;

public class UserListCommand extends Command {
    private static final long serialVersionUID = 4362576852933139542L;
//    private static final Logger log = Logger.getLogger(LoginCommand.class);

    @Override
    public String execute(HttpServletRequest request,
                          HttpServletResponse response) throws IOException, ServletException {

//        log.debug("Command starts");

        HttpSession session = request.getSession();

        List<User> users = new ArrayList<>();

        if (session.getAttribute("isAdmin") != null && (boolean) session.getAttribute("isAdmin")) {
            users = new UserDao().findAllUsers();
//        log.trace("Found users in DB");
        }

        String forward = "userlist.jsp";

        request.setAttribute("userList", users);
//        log.trace("userList --> " + users);

//        log.debug("Command finished");
        return forward;
    }
}