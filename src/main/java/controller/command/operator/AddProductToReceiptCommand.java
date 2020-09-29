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
import java.util.*;

public class AddProductToReceiptCommand extends FrontCommand {

    private static final  String forwardPage = "operator/welcome-page";
    private Map<String, String> parameters;

    public AddProductToReceiptCommand(){
        parameters = new HashMap<>();
        parameters.put("reference", new String());
        parameters.put("name", new String());
        parameters.put("quantity", new String());
        parameters.put("message", new String());
        parameters.put("receipt_id", "0");

    }

    private void initializeParameters(){
        for (Map.Entry<String, String> entry : parameters.entrySet()){
             if (!request.getParameter(entry.getKey()).isEmpty() && request.getParameter(entry.getKey()) != null) {
                entry.setValue(request.getParameter(entry.getKey()));
            }
        }
    }

    private boolean isEnterFormEmpty(){
        if (parameters.get("reference").isEmpty() && parameters.get("name").isEmpty()) {
            parameters.put("message", "Please enter reference or name of product!");
            return true;
        }
        return false;
    }
    private boolean isProductExists(CashRegisterService cashRegisterService){
        if (!cashRegisterService.isProductExists(parameters.get("reference"), parameters.get("name"))){
            parameters.put("message", "Product doesn't exist, check reference or name of product!");
            return false;
        }
        return true;
    }
    private boolean isQuantityCorrect(CashRegisterService cashRegisterService){
        double quantityFromRequest = Double.parseDouble(parameters.get("quantity"));
        Product product = cashRegisterService.getProduct(parameters.get("reference"), parameters.get("name"));
        if (!product.isWeight() && quantityFromRequest % 1 != 0){
            parameters.put("message", "It not weight product, quantity must be whole number");
            return false;
        }
        double availableStockForProduct = cashRegisterService.getStockForProduct(product);
        if ((availableStockForProduct - quantityFromRequest) < 0) {
            parameters.put("message", "Not enough quantity on stock. " + "Available quantity: " + availableStockForProduct);
            return false;
        }
        return true;
    }



    @Override
    public void process(CashRegisterService cashRegisterService) throws ServletException, IOException {
        initializeParameters();
        int receipt_id = Integer.parseInt(parameters.get("receipt_id"));

        if (isEnterFormEmpty()) {
            reSendAttributes(cashRegisterService, receipt_id);
            forward(forwardPage);
            return;
        }
        if (!isProductExists(cashRegisterService)){
            reSendAttributes(cashRegisterService, receipt_id);
            forward(forwardPage);
            return;
        }
        if (!isQuantityCorrect(cashRegisterService)){
            reSendAttributes(cashRegisterService, receipt_id);
            forward(forwardPage);
            return;
        }


        int userId = HttpUtils.getUserIdFromCookie(request);
        Product product = cashRegisterService.getProduct(parameters.get("reference"), parameters.get("name"));
        double quantityFromRequest = Double.parseDouble(parameters.get("quantity"));

        receipt_id = cashRegisterService.addProductToReceipt(receipt_id, product, userId, quantityFromRequest);
        parameters.put("message", "Receipt number: " + receipt_id);
        reSendAttributes(cashRegisterService, receipt_id);

        forward(forwardPage);

    }

    private void reSendAttributes(CashRegisterService cashRegisterService, int receipt_id){
        Receipt receipt = cashRegisterService.getReceipt(receipt_id);
        request.setAttribute("receipt", receipt.getProductList());
        request.setAttribute("receipt_id", receipt_id);
        request.setAttribute("reference", parameters.get("reference"));
        request.setAttribute("name", parameters.get("name"));
        request.setAttribute("quantity", parameters.get("quantity"));
        request.setAttribute("message", parameters.get("message"));
    }



}