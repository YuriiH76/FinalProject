package com.epam.web.command;

import com.epam.web.db.UserDao;
import com.epam.web.entity.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.Locale;

public class LoginCommand extends Command {
    private static final long serialVersionUID = 358809752847055449L;
//    private static final Logger log = Logger.getLogger(LoginCommand.class);

    @Override
    public String execute(HttpServletRequest request,
                          HttpServletResponse response) throws IOException, ServletException {

//        log.debug("Command starts");

        HttpSession session = request.getSession();

        String login = request.getParameter("login");
//        log.trace("Request parameter: logging --> " + login);


        String password = request.getParameter("password");
        String encoded = new BCryptPasswordEncoder().encode(password);
        System.out.println(encoded);


        String errorMessage = null;
//        String forward = Path.PAGE__ERROR_PAGE;
//        String forward = request.getRequestURI();
        String forward = "login.jsp";

        if (login == null || password == null || login.isEmpty() || password.isEmpty()) {
            request.setAttribute("login", login);
            errorMessage = "Login/password cannot be empty";
            request.setAttribute("errorMessage", errorMessage);
//            log.error("errorMessage --> " + errorMessage);
            return forward;
        }

        User user = new UserDao().findUserByLogin(login);
//        log.trace("Found in DB: user --> " + user);

        if (user == null || !password.equals(user.getPassword())) {
            request.setAttribute("login", login);
            errorMessage = "Cannot find user with such login/password";
            request.setAttribute("errorMessage", errorMessage);
//            log.error("errorMessage --> " + errorMessage);
            return forward;
        } else {
            boolean isAdmin = user.getAdmin();
            forward = "index.jsp";

//            Role userRole = Role.getRole(user);
//            log.trace("userRole --> " + userRole);
//
//            if (userRole == Role.ADMIN)
//                forward = Path.COMMAND__LIST_ORDERS;
//
//            if (userRole == Role.CLIENT)
//                forward = Path.COMMAND__LIST_MENU;

            session.setAttribute("user", user);
//            log.trace("Set the session attribute: user --> " + user);

            session.setAttribute("isAdmin", isAdmin);
//            log.trace("Set the session attribute: isAdmin --> " + isAdmin);
//
//            log.info("User " + user + " logged as admin - " + isAdmin);

            // work with i18n
            String userLanguage = user.getLanguage();
            if (userLanguage == null && userLanguage.isEmpty()) {
                userLanguage = Locale.getDefault().getLanguage();
            }
            session.setAttribute("language", userLanguage.toLowerCase());
//            log.trace("Set the session attribute: userLanguage --> " + userLanguage);
//            log.info("Language for user: userLanguage --> " + userLanguage);
        }

//        log.debug("Command finished");
        return forward;
    }

}