package controller.command.chief;

import controller.command.FrontCommand;
import entity.Receipt;
import service.CashRegisterService;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.List;

public class DeleteFromReceiptCommand extends FrontCommand {


    @Override
    public void process(CashRegisterService cashRegisterService) throws ServletException, IOException {
        int receipt_id = Integer.parseInt(request.getParameter("receipt_id"));
        int product_id = Integer.parseInt(request.getParameter("product_id"));
        cashRegisterService.deleteProductFromReceipt(product_id, receipt_id);
        Receipt receipt = cashRegisterService.getReceipt(receipt_id);
        if (receipt == null) {
            request.setAttribute("message", "Receipt Nr: " + receipt_id + "is canceled.");
            List<Receipt> receipts = cashRegisterService.getReceipts();
            request.setAttribute("receipts", receipts);
            forward( "chief/welcome-page");
        }
        request.setAttribute("message", "Receipt Nr: " + receipt_id);
        request.setAttribute("receipt", receipt.getProductList());
        request.setAttribute("receipt_id", receipt_id);
        forward( "chief/update-receipt");
    }
}