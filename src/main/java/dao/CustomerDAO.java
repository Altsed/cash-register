package dao;

import entity.Product;

import java.util.List;

interface CustomerDAO {
    void getProduct();
    void createProduct();
    List<Product> getProducts();



}
