package dao;

import entity.Product;
import java.util.List;

public interface CustomerDAO {
    void getProduct();
    void createProduct();
    List<Product> getProducts();

    String validateUser(String login, String password);
}
