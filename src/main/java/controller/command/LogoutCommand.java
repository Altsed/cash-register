package controller.command;

import service.CashRegisterService;
import utils.HttpUtils;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import java.io.IOException;

public class LogoutCommand extends FrontCommand {
    @Override
    public void process(CashRegisterService cashRegisterService) throws ServletException, IOException {
//        HttpUtils.deleteCookie(response, "role");
//        HttpUtils.deleteCookie(response, "user_id");

        request.removeAttribute("role");
        request.removeAttribute("user_id");
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies){
            cookie.setValue("");
            cookie.setMaxAge(0);
        }


        response.sendRedirect("/");
    }
}

