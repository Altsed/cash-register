package dao;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class PoolConnectionBuilderPostresqlImpl implements PoolConnectionBuilder {
    @Override
    public Connection getConnection() {
        Connection con = null;
        try {
            InitialContext cxt = new InitialContext();
            Context initContext = new InitialContext();
            Context envContext = (Context) initContext.lookup("java:comp/env");
            DataSource ds = (DataSource) envContext.lookup("jdbc/desk_db");
            con = ds.getConnection();

        } catch (NamingException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return con;
    }

    public static void main(String[] args) {
        PoolConnectionBuilder poolConnectionBuilder = new PoolConnectionBuilderPostresqlImpl();
        poolConnectionBuilder.getConnection();
    }

}
