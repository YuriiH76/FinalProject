package com.epam.web.command;

import java.io.IOException;

import com.epam.web.Path;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


/**
 * No command.
 */
public class NoCommand extends Command {

    private static final long serialVersionUID = 750476158324670472L;

//    private static final Logger log = Logger.getLogger(NoCommand.class.getName());

    @Override
    public String execute(HttpServletRequest request,
                          HttpServletResponse response) throws IOException, ServletException {
//        log.debug("Command starts");

        String errorMessage = "No such command";
        request.setAttribute("errorMessage", errorMessage);
//        log.error("Set the request attribute: errorMessage --> " + errorMessage);

//        log.debug("Command finished");
        return Path.PAGE__ERROR_PAGE;
    }

}