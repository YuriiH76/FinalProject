package com.epam.web.command;

import com.epam.web.db.UserDao;
import com.epam.web.entity.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.math.BigDecimal;

//import org.apache.log4j.Logger;

public class UpdateUserAccountCommand extends Command {
    private static final long serialVersionUID = 1978101957173680489L;
//    private static final Logger log = Logger.getLogger(LoginCommand.class);

    @Override
    public String execute(HttpServletRequest request,
                          HttpServletResponse response) throws IOException, ServletException {

//        log.debug("Command starts");

        HttpSession session = request.getSession();

        User user = new User();
        user.setLogin(request.getParameter("login"));
        BigDecimal userAccount = new BigDecimal(request.getParameter("account"));
        user.setAccount(userAccount.add(new BigDecimal(request.getParameter("put"))));

        if (session.getAttribute("isAdmin") != null && (boolean) session.getAttribute("isAdmin")) {
            new UserDao().updateUser(user, "account");
//        log.trace("Found in DB: user --> " + user);
        }

        String forward = "/ip/controller?command=userlist";

//        log.debug("Command finished");
        return forward;
    }
}
