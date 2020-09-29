package controller.command.operator;

import controller.command.FrontCommand;
import entity.Product;
import entity.Receipt;
import service.CashRegisterService;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CloseReceiptCommand extends FrontCommand {

    @Override
    public void process(CashRegisterService cashRegisterService) throws ServletException, IOException {
        if (request.getParameter("receipt_id").isEmpty() || request.getParameter("receipt_id")==null){
            Receipt receipt = new Receipt();
            request.setAttribute("message", "Create new Receipt: ");
            request.setAttribute("receipt", receipt.getProductList());
            forward("operator/welcome-page");
            return;
        }

        int receipt_id = Integer.parseInt(request.getParameter("receipt_id"));
        cashRegisterService.closeReceipt(receipt_id);
        Receipt receipt = new Receipt();
        request.setAttribute("message", "Receipt Nr: " + receipt_id + " closed!  Please create new receipt");
        request.setAttribute("receipt", receipt.getProductList());
        forward("operator/welcome-page");

    }

}
