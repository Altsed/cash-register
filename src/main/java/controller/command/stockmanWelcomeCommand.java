package controller.command;

import entity.Product;
import service.CashRegisterService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;

public class stockmanWelcomeCommand extends FrontCommand{
    @Override
    public void process(CashRegisterService cashRegisterService) throws ServletException, IOException {
        List<Product> products = cashRegisterService.getProducts();
        products.sort(Comparator.comparing(Product::getName));
        request.setAttribute("products", products);
        request.setAttribute("role", "stockman");
        forward( "stockman/welcome-page");

    }
}
