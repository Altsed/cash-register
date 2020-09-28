package dao;

import entity.Product;
import entity.Receipt;
import entity.Role;
import entity.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static dao.query.SqlQuery.*;

public class CustomerDAOPostgresqlImpl implements CustomerDAO {

    PoolConnectionBuilder connectionBuilder = new PoolConnectionBuilderPostresqlImpl();
    @Override
    public Product getProduct(String reference, String name) {
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;

        try(Connection connection = connectionBuilder.getConnection()){
            preparedStatement = connection.prepareStatement(CHECK_PRODUCT_IF_EXISTS);
            preparedStatement.setString(1, reference);
            preparedStatement.setString(2, name);
            if (!preparedStatement.execute()){
                return null;
            }
            resultSet = preparedStatement.getResultSet();
            resultSet.next();
            return new Product(resultSet.getInt("id"),
                     resultSet.getString("reference"),
                    resultSet.getString("name"),
                    resultSet.getBoolean("is_weight"));

        } catch (SQLException throwables) {
            throwables.printStackTrace();

        }finally {
            closeStatmentAndResultSet(preparedStatement, resultSet);
        }
        return null;

    }

    @Override
    public double getStockForProduct(Product product) {
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        try(Connection connection = connectionBuilder.getConnection()){
            preparedStatement = connection.prepareStatement(GET_STOCK_BY_PRODUCT_ID);
            preparedStatement.setInt(1, product.getId());
            if (!preparedStatement.execute()){
                return 0;
            }
            resultSet = preparedStatement.getResultSet();
            resultSet.next();
            return resultSet.getDouble("available_quantity");

        } catch (SQLException throwables) {
            throwables.printStackTrace();

        }finally {
            closeStatmentAndResultSet(preparedStatement, resultSet);
        }
        return 0;

    }

    public Product getProductByID(int productId) {
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        try(Connection connection = connectionBuilder.getConnection()){
            preparedStatement = connection.prepareStatement(GET_PRODUCT_BY_ID);
            preparedStatement.setInt(1, productId);
            if (!preparedStatement.execute()){
                return null;
            }
            resultSet = preparedStatement.getResultSet();
            resultSet.next();
            return new Product(resultSet.getInt("id"),
                    resultSet.getString("reference"),
                    resultSet.getString("name"),
                    resultSet.getBoolean("isWeight"));

        } catch (SQLException throwables) {
            throwables.printStackTrace();

        }finally {
            closeStatmentAndResultSet(preparedStatement, resultSet);
        }
        return null;

    }

    @Override
    public void createProduct(Product product) {

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try (Connection connection = connectionBuilder.getConnection()) {
            preparedStatement = connection.prepareStatement(CREATE_PRODUCT, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, product.getReference());
            preparedStatement.setString(2, product.getName());
            preparedStatement.setBoolean(3, product.isWeight());

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
                String reference = resultSet.getString("reference");
                String nameOfProduct = resultSet.getString("name");
                boolean isWeight = resultSet.getBoolean("is_weight");
                double stock = resultSet.getDouble("available_quantity");
                products.add( new Product (id, reference, nameOfProduct, isWeight, stock));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            closeStatmentAndResultSet(statement, resultSet);
        }
        return products;
    }

    @Override
    public int getLoginId(String login) {
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        try(Connection connection = connectionBuilder.getConnection()){
            preparedStatement = connection.prepareStatement(GET_USER_ID_BY_LOGIN);
            preparedStatement.setString(1, login);
            if (!preparedStatement.execute()){
                return 0;
            }
            resultSet = preparedStatement.getResultSet();
            resultSet.next();
            return resultSet.getInt("id");

        } catch (SQLException throwables) {
            throwables.printStackTrace();

        }finally {
            closeStatmentAndResultSet(preparedStatement, resultSet);
        }
        return 0;
    }

    @Override
    public User getUserById(int user_id) {
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        try(Connection connection = connectionBuilder.getConnection()){
            preparedStatement = connection.prepareStatement(GET_USER_BY_ID);
            preparedStatement.setInt(1, user_id);
            if (!preparedStatement.execute()){
                return null;
            }
            resultSet = preparedStatement.getResultSet();
            resultSet.next();
            return new User(resultSet.getString("login"));

        } catch (SQLException throwables) {
            throwables.printStackTrace();

        }finally {
            closeStatmentAndResultSet(preparedStatement, resultSet);
        }
        return null;
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
                        resultSet.getInt("role_id"));
            }
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

    @Override
    public List<Receipt> getReceipts() {
//        List<Product> receipts = new ArrayList<>();
//        Statement statement = null;
//        ResultSet resultSet = null;
//        try(Connection connection = connectionBuilder.getConnection()){
//            statement = connection.createStatement();
//            resultSet = statement.executeQuery(GET_PRODUCTS);
//            while (resultSet.next()){
//                int id = resultSet.getInt("id");
//                String nameOfProduct = resultSet.getString("name");
//                boolean isWeight = resultSet.getBoolean("is_weight");
//                double stock = resultSet.getDouble("available_quantity");
//                products.add( new Product (id, nameOfProduct, isWeight, stock));
//            }
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        } finally {
//            closeStatmentAndResultSet(statement, resultSet);
//        }
        return null;
    }

    @Override
    public boolean isProductExists(String reference, String name) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try (Connection connection = connectionBuilder.getConnection()) {
            preparedStatement = connection.prepareStatement(CHECK_PRODUCT_IF_EXISTS);
            preparedStatement.setString(1, reference);
            preparedStatement.setString(2, name);
            resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()){
                return false;
            }
            if (resultSet.next()){

                return false;
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            closeStatmentAndResultSet(preparedStatement, resultSet);
        }
        return true;
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

    @Override
    public int addProductToReceipt(int receiptId, Product product, int user_id, double quantity) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try (Connection connection = connectionBuilder.getConnection()) {
            if (receiptId == 0) {
                preparedStatement = connection.prepareStatement(CREATE_RECEIPT, Statement.RETURN_GENERATED_KEYS);
                preparedStatement.setInt(1, user_id);
                if (preparedStatement.executeUpdate() > 0) {
                    resultSet = preparedStatement.getGeneratedKeys();
                    if (resultSet != null && resultSet.next()) {
                        receiptId = resultSet.getInt("id");
                    }
                }
            }
            preparedStatement = connection.prepareStatement(INSERT_PRODUCTS_TO_RECEIPT);
            int productIdToInsert = getProduct(product.getReference(), product.getName()).getId();
            preparedStatement.setInt(1, receiptId);
            preparedStatement.setInt(2, productIdToInsert);
            preparedStatement.setDouble(3, quantity);
            if (preparedStatement.executeUpdate() > 0) {
                return receiptId;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            closeStatmentAndResultSet(preparedStatement, resultSet);
        }
        return 0;
    }

    @Override
    public Receipt getReceipt(int receiptId) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try (Connection connection = connectionBuilder.getConnection()) {
            preparedStatement = connection.prepareStatement(GET_RECEIPT_BY_ID);
            preparedStatement.setInt(1, receiptId);
            if (preparedStatement.execute()) {
                Receipt receipt = new Receipt();
                resultSet = preparedStatement.getResultSet();
                while (resultSet.next()) {
                    Product product = new Product(resultSet.getInt("product_id"),
                            resultSet.getString("reference"),
                            resultSet.getString("name"));
                            resultSet.getBoolean("is_weight");
                    double quantity = resultSet.getDouble("quantity");
                    if (receipt.getProductList().containsKey(product)){
                        double existingQuantity = receipt.getProductList().get(product);
                        receipt.getProductList().put(product, quantity + existingQuantity);
                    }
                    else {
                        receipt.getProductList().put(product, quantity);
                    }
               }
                return receipt;
            }
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            closeStatmentAndResultSet(preparedStatement, resultSet);
        }
        return null;
    }

}
