package controller.command;

import service.CashRegisterService;

import javax.servlet.ServletException;
import java.io.IOException;

public class AddProductCommand extends FrontCommand{
    @Override
    public void process(CashRegisterService cashRegisterService) throws ServletException, IOException {
        cashRegisterService.createProduct();
        forward("result");
    }
}
