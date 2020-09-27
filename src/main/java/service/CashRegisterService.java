package service;

import entity.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface CashRegisterService {
    void getProduct();
    void createProduct(Product product);
    List<Product> getProducts();
    String validateUser(String login, String password);

    List<Role> getRoles();

    String registerUser(User user);

    void loginApproved(HttpServletRequest request, String roleName);


    void updateStock(Product product);

    List<Receipt> getReceipts();
}
