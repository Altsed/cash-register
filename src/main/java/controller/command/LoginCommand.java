package controller.command;

import entity.Product;
import entity.Receipt;
import entity.Role;
import service.CashRegisterService;
import utils.HttpUtils;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Enumeration;
import java.util.List;

public class LoginCommand extends FrontCommand{
    @Override
    public void process(CashRegisterService cashRegisterService) throws ServletException, IOException {
        if (request.getParameter("login").isEmpty() || request.getParameter("password").isEmpty()) {
            forward("login-error");
            return;
        }
        request.setAttribute("login", request.getParameter("login"));
        String roleName = cashRegisterService.validateUser(request.getParameter("login") , request.getParameter("password"));
        if ("login-error".equals(roleName)) {
            forward("login-error");
            return;
        }
        String commandName = roleName.substring(0, 1).toUpperCase() + roleName.substring(1);
        HttpUtils.setRoleToSession(request, roleName);
        HttpUtils.storeRoleInCookie(response, roleName);
        request.getSession().setAttribute("command", commandName + "Welcome");
        response.sendRedirect(roleName + "/welcome-page");


        //forward(roleName + "/welcome-page");

   }
}
