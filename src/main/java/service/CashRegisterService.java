package service;

import entity.Product;
import java.util.List;

public interface CashRegisterService {
    void getProduct();
    void createProduct();
    List<Product> getProducts();
}
