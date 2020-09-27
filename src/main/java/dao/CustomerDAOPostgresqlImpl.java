package dao;

import entity.Product;
import entity.Role;
import entity.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static dao.query.SqlQuery.*;

public class CustomerDAOPostgresqlImpl implements CustomerDAO {

    PoolConnectionBuilder connectionBuilder = new PoolConnectionBuilderPostresqlImpl();
    @Override
    public void getProduct() {

    }

    @Override
    public void createProduct(Product product) {

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try (Connection connection = connectionBuilder.getConnection()) {
            preparedStatement = connection.prepareStatement(CREATE_PRODUCT, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, product.getName());
            preparedStatement.setBoolean(2, product.isWeight());

            if (preparedStatement.executeUpdate() > 0) {
                resultSet = preparedStatement.getGeneratedKeys();
                if (resultSet != null && resultSet.next()) {
                    product.setId(resultSet.getInt("id"));
                }
            }

            preparedStatement = connection.prepareStatement(INSERT_INIT_QUANTITY);
            preparedStatement.setInt(1, product.getId());
            preparedStatement.setDouble(2, product.getStock());
            preparedStatement.executeUpdate();


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            closeStatmentAndResultSet(preparedStatement, resultSet);
        }

    }

    @Override
    public void updateStock(Product product) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try (Connection connection = connectionBuilder.getConnection()) {
            preparedStatement = connection.prepareStatement(UPDATE_QUANTITY_FOR_THE_PRODUCT);
            preparedStatement.setDouble(1, product.getStock());
            preparedStatement.setInt(2, product.getId());
            preparedStatement.executeUpdate();
       } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            closeStatmentAndResultSet(preparedStatement, resultSet);
        }

    }

    @Override
    public List<Product> getProducts() {
        List<Product> products = new ArrayList<>();
        Statement statement = null;
        ResultSet resultSet = null;
        try(Connection connection = connectionBuilder.getConnection()){
            statement = connection.createStatement();
            resultSet = statement.executeQuery(GET_PRODUCTS);
            while (resultSet.next()){
                int id = resultSet.getInt("id");
                String nameOfProduct = resultSet.getString("name");
                boolean isWeight = resultSet.getBoolean("is_weight");
                double stock = resultSet.getDouble("available_quantity");
                products.add( new Product (id, nameOfProduct, isWeight, stock));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            closeStatmentAndResultSet(statement, resultSet);
        }
        return products;
    }

    @Override
    public User validateUser(String login) {
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        try(Connection connection = connectionBuilder.getConnection()){
            preparedStatement = connection.prepareStatement(GET_ROLE_ID_AND_PASSWORD_FOR_THE_USER);
            preparedStatement.setString(1, login);

            if (!preparedStatement.execute()){
                return null;
            }
            resultSet = preparedStatement.getResultSet();

            User user = new User();
            while (resultSet.next()){

                user = new User(login,
                        resultSet.getString("password"),
                        Integer.parseInt(resultSet.getString("role_id")));

            }
            preparedStatement.close();
            resultSet.close();
            return user;

        } catch (SQLException throwables) {
            throwables.printStackTrace();

        }finally {
            closeStatmentAndResultSet(preparedStatement, resultSet);
        }
        return null;

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
    public String getRoleForUser(User user) {
        String roleName = "";

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try(Connection connection = connectionBuilder.getConnection()){
            preparedStatement = connection.prepareStatement(GET_ROLE_FOR_THE_USER);
            preparedStatement.setInt(1, user.getRole());
            resultSet = preparedStatement.executeQuery();
            if (resultSet != null && resultSet.next()){
                return resultSet.getString("name");
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            closeStatmentAndResultSet(preparedStatement, resultSet);
        }
        return roleName;
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
