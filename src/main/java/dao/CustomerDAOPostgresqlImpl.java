package dao;

import entity.Product;
import entity.Receipt;
import entity.Role;
import entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.*;

import static dao.query.SqlQuery.*;

public class CustomerDAOPostgresqlImpl implements CustomerDAO {
    private final Logger logger = LogManager.getLogger(CustomerDAOPostgresqlImpl.class);

    PoolConnectionBuilder connectionBuilder = new PoolConnectionBuilderPostresqlImpl();

    public void setConnectionBuilder(PoolConnectionBuilder connectionBuilder) {
        this.connectionBuilder = connectionBuilder;
    }

    @Override
    public Product getProduct(String reference, String name)  {
        Product product = new Product();
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;

        try(Connection connection = connectionBuilder.getConnection()){
            preparedStatement = connection.prepareStatement(CHECK_PRODUCT_IF_EXISTS);
            preparedStatement.setString(1, Optional.of(reference).orElse(""));
            preparedStatement.setString(2, Optional.of(name).orElse(""));
            if (!preparedStatement.execute()){
                return null;
            }
            resultSet = preparedStatement.getResultSet();
            resultSet.next();

            product = new Product(resultSet.getInt("id"),
                    resultSet.getString("reference"),
                    resultSet.getString("name"),
                    resultSet.getBoolean("is_weight"));

        } catch (SQLException throwables) {
            logger.error(throwables.getLocalizedMessage());


        }finally {
            closeStatmentAndResultSet(preparedStatement, resultSet);
        }
        return product;

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
            logger.error(throwables.getLocalizedMessage());
        } finally {
            closeStatmentAndResultSet(statement, resultSet);
        }
        return products;
    }
    @Override
    public void createProduct(Product product) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        if (product == null) {
            throw new IllegalArgumentException();
        }
        try {
            connection = connectionBuilder.getConnection();
            connection.setAutoCommit(false);
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
            connection.commit();

        } catch (SQLException throwables) {
            try {
                connection.rollback();
                logger.error(throwables.getLocalizedMessage());
            } catch (SQLException e) {
                logger.error(throwables.getLocalizedMessage());
            }

        }finally {
            try {
                connection.close();
            } catch (SQLException throwables) {
                logger.error(throwables.getLocalizedMessage());
            }
            closeStatmentAndResultSet(preparedStatement, resultSet);
        }

    }


    @Override
    public void deleteProductFromReceipt(int product_id, int receipt_id) {
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;

        try(Connection connection = connectionBuilder.getConnection()){
            preparedStatement = connection.prepareStatement(DELETE_PRODUCT_FROM_RECEIPT);
            preparedStatement.setInt(1, product_id);
            preparedStatement.setInt(2, receipt_id);
            preparedStatement.executeUpdate();

            if (getReceipt(receipt_id).getProductList().size() == 0) {
                deleteReceipt(receipt_id);
            }

        } catch (SQLException throwables) {
            logger.error(throwables.getLocalizedMessage());

        }finally {
            closeStatmentAndResultSet(preparedStatement, resultSet);
        }
    }

    @Override
    public void deleteReceipt(int receipt_id) {
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;

        try(Connection connection = connectionBuilder.getConnection()){
            preparedStatement = connection.prepareStatement(DELETE_RECEIPT);
            preparedStatement.setInt(1, receipt_id);
            preparedStatement.executeUpdate();

        } catch (SQLException throwables) {
            logger.error(throwables.getLocalizedMessage());

        }finally {
            closeStatmentAndResultSet(preparedStatement, resultSet);
        }
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
            logger.error(throwables.getLocalizedMessage());

        }finally {
            closeStatmentAndResultSet(preparedStatement, resultSet);
        }
        return 0;

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
            logger.error(throwables.getLocalizedMessage());
        }finally {
            closeStatmentAndResultSet(preparedStatement, resultSet);
        }

    }

    @Override
    public void updateQuantity(int receipt_id, int product_id, double quantity) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try (Connection connection = connectionBuilder.getConnection()) {
            preparedStatement = connection.prepareStatement(UPDATE_QUANTITY_IN_RECEIPT_FOR_THE_PRODUCT);
            preparedStatement.setDouble(1, quantity);
            preparedStatement.setInt(2, product_id);
            preparedStatement.setInt(3, receipt_id);
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            logger.error(throwables.getLocalizedMessage());
        }finally {
            closeStatmentAndResultSet(preparedStatement, resultSet);
        }
    }

    @Override
    public void closeReceipt(int receipt_id) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try (Connection connection = connectionBuilder.getConnection()) {
            preparedStatement = connection.prepareStatement(CLOSE_RECEIPT);
            preparedStatement.setInt(1, receipt_id);
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            logger.error(throwables.getLocalizedMessage());
        }finally {
            closeStatmentAndResultSet(preparedStatement, resultSet);
        }
    }


    @Override
    public int getLoginId(String login) {
        int id = 0;
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        try(Connection connection = connectionBuilder.getConnection()){
            preparedStatement = connection.prepareStatement(GET_USER_ID_BY_LOGIN);
            preparedStatement.setString(1, login);
            if (!preparedStatement.execute()){
                return id;
            }
            resultSet = preparedStatement.getResultSet();
            resultSet.next();
            return resultSet.getInt("id");

        } catch (SQLException throwables) {
            logger.error(throwables.getLocalizedMessage());

        }finally {
            closeStatmentAndResultSet(preparedStatement, resultSet);
        }
        return id;
    }

    @Override
    public User getUserById(int user_id) {
        User user = new User();
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
            logger.error(throwables.getLocalizedMessage());

        }finally {
            closeStatmentAndResultSet(preparedStatement, resultSet);
        }
        return user;
    }

    @Override
    public User validateUser(String login) {
        User user = new User();
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        try(Connection connection = connectionBuilder.getConnection()){
            preparedStatement = connection.prepareStatement(GET_ROLE_ID_AND_PASSWORD_FOR_THE_USER);
            preparedStatement.setString(1, login);

            if (!preparedStatement.execute()){
                return null;
            }
            resultSet = preparedStatement.getResultSet();
            while (resultSet.next()){
                user = new User(login,
                        resultSet.getString("password"),
                        resultSet.getInt("role_id"));
            }
            return user;

        } catch (SQLException throwables) {
            logger.error(throwables.getLocalizedMessage());

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
            logger.error(throwables.getLocalizedMessage());
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
            logger.error(throwables.getLocalizedMessage());
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

            logger.error(throwables.getLocalizedMessage());


        }finally {
            closeStatmentAndResultSet(preparedStatement, resultSet);
        }
        return statusUser;

    }

    @Override
    public List<Receipt> getReceipts() {
        List<Receipt> receipts = new ArrayList<>();
        Statement statement = null;
        ResultSet resultSet = null;
        try(Connection connection = connectionBuilder.getConnection()){
            statement = connection.createStatement();
            resultSet = statement.executeQuery(GET_RECEIPTS);
            while (resultSet.next()){
                int id = resultSet.getInt("id");
                String login = resultSet.getString("login");
                boolean status = Boolean.getBoolean(resultSet.getString("status"));
                User user = new User(login);
                receipts.add(new Receipt(id, user, status));
             }
        } catch (SQLException throwables) {
            logger.error(throwables.getLocalizedMessage());
        } finally {
            closeStatmentAndResultSet(statement, resultSet);
        }
        return receipts;
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
            logger.error(throwables.getLocalizedMessage());
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
                logger.error(throwables.getLocalizedMessage());
            }
        }
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException throwables) {
                logger.error(throwables.getLocalizedMessage());
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
            logger.error(throwables.getLocalizedMessage());
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
            return null;
        }
        catch (SQLException throwables) {
            logger.error(throwables.getLocalizedMessage());
        }finally {
            closeStatmentAndResultSet(preparedStatement, resultSet);
        }
        return null;
    }

    @Override
    public List<Product> generateTop10Report() {
        List<Product> products = new ArrayList<>();
        Statement statement = null;
        ResultSet resultSet = null;
        try(Connection connection = connectionBuilder.getConnection()){
            statement = connection.createStatement();
            resultSet = statement.executeQuery(GET_TOP_TEN_PRODUCTS);
            while (resultSet.next()){
                products.add( new Product(resultSet.getString("reference"),
                        resultSet.getString("name"),
                        resultSet.getDouble("sum")));
            }
            return products;
        } catch (SQLException throwables) {
            logger.error(throwables.getLocalizedMessage());
        } finally {
            closeStatmentAndResultSet(statement, resultSet);
        }
        return products;
    }

    @Override
    public Map<User, Integer> generateBestOperatorsReport() {
        Map<User, Integer> map = new HashMap<>();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try (Connection connection = connectionBuilder.getConnection()) {
            preparedStatement = connection.prepareStatement(GET_BEST_OPERATORS);
            if (preparedStatement.execute()) {
                resultSet = preparedStatement.getResultSet();
                while (resultSet.next()) {
                    User user = new User(resultSet.getInt("user_id"),
                            resultSet.getString("login"));
                    int sumOfReceipts = resultSet.getInt("sum");
                    map.put(user, sumOfReceipts);
                }
            }
            return map;
        }
        catch (SQLException throwables) {
            logger.error(throwables.getLocalizedMessage());
        }finally {
            closeStatmentAndResultSet(preparedStatement, resultSet);
        }
        return map;
     }

    @Override
    public List<Product> getProducts(int currentPage, int recordsPerPage) {

        List<Product> products = new ArrayList<>();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        int start = currentPage * recordsPerPage - recordsPerPage;
        try(Connection connection = connectionBuilder.getConnection()){
            preparedStatement = connection.prepareStatement(GET_PRODUCTS_FOR_PAGE);
            preparedStatement.setInt(1, start );
            preparedStatement.setInt(2, recordsPerPage);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                int id = resultSet.getInt("id");
                String reference = resultSet.getString("reference");
                String nameOfProduct = resultSet.getString("name");
                boolean isWeight = resultSet.getBoolean("is_weight");
                double stock = resultSet.getDouble("available_quantity");
                Product product = new Product (id, reference, nameOfProduct, isWeight, stock);
                products.add( new Product (id, reference, nameOfProduct, isWeight, stock));
            }

            return products;
        } catch (SQLException throwables) {
            logger.error(throwables.getLocalizedMessage());
        } finally {
            closeStatmentAndResultSet(preparedStatement, resultSet);
        }
        return products;

    }

    @Override
    public int getNumberOfRows() {
        Integer numOfRows = 0;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try(Connection connection = connectionBuilder.getConnection()){
            preparedStatement = connection.prepareStatement(GET_PRODUCTS_COUNT);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                numOfRows = resultSet.getInt(1);
            }
            return numOfRows;
        } catch (SQLException throwables) {
            logger.error(throwables.getLocalizedMessage());
        } finally {
            closeStatmentAndResultSet(preparedStatement, resultSet);
        }
        return numOfRows;
    }
}
