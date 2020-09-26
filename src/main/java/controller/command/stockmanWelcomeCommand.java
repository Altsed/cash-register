package controller.command;

import entity.Product;
import service.CashRegisterService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class stockmanWelcomeCommand extends FrontCommand{
    @Override
    public void process(CashRegisterService cashRegisterService) throws ServletException, IOException {
        List<Product> products = cashRegisterService.getProducts();
        HttpSession session = request.getSession();
        cashRegisterService.createProduct();
         session.setAttribute("products", products);
        session.setAttribute("role", "stockman");
        forward( "stockman/welcome-page");

    }
}
