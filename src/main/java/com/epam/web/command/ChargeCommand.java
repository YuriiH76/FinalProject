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

public class ChargeCommand extends Command {
    private static final long serialVersionUID = 3651287015315736628L;
//    private static final Logger log = Logger.getLogger(LoginCommand.class);

    @Override
    public String execute(HttpServletRequest request,
                          HttpServletResponse response) throws IOException, ServletException {

//        log.debug("Command starts");



        String forward = "/ip/controller?command=userlist";

//        log.debug("Command finished");
        return forward;
    }
}
