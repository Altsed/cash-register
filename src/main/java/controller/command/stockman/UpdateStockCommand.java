package controller.command.stockman;

import controller.command.FrontCommand;
import entity.Product;
import service.CashRegisterService;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.List;

public class UpdateStockCommand extends FrontCommand {
    @Override
    public void process(CashRegisterService cashRegisterService) throws ServletException, IOException {
        List<Product> products = cashRegisterService.getProducts();
        products.sort(Comparator.comparing(Product::getName));

        int productId = Integer.parseInt(request.getParameter("productId"));
        double stock = Double.parseDouble(request.getParameter("stock"));
        String nameOfProduct = request.getParameter("nameOfProduct");
        boolean isWeight = Boolean.getBoolean(request.getParameter("isWeight"));

        if (!isWeight && stock % 1 != 0) {
            request.setAttribute("products", products);
            request.setAttribute("message", "It not weight product, quantity must be whole number");
            request.setAttribute("command", "StockmanWelcome");

            forward( "stockman/welcome-page");
            return;
        }

        Product product = new Product(productId, nameOfProduct, isWeight, stock);

        cashRegisterService.updateStock(product);
        List<Product> newProductList = cashRegisterService.getProducts();
        newProductList.sort(Comparator.comparing(Product::getName));
        request.setAttribute("message", "Quantity was updated!");
        request.setAttribute("products", newProductList);
        forward( "stockman/welcome-page");

    }
}
