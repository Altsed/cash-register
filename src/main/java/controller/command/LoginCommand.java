package controller.command;

import service.CashRegisterService;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.Enumeration;

public class LoginCommand extends FrontCommand{
    @Override
    public void process(CashRegisterService cashRegisterService) throws ServletException, IOException {
        if (request.getParameter("login").isEmpty() || request.getParameter("password").isEmpty()) {
            forward("login-error");
            return;
        }
        String loginStatus = cashRegisterService.validateUser(request.getParameter("login") , request.getParameter("password"));
        if ("login-error".equals(loginStatus)) {
            forward("login-error");
            return;
        }

        forward(loginStatus + "/welcome-page");

   }
}
