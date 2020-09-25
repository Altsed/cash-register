package service;

import dao.CustomerDAO;
import dao.CustomerDAOPostgresqlImpl;
import entity.Product;
import entity.Role;
import entity.User;

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
        return customerDAO.validateUser(login, password);
    }

    @Override
    public List<Role> getRoles() {
        return customerDAO.getRoles();
    }

    @Override
    public String registerUser(String login, String password, String role) {




        return null;
    }
}
