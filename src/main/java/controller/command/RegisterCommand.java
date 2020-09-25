package controller.command;

import entity.Role;
import entity.User;
import service.CashRegisterService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class RegisterCommand extends FrontCommand {
    @Override
    public void process(CashRegisterService cashRegisterService) throws ServletException, IOException {

        String action = request.getParameter("registration");

        if (action == null) {
            List<Role> roles = cashRegisterService.getRoles();
            HttpSession session = request.getSession();
            session.setAttribute("roles", roles);
            forward("register_form");
        }

        User user = new User(request.getParameter("login"),
                request.getParameter("password"),
                Integer.parseInt(request.getParameter("role")));

       if (!user.validate()) {
            removeAndSetAttributes(user);
            forward("register_error_form");
        } else {
            cashRegisterService.registerUser(user);
            if (!user.isValid()) {
                removeAndSetAttributes(user);
                forward("register_error_form");
            }
            request.setAttribute("message", "User was successfully registered");
            forward("register_form");
        }

    }

    private void removeAndSetAttributes(User user){
        request.removeAttribute("message");
        request.removeAttribute("login");

        request.setAttribute("message", user.getMessage());
        request.setAttribute("login", user.getLogin());
    }
}





