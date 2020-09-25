package dao.query;

public class SqlQuery {
    public static final String GET_ROLE_ID_AND_PASSWORD_FOR_THE_USER =
            "SELECT role_id, password FROM user_account JOIN user_role ON user_account.role_id = user_role.id and user_account.login = ?";
    public static final String GET_ROLES = "SELECT * FROM user_role";

    public static final String GET_ROLE_FOR_THE_USER = "SELECT name FROM user_role WHERE id=?";
    public static final String CREATE_USER = "INSERT INTO user_account (login, password, role_id) VALUES\n" +
            "(?, ?, ?);";


}
