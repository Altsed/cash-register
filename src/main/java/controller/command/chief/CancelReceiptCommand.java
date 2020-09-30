package controller.command.chief;

import controller.command.FrontCommand;
import entity.Receipt;
import service.CashRegisterService;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.List;

public class CancelReceiptCommand extends FrontCommand {

    @Override
    public void process(CashRegisterService cashRegisterService) throws ServletException, IOException {
        int receipt_id = Integer.parseInt(request.getParameter("receipt_id"));
        cashRegisterService.deleteReceipt(receipt_id);
        request.setAttribute("message", "Receipt Nr: " + receipt_id + " canceled.");
        List<Receipt> receipts = cashRegisterService.getReceipts();
        request.setAttribute("receipts", receipts);
        forward("chief/welcome-page");


    }
}