package controller.command;

import service.CashRegisterService;
import utils.HttpUtils;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import java.io.IOException;

public class LogoutCommand extends FrontCommand {
    @Override
    public void process(CashRegisterService cashRegisterService) throws ServletException, IOException {
        HttpUtils.deleteCookie(request, response);
        response.sendRedirect("/");
    }
}

