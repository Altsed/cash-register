package dao;

import entity.Product;
import entity.Role;
import validation.BCryptPassword;

import java.sql.*;
import java.util.ArrayList;
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

    @Override
    public List<Role> getRoles() {
        List<Role> listRoles = new ArrayList<>();
        Statement statement = null;
        ResultSet resultSet = null;
        try(Connection connection = connectionBuilder.getConnection()){
            statement = connection.createStatement();
            resultSet = statement.executeQuery(GET_ROLES);
           while (resultSet.next()){
               listRoles.add( new Role (resultSet.getInt("id"),resultSet.getString("name")));
            }
            return listRoles;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }

        return listRoles;

    }
}
