package dao.query;

public class SqlQuery {
    public static final String GET_ROLE_ID_AND_PASSWORD_FOR_THE_USER =
            "SELECT role_id, password FROM user_account JOIN user_role ON user_account.role_id = user_role.id and user_account.login = ?";
    public static final String GET_ROLES = "SELECT * FROM user_role";

    public static final String GET_ROLE_FOR_THE_USER = "SELECT name FROM user_role WHERE id=?";
    public static final String CREATE_USER = "INSERT INTO user_account (login, password, role_id) VALUES\n" +
            "(?, ?, ?);";
    public static final String GET_PRODUCTS = "SELECT * FROM product FULL OUTER JOIN  warehouse w on product.id = w.product_id";

    public static final String CREATE_PRODUCT = "INSERT INTO product (name, is_weight) VALUES\n" +
            "(?, ?);";
    public static final String INSERT_INIT_QUANTITY ="INSERT INTO warehouse (product_id, available_quantity) VALUES\n" +
            "(?, ?);";


    public static final String UPDATE_QUANTITY_FOR_THE_PRODUCT ="UPDATE warehouse SET available_quantity=? WHERE product_id=?";
}
