package controller;

import controller.command.FrontCommand;
import controller.filter.RoleFilter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.CashRegisterService;
import service.CashRegisterServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Constructor;

@WebServlet("/")
public class FrontControllerServlet extends HttpServlet {

    CashRegisterService cashRegisterService = new CashRegisterServiceImpl();
    private final Logger logger = LogManager.getLogger(FrontControllerServlet.class);
    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf8");
        FrontCommand command = getCommand(request);
        command.init(getServletContext(), request, response);
        command.process(cashRegisterService);
    }
    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf8");
        FrontCommand command = getCommand(request);
        command.init(getServletContext(), request, response);
        command.process(cashRegisterService);
    }

    protected FrontCommand getCommand(HttpServletRequest request) {

        try {
            String parameter;
            if (request.getSession().getAttribute("command") != null) {
                parameter = (String)request.getSession().getAttribute("command");
                request.getSession().removeAttribute("command");
            }
            else {
                parameter = request.getParameter("command");
            }
            String commandNameClass = String.format(
                    "controller.command.%sCommand",
                    parameter);
            Class<?> c = Class.forName(commandNameClass);
            Constructor<?> []cons = c.getConstructors();
            return (FrontCommand) cons[0].newInstance();
        } catch (Exception e) {
            logger.error(e.getCause());
            return null;
        }
    }
}