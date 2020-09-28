package dao.query;

public class SqlQuery {
    public static final String GET_ROLE_ID_AND_PASSWORD_FOR_THE_USER =
            "SELECT role_id, password FROM user_account JOIN user_role ON user_account.role_id = user_role.id and user_account.login = ?";
    public static final String GET_ROLES = "SELECT * FROM user_role";

    public static final String GET_ROLE_FOR_THE_USER = "SELECT name FROM user_role WHERE id=?";
    public static final String GET_USER_ID_BY_LOGIN = "SELECT id FROM user_account WHERE login=?";
    public static final String GET_USER_BY_ID = "SELECT login FROM user_account WHERE id=?";
    public static final String CREATE_USER = "INSERT INTO user_account (login, password, role_id) VALUES\n" +
            "(?, ?, ?);";
    public static final String GET_PRODUCT_BY_ID = "SELECT * FROM product WHERE product_id=?";
    public static final String GET_STOCK_BY_PRODUCT_ID = "SELECT available_quantity FROM warehouse WHERE product_id=?";
    public static final String GET_PRODUCTS = "SELECT * FROM product FULL OUTER JOIN  warehouse w on product.id = w.product_id";


    public static final String CREATE_PRODUCT = "INSERT INTO product (reference, name, is_weight) VALUES\n" +
            "(?, ?, ?);";
    public static final String INSERT_INIT_QUANTITY ="INSERT INTO warehouse (product_id, available_quantity) VALUES\n" +
            "(?, ?);";
    public static final String UPDATE_QUANTITY_FOR_THE_PRODUCT ="UPDATE warehouse SET available_quantity=? WHERE product_id=?";

    public static final String CHECK_PRODUCT_IF_EXISTS = "SELECT * FROM product WHERE reference=? OR name=?;";

    public static final String CREATE_RECEIPT = "INSERT INTO receipt (user_id) VALUES (?);";
    public static final String GET_RECEIPT_BY_ID ="SELECT product_id, reference, name, quantity, is_weight FROM receipt_has_product\n" +
            "    INNER JOIN receipt ON receipt_id=receipt.id\n" +
            "    INNER JOIN product ON receipt_has_product.product_id = product.id\n" +
            "    WHERE receipt_id=?";

    public static final String INSERT_PRODUCTS_TO_RECEIPT = "INSERT INTO receipt_has_product " +
            "(receipt_id, product_id, quantity) VALUES (?, ?, ?);";
}
