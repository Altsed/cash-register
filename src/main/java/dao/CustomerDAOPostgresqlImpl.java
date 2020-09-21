package dao;

import entity.Product;

import java.util.List;

public class CustomerDAOPostgresqlImpl implements CustomerDAO {

    PoolConnectionBuilder connectionBuilder = new PoolConnectionBuilderPostresqlImpl();
    @Override
    public void getProduct() {

    }

    @Override
    public void createProduct() {

    }

    @Override
    public List<Product> getProducts() {
        return null;
    }
}
