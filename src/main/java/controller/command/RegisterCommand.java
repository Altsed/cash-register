package controller.command;

import entity.Role;
import entity.User;
import service.CashRegisterService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class RegisterCommand extends FrontCommand {
    @Override
    public void process(CashRegisterService cashRegisterService) throws ServletException, IOException {
        String roleView = "/welcome-page";
        String action = request.getParameter("registration");

        if (action == null) {
            List<Role> roles = cashRegisterService.getRoles();
            HttpSession session = request.getSession();
            session.setAttribute("roles", roles);
            forward("register_form");
        }
        int roleId = Integer.parseInt(request.getParameter("role"));
        User user = new User(request.getParameter("login"), request.getParameter("password"), roleId);

       if (!user.validate()) {
            removeAndSetAttributes(user);
            forward("register_error_form");
        } else {
            String statusUser = cashRegisterService.registerUser(user);
            if (!user.isValid()) {
                removeAndSetAttributes(user);
                forward("register_error_form");
            }
            forward(statusUser + roleView);
        }

    }

    private void removeAndSetAttributes(User user){
        request.removeAttribute("message");
        request.removeAttribute("login");

        request.setAttribute("message", user.getMessage());
        request.setAttribute("login", user.getLogin());
    }
}





