package service;

import dao.CustomerDAO;
import dao.CustomerDAOPostgresqlImpl;
import entity.Product;
import entity.Role;
import entity.User;
import validation.BCryptPassword;

import javax.jws.soap.SOAPBinding;
import java.util.List;

public class CashRegisterServiceImpl implements CashRegisterService {
    CustomerDAO customerDAO = new CustomerDAOPostgresqlImpl();

    @Override
    public void getProduct() {

    }

    @Override
    public void createProduct() {
    }

    @Override
    public List<Product> getProducts() {
        return null;
    }

    @Override
    public String validateUser(String login, String password) {
        User user = customerDAO.validateUser(login);
        String hashPassword = BCryptPassword.hashPassword(password);

        if (user == null || user.getPassword().equals(hashPassword)) {
            return "login-error";
        }

        return customerDAO.getRoleForUser(user);

    }

    @Override
    public List<Role> getRoles() {
        return customerDAO.getRoles();
    }

    @Override
    public String registerUser(User user) {
        user.setPassword(BCryptPassword.hashPassword(user.getPassword()));
        return customerDAO.registerUser(user);
    }
}
