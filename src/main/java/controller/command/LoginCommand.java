package controller.command;

import service.CashRegisterService;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.Enumeration;

public class LoginCommand extends FrontCommand{
    @Override
    public void process(CashRegisterService cashRegisterService) throws ServletException, IOException {

        if (cashRegisterService.validateUser(request.getParameter("login") , request.getParameter("password")).isEmpty()) {
            forward("loginError");
            return;
        }
        response.getWriter().print("Success");
   }
}
