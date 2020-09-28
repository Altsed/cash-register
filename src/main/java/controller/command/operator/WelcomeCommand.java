package controller.command.operator;

import controller.command.FrontCommand;
import entity.Product;
import entity.Receipt;
import service.CashRegisterService;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;

public class WelcomeCommand extends FrontCommand {
    @Override
    public void process(CashRegisterService cashRegisterService) throws ServletException, IOException {
        List<Receipt> receipts = cashRegisterService.getReceipts();
        request.setAttribute("receipts", receipts);
        forward( "operator/welcome-page");

    }
}
