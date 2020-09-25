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

        List<Role> roles = cashRegisterService.getRoles();

        request.setAttribute("roles", roles);
        forward("register_form");
        }
}
