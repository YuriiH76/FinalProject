package com.epam.web;

import com.epam.web.command.Command;
import com.epam.web.command.CommandContainer;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
//import org.apache.log4j.Logger;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.io.IOException;
import java.security.KeyStore;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Locale;
import java.util.UUID;

public class Controller extends HttpServlet {
    private static final long serialVersionUID = 1L;

//    private static final Logger log = Logger.getLogger(Controller.class);


    @Override
    public void init() throws ServletException {
        System.out.println("init() # .Context: "
                + Collections.list(getServletContext().getInitParameterNames())
                + ". Local: " + Collections.list(getInitParameterNames()));
//        System.out.println(getServletContext().getInitParameter("M"));
        System.out.println(getInitParameter("language"));
        super.init();
    }

    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {
        System.out.println("Get");
        process(request, response, "GET");
    }

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {
        System.out.println("Post");

        process(request, response, "POST");
    }

    private void process(HttpServletRequest request,
                         HttpServletResponse response, String method) throws IOException, ServletException {

//        log.debug("Controller starts");

        // extract command name from the request
        String commandName = request.getParameter("command");
//        log.trace("Request parameter: command --> " + commandName);

        // obtain command object by its name
        Command command = CommandContainer.get(commandName);
//        log.trace("Obtained command --> " + command);

        // execute command and get forward address
        String forward = command.execute(request, response);
//        log.trace("Forward address --> " + forward);

//        log.debug("Controller finished, now go to forward address --> " + forward);

        // if the forward address is not null go to the address
        if (forward != null) {
            if (method.equals("GET") || request.getAttribute("errorMessage") != null) {
                System.out.println("request forward");
                RequestDispatcher disp = request.getRequestDispatcher(forward);
                disp.forward(request, response);
            } else {
                System.out.println("send redirect");
                response.sendRedirect(forward);
            }
        }
    }
}














//
//
//
////        Enumeration<String> attributes = request.getSession().getAttributeNames();
////        while (attributes.hasMoreElements()) {
////            String attribute = (String) attributes.nextElement();
////            System.out.println(attribute+" : "+request.getSession().getAttribute(attribute));
////        }
//
//        HttpSession session = request.getSession();
////        if (session.isNew()) {
//            session.setAttribute("userLogin", null);
//            session.setAttribute("isInvalid", null);
//            Locale locale = Locale.getDefault();
//            session.setAttribute("language", "en");
//            System.out.println(locale.getLanguage());
//            session.getAttributeNames();
////        }
//
//
//
//        session.setMaxInactiveInterval(30);
//
//        if (method == "POST") {
//            System.out.println(request.getParameter("command"));
//            if ("login".equals(request.getParameter("command"))) {
//                //check in DB
//                session.setAttribute("userLogin", request.getParameter("login"));
//                session.setAttribute("language", "uk");
//                System.out.println(session.getAttribute("userLogin"));
//
//                System.out.println(UUID.randomUUID().toString());
//                String encoded = new BCryptPasswordEncoder().encode("123");
//                System.out.println(encoded);
//
//                if ("1".equals(request.getParameter("password"))) {
//                    session.setAttribute("isInvalid", " is-invalid");
//                    request.getRequestDispatcher("/login.jsp").forward(request, response);
//                } else {
////                getServletContext().getRequestDispatcher("/index.jsp").forward(request, response);
//                    response.sendRedirect("index.jsp");
//                }
//            }
//        }
//    }
//}
