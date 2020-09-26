package service;

import dao.CustomerDAO;
import dao.CustomerDAOPostgresqlImpl;
import entity.Datable;
import entity.Product;
import entity.Role;
import entity.User;
import utils.BCryptPassword;
import utils.HttpUtils;

import javax.servlet.http.HttpServletRequest;
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
        return customerDAO.getProducts();
    }



    @Override
    public String validateUser(String login, String password) {
        User user = customerDAO.validateUser(login);
        String hashPassword = BCryptPassword.hashPassword(password);

        if (user == null || user.getLogin() == null || !BCryptPassword.checkPass(password, user.getPassword())) {
            return "login-error";
        }
        return customerDAO.getRoleForUser(user);
    }

    @Override
    public void loginApproved(HttpServletRequest request, String roleName) {
        HttpUtils.setRoleToSession(request, roleName);
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
