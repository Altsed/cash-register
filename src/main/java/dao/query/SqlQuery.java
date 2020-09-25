package dao.query;

public class SqlQuery {
    public static final String GET_ROLE_AND_PASSWORD_FOR_THE_USER =
            "SELECT name, password FROM user_role, user_account where login=?";
    public static final String GET_ROLES = "SELECT * FROM user_role";

    public static final String GET_ROLE_FOR_THE_USER = "SELECT name FROM user_role WHERE id=?";
    public static final String CREATE_USER = "INSERT INTO user_account (login, password, role_id) VALUES\n" +
            "(?, ?, ?);";


}
