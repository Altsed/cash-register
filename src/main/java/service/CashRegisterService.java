package service;

import entity.Product;
import entity.Role;

import java.util.List;

public interface CashRegisterService {
    void getProduct();
    void createProduct();
    List<Product> getProducts();
    String validateUser(String login, String password);

    List<Role> getRoles();
}
