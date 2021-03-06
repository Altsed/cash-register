package dao;

import entity.Product;
import entity.Receipt;
import entity.Role;
import entity.User;
import java.util.List;
import java.util.Map;

public interface CustomerDAO {
    Product getProduct(String reference, String name);

    void createProduct(Product product);

    List<Product> getProducts();

    User validateUser(String login);

    List<Role> getRoles();

    String registerUser(User user);

    String getRoleForUser(User user);

    void updateStock(Product product);

    List<Receipt> getReceipts();

    boolean isProductExists(String reference, String name);

    int getLoginId(String login);

    User getUserById(int user_id);

    void deleteProductFromReceipt(int product_id, int receipt_id);

    int addProductToReceipt(int receiptId, Product product, int user_id, double quantity);

    Receipt getReceipt(int receiptId);

    double getStockForProduct(Product product);

    void updateQuantity(int receipt_id, int product_id, double quantity);

    void closeReceipt(int receipt_id);

    void deleteReceipt(int receipt_id);

    List<Product> generateTop10Report();

    Map<User, Integer> generateBestOperatorsReport();

    List<Product> getProducts(int currentPage, int recordsPerPage);

    int getNumberOfRows();
}
