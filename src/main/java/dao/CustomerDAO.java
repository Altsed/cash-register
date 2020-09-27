package dao;

import entity.Product;
import entity.Receipt;
import entity.Role;
import entity.User;

import java.util.List;

public interface CustomerDAO {
    void getProduct();
    void createProduct(Product product);

    List<Product> getProducts();

    User validateUser(String login);

    List<Role> getRoles();

    String registerUser(User user);

    String getRoleForUser(User user);

    void updateStock(Product product);

    List<Receipt> getReceipts();
}
