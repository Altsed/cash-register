package dao;

import entity.Product;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import static dao.query.SqlQuery.*;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CustomerDAOPostgresqlImplTest extends TestCase {
    @Mock
    private Connection connection;

    @Mock
    private PreparedStatement preparedStatement;

    @Mock
    private ResultSet resultSet;

    private Product product;

    @Mock
    private PoolConnectionBuilder poolConnectionBuilder;

    CustomerDAOPostgresqlImpl customerDAOPostgresql = new CustomerDAOPostgresqlImpl();

    @Before
    public void init() throws Exception{
        poolConnectionBuilder = mock(PoolConnectionBuilder.class);
        when(connection.prepareStatement(CREATE_PRODUCT, Statement.RETURN_GENERATED_KEYS)).thenReturn(preparedStatement);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(poolConnectionBuilder.getConnection()).thenReturn(connection);
        customerDAOPostgresql.setConnectionBuilder(poolConnectionBuilder);
        product = new Product(1,"reference", "name", true);
        when(resultSet.first()).thenReturn(true);
        when(resultSet.getInt(1)).thenReturn(1);
        when(resultSet.getString(2)).thenReturn(product.getReference());
        when(resultSet.getString(3)).thenReturn(product.getName());
        when(resultSet.getBoolean(3)).thenReturn(product.isWeight());
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
    }

    @Test (expected = IllegalArgumentException.class)
    public void nullCreateThrowsException(){
        new CustomerDAOPostgresqlImpl().createProduct(null);
    }

    @Test
    public void testGetProduct() {
        customerDAOPostgresql.getProduct(product.getReference(), product.getName());
    }

    @Test
    public void testCreateProduct() {
        customerDAOPostgresql.createProduct(product);
    }
}