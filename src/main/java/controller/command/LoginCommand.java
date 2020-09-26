package controller.command;

import entity.Product;
import entity.Receipt;
import entity.Role;
import service.CashRegisterService;
import utils.HttpUtils;

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
        HttpUtils.setRoleToSession(request, roleName);
        HttpUtils.storeRoleInCookie(response, roleName);

        forward(roleName + "/welcome-page");

   }
}
