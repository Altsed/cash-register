package controller.command.operator;

import controller.command.FrontCommand;
import entity.Product;
import entity.Receipt;
import service.CashRegisterService;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class WelcomeCommand extends FrontCommand {
    @Override
    public void process(CashRegisterService cashRegisterService) throws ServletException, IOException {
        Receipt receipt = new Receipt();
        request.setAttribute("message", "Create new Receipt: ");
        request.setAttribute("receipt", receipt.getProductList());

        forward( "operator/welcome-page");
    }
}
