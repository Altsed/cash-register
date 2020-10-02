package service;

import entity.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public interface CashRegisterService {
    Product getProduct(String reference, String name);
    void createProduct(Product product);
    List<Product> getProducts();
    String validateUser(String login, String password);

    List<Role> getRoles();

    String registerUser(User user);

    void loginApproved(HttpServletRequest request, String roleName);


    void updateStock(Product product);

    List<Receipt> getReceipts();

    boolean isProductExists(String reference, String name);

    int addProductToReceipt(int receiptId, Product product, int user_id, double quantity);

    int getLoginId(String login);

    User getUserByID(int user_id);

    Receipt getReceipt(int receiptId);

    double getStockForProduct(Product product);

    void updateQuantity(int receipt_id, int product_id, double quantity);

    void closeReceipt(int receipt_id);

    void deleteProductFromReceipt(int product_id, int receipt_id);

    void deleteReceipt(int receipt_id);

    List<Product> generateTop10Report();

    Map<User, Integer> generateBestOperatorsReport();
}
