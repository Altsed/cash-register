package controller;

import controller.command.FrontCommand;
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

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {

        FrontCommand command = getCommand(request);
        command.init(getServletContext(), request, response);
        command.process(cashRegisterService);
    }
    @Override
    protected void doPost(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {

        FrontCommand command = getCommand(request);
        command.init(getServletContext(), request, response);
        command.process(cashRegisterService);
    }

    private FrontCommand getCommand(HttpServletRequest request) {
        System.out.println(request.getRequestURL());

        try {
            System.out.println(request.getParameter("command"));
            String commandNameClass = String.format(
                    "controller.command.%sCommand",
                    request.getParameter("command"));
            Class<?> c = Class.forName(commandNameClass);
            Constructor<?> []cons = c.getConstructors();
            return (FrontCommand) cons[0].newInstance();
        } catch (Exception e) {
            return null;
        }
    }
}
