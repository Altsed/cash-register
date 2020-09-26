package service;

import entity.Datable;
import entity.Product;
import entity.Role;
import entity.User;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface CashRegisterService {
    void getProduct();
    void createProduct();
    List<Product> getProducts();
    String validateUser(String login, String password);

    List<Role> getRoles();

    String registerUser(User user);

    void loginApproved(HttpServletRequest request, String roleName);


}
