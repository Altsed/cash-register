package dao.query;

public class SqlQuery {
    public static final String GET_ROLE_AND_PASSWORD_FOR_THE_USER =
            "SELECT name, password FROM user_role, user_account where login=?";

}
