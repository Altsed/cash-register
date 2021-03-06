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

        String roleName = cashRegisterService.validateUser(request.getParameter("login") , request.getParameter("password"));
        if ("login-error".equals(roleName)) {
            forward("login-error");
            return;
        }
        int loginId = cashRegisterService.getLoginId(request.getParameter("login"));

        HttpUtils.storeLanguageInCookie(response, request.getParameter("language"));
        HttpUtils.storeRoleInCookie(response, roleName);
        HttpUtils.storeLoginIdCookie(response, loginId);
        request.getSession().setAttribute("command", roleName + ".Welcome");
        response.sendRedirect(roleName + "/welcome-page");


   }
}
