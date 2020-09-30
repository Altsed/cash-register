package controller.command.chief;

import controller.command.FrontCommand;
import entity.Product;
import entity.Receipt;
import service.CashRegisterService;
import utils.HttpUtils;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class UpdateReceiptCommand extends FrontCommand {

    @Override
    public void process(CashRegisterService cashRegisterService) throws ServletException, IOException {
        int receipt_id = Integer.parseInt(request.getParameter("receipt_id"));
        Receipt receipt = cashRegisterService.getReceipt(receipt_id);
        request.setAttribute("message", "Receipt Nr: " + receipt_id);
        request.setAttribute("receipt", receipt.getProductList());
        request.setAttribute("receipt_id", receipt_id);
        forward( "chief/update-receipt");
    }
}