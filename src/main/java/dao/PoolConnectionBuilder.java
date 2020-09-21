package dao;

import java.sql.Connection;

public interface PoolConnectionBuilder {
    Connection getConnection();
}
