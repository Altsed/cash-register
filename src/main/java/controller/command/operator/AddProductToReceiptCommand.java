package controller.command.operator;

import controller.command.FrontCommand;
import entity.Product;
import entity.Receipt;
import entity.User;
import service.CashRegisterService;
import utils.HttpUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;

public class AddProductToReceiptCommand extends FrontCommand {


    private boolean isEnterFormEmpty(){
        if (request.getParameter("reference").isEmpty() && request.getParameter("name").isEmpty()) {
            request.setAttribute("quantity", request.getParameter("quantity"));
            request.setAttribute("message", "Please enter reference or name of product!");
            return true;
        }
        return false;
    }
    private boolean isProductExists(CashRegisterService cashRegisterService){
        String reference = request.getParameter("reference");
        String name = request.getParameter("name");
        double quantity = Double.parseDouble(request.getParameter("quantity"));

        if (!cashRegisterService.isProductExists(reference, name)){
            request.setAttribute("message", "Product doesn't exist, check reference or name of product!");
            request.setAttribute("quantity", quantity);
            return false;
        }
        return true;
    }
    private boolean isQuantityCorrect(CashRegisterService cashRegisterService){
        String reference = request.getParameter("reference");
        String name = request.getParameter("name");
        if (reference == null) {
            reference = "";
        }
        if (name == null) {
            name = "";
        }
        double quantity = Double.parseDouble(request.getParameter("quantity"));
        Product product = cashRegisterService.getProduct(reference, name);
        if (!product.isWeight() && quantity % 1 != 0){
            request.setAttribute("message", "It not weight product, quantity must be whole number");
            request.setAttribute("reference", reference);
            request.setAttribute("name", name);
            return false;
        }
        double availableStockForProduct = cashRegisterService.getStockForProduct(product);
        if ((availableStockForProduct - quantity) < 0) {
            request.setAttribute("message", "Not enough quantity on stock. " +
                    "Available quantity: " + availableStockForProduct);
            request.setAttribute("reference", reference);
            request.setAttribute("name", name);
            return false;
        }

        return true;
    }



    @Override
    public void process(CashRegisterService cashRegisterService) throws ServletException, IOException {
        int receiptId;
        if (request.getParameter("receipt_id") == null || request.getParameter("receipt_id").isEmpty() ){
            receiptId = 0;
        }
        else receiptId = Integer.parseInt(request.getParameter("receipt_id"));

        if (isEnterFormEmpty()) {
            reSendAttributes(cashRegisterService, receiptId);
            forward( "operator/welcome-page");
            return;
        }
        if (!isProductExists(cashRegisterService)){
            reSendAttributes(cashRegisterService, receiptId);
            forward( "operator/welcome-page");
            return;
        }
       if (!isQuantityCorrect(cashRegisterService)){
           reSendAttributes(cashRegisterService, receiptId);
            forward( "operator/welcome-page");
            return;
        }


        int userId = HttpUtils.getUserIdFromCookie(request);
        Product product = cashRegisterService.getProduct(request.getParameter("reference"),
                request.getParameter("name"));
        double quantity = Double.parseDouble(request.getParameter("quantity"));

        receiptId = cashRegisterService.addProductToReceipt(receiptId, product, userId, quantity);
        reSendAttributes(cashRegisterService, receiptId);
        request.setAttribute("message", "Receipt number: " + receiptId);
        forward( "operator/welcome-page");

    }

    private void reSendAttributes(CashRegisterService cashRegisterService, int receiptId){
        Receipt receipt = cashRegisterService.getReceipt(receiptId);
        request.setAttribute("receipt", receipt.getProductList());
        request.setAttribute("receipt_id", receiptId);


    }



}
