package dao;

import entity.Product;
import entity.Role;
import entity.User;
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
        String name = "";
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        try(Connection connection = connectionBuilder.getConnection()){
            preparedStatement = connection.prepareStatement(GET_ROLE_AND_PASSWORD_FOR_THE_USER);
            preparedStatement.setString(1, login);

            if (!preparedStatement.execute()){
                return name;
            }
            resultSet = preparedStatement.getResultSet();
            resultSet.next();
            boolean checkPasword = checkPass(password, resultSet.getString("password"));
            name = resultSet.getString("name");
            preparedStatement.close();
            resultSet.close();
            return checkPasword ? name : "";

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            closeStatmentAndResultSet(preparedStatement, resultSet);
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
            closeStatmentAndResultSet(statement, resultSet);

        }

        return listRoles;

    }

    @Override
    public String registerUser(User user) {
        String statusUser = new String();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try(Connection connection = connectionBuilder.getConnection()){
            preparedStatement = connection.prepareStatement(CREATE_USER);
            preparedStatement.setString(1, user.getLogin());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setInt(3, user.getRole());
            if (preparedStatement.executeUpdate() > 0) {
                preparedStatement = connection.prepareStatement(GET_ROLE_FOR_THE_USER);
                preparedStatement.setInt(1, user.getRole());
                resultSet = preparedStatement.executeQuery();
                if (resultSet != null && resultSet.next()){
                    return resultSet.getString("name");
                }
            }

        } catch (SQLException throwables) {
            user.setValid(false);
            user.setMessage("User already exists.");
            throwables.printStackTrace();
            return user.getMessage();

        }finally {
            closeStatmentAndResultSet(preparedStatement, resultSet);
        }
        return statusUser;

    }

    private void closeStatmentAndResultSet(Statement statement, ResultSet resultSet){
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }
}
