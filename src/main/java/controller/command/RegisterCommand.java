package controller.command;

import entity.Role;
import service.CashRegisterService;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RegisterCommand extends FrontCommand{
    @Override
    public void process(CashRegisterService cashRegisterService) throws ServletException, IOException {

        List<Role> roles = new ArrayList<>();
        roles.add(new Role(1, "Admin"));
        roles.add(new Role(2, "User"));

        request.setAttribute("roles", roles);
        forward("register_form");
        }
}
