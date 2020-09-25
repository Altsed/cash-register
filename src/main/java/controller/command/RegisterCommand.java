package controller.command;

import entity.Role;
import entity.User;
import service.CashRegisterService;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class RegisterCommand extends FrontCommand {
    @Override
    public void process(CashRegisterService cashRegisterService) throws ServletException, IOException {
        String action = request.getParameter("registration");
        if (action == null) {
            List<Role> roles = cashRegisterService.getRoles();
            request.setAttribute("roles", roles);
            forward("register_form");

        }
            int roleId = Integer.parseInt(request.getParameter("role"));
            User user = new User(request.getParameter("login"), request.getParameter("password"), roleId);

            if (!user.validate()) {
                request.setAttribute("message", user.getMessage());
                request.setAttribute("login", user.getLogin());
                request.setAttribute("password", user.getPassword());
                System.out.println("is validate" + user.validate());

                forward("register_error_form");


            }
        }





}
