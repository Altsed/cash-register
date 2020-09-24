package dao;

import entity.Product;
import validation.BCryptPassword;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static dao.query.SqlQuery.*;
import static validation.BCryptPassword.checkPass;
import static validation.BCryptPassword.hashPassword;

public class CustomerDAOPostgresqlImpl implements CustomerDAO {

    PoolConnectionBuilder connectionBuilder = new PoolConnectionBuilderPostresqlImpl();
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
        String name = new String();
        try(Connection connection = connectionBuilder.getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(GET_ROLE_AND_PASSWORD_FOR_THE_USER);
            preparedStatement.setString(1, login);

            if (!preparedStatement.execute()){
                return name;
            }
            ResultSet resultSet = preparedStatement.getResultSet();
            resultSet.next();
            boolean checkPasword = checkPass(password, resultSet.getString("password"));
            name = resultSet.getString("name");
            preparedStatement.close();
            resultSet.close();
            return checkPasword ? name : "";

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return name;

    }


}
