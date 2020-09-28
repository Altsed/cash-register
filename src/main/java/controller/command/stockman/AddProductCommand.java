package controller.command.stockman;

import controller.command.FrontCommand;
import entity.Product;
import service.CashRegisterService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;

public class AddProductCommand extends FrontCommand {
    @Override
    public void process(CashRegisterService cashRegisterService) throws ServletException, IOException {
        List<Product> products = cashRegisterService.getProducts();
        products.sort(Comparator.comparing(Product::getName));
        String referenceOfProduct = request.getParameter("reference");
        String nameOfProduct = request.getParameter("name");
        boolean isWeight = Boolean.getBoolean(request.getParameter("isWeight"));
        double stock = Double.parseDouble(request.getParameter("stock"));

        Product product = new Product(referenceOfProduct, nameOfProduct, isWeight, stock);
        if (cashRegisterService.isProductExists(referenceOfProduct, nameOfProduct)) {
            request.setAttribute("products", products);
            request.setAttribute("message", "Product is already exists");
            request.setAttribute("nameOfProduct", product.getName());
            request.setAttribute("command", "StockmanWelcome");
            forward( "stockman/welcome-page");
            return;
        }


        cashRegisterService.createProduct(product);
        List<Product> newProductList = cashRegisterService.getProducts();
        newProductList.sort(Comparator.comparing(Product::getName));
        request.setAttribute("products", newProductList);

        forward( "stockman/welcome-page");

    }
}
