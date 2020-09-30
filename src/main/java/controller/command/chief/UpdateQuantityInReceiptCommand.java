package controller.command.chief;

import controller.command.FrontCommand;
import entity.Product;
import entity.Receipt;
import service.CashRegisterService;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class UpdateQuantityInReceiptCommand extends FrontCommand {

    private static final  String forwardPage = "chief/update-receipt";
    private Map<String, String> parameters;

   private void initializeParameters(){
        parameters = new HashMap<>();
        parameters.put("quantity_to_update", request.getParameter("quantity_to_update"));
        parameters.put("product_id", request.getParameter("product_id"));
        parameters.put("receipt_id", request.getParameter("receipt_id"));
        parameters.put("reference", request.getParameter("reference"));



    }
    private boolean isQuantityCorrect(CashRegisterService cashRegisterService){
        double quantityFromRequest = Double.parseDouble(parameters.get("quantity_to_update"));
        Product product = cashRegisterService.getProduct(parameters.get("reference"), parameters.get("name"));
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
        if (!isQuantityCorrect(cashRegisterService)){
            reSendAttributes(cashRegisterService, receipt_id);
            forward(forwardPage);
            return;
        }
        int product_id = Integer.parseInt(parameters.get("product_id"));
        double quantity = Double.parseDouble(parameters.get("quantity_to_update"));
        cashRegisterService.updateQuantity(receipt_id, product_id, quantity);
        parameters.put("quantity_to_update", "");
        parameters.put("message", "Quantity was updated !");
        reSendAttributes(cashRegisterService, receipt_id);
        forward( forwardPage);

    }

    private void reSendAttributes(CashRegisterService cashRegisterService, int receipt_id){
        Receipt receipt = cashRegisterService.getReceipt(receipt_id);
        request.setAttribute("receipt", receipt.getProductList());
        request.setAttribute("receipt_id", receipt_id);
        request.setAttribute("quantity_to_update", parameters.get("quantity_to_update"));
        request.setAttribute("message", parameters.get("message"));

    }



}
