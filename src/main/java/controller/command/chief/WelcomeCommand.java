package controller.command.chief;

import controller.command.FrontCommand;
import entity.Receipt;
import service.CashRegisterService;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class WelcomeCommand extends FrontCommand {
    @Override
    public void process(CashRegisterService cashRegisterService) throws ServletException, IOException {
        List<Receipt> receipts = cashRegisterService.getReceipts();
        request.setAttribute("message", "Receipt list: ");
        request.setAttribute("receipts", receipts);

        forward( "chief/welcome-page");
    }
}
