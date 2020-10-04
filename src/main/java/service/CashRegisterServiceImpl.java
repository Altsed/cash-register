package service;

import dao.CustomerDAO;
import dao.CustomerDAOPostgresqlImpl;
import entity.*;
import utils.BCryptPassword;
import utils.HttpUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public class CashRegisterServiceImpl implements CashRegisterService {

    CustomerDAO customerDAO = new CustomerDAOPostgresqlImpl();

    @Override
    public String validateUser(String login, String password) {
        User user = customerDAO.validateUser(login);
        if (user == null || user.getLogin() == null || !BCryptPassword.checkPass(password, user.getPassword())) {
            return "login-error";
        }
        return customerDAO.getRoleForUser(user);
    }

    @Override
    public int getLoginId(String login) {
        return customerDAO.getLoginId(login);
    }

    @Override
    public void loginApproved(HttpServletRequest request, String roleName) {
        HttpUtils.setRoleToSession(request, roleName);
    }

    @Override
    public String registerUser(User user) {
        user.setPassword(BCryptPassword.hashPassword(user.getPassword()));
        return customerDAO.registerUser(user);
    }

    @Override
    public void createProduct(Product product) {
        customerDAO.createProduct(product);
    }

    @Override
    public List<Product> getProducts() {
        return customerDAO.getProducts();
    }

    @Override
    public void updateStock(Product product) {
        customerDAO.updateStock(product);
    }

    @Override
    public void updateQuantity(int receipt_id, int product_id, double quantity) {
        customerDAO.updateQuantity(receipt_id, product_id, quantity);
    }

    @Override
    public void closeReceipt(int receipt_id) {
        customerDAO.closeReceipt(receipt_id);
    }



    @Override
    public List<Role> getRoles() {
        return customerDAO.getRoles();
    }



    @Override
    public void deleteReceipt(int receipt_id) {
        customerDAO.deleteReceipt(receipt_id);
    }

    @Override
    public List<Receipt> getReceipts() {
        return customerDAO.getReceipts();
    }

    @Override
    public void deleteProductFromReceipt(int product_id, int receipt_id) {
        customerDAO.deleteProductFromReceipt(product_id, receipt_id);
    }

    @Override
    public boolean isProductExists(String reference, String name) {
        return customerDAO.isProductExists(reference, name);
    }

    @Override
    public Product getProduct(String reference, String name) {
        return customerDAO.getProduct(reference, name);
    }

    @Override
    public int addProductToReceipt(int receiptId, Product product, int user_id, double quantity) {
        return customerDAO.addProductToReceipt(receiptId, product, user_id, quantity);
    }
    @Override
    public Receipt getReceipt(int receiptId) {
        return customerDAO.getReceipt(receiptId);
    }
    @Override
    public User getUserByID(int user_id) {
        return customerDAO.getUserById(user_id);
    }

    @Override
    public double getStockForProduct(Product product) {
        return customerDAO.getStockForProduct(product);
    }

    @Override
    public List<Product> generateTop10Report() {
        return customerDAO.generateTop10Report();
    }

    @Override
    public Map<User, Integer> generateBestOperatorsReport() {
        return customerDAO.generateBestOperatorsReport();
    }

    @Override
    public List<Product> getProducts(int currentPage, int recordsPerPage) {
        return customerDAO.getProducts(currentPage, recordsPerPage);
    }

    @Override
    public int getNumberOfRows() {
        return customerDAO.getNumberOfRows();
    }
}
