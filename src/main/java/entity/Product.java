package entity;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Product {
    private int id;
    private String name;
    private boolean isWeight;
    private double stock;

    public Product(int id, String name, boolean isWeight, double stock) {
        this.id = id;
        this.name = name;
        this.isWeight = isWeight;
        this.stock = stock;
    }



    public Product(String name, boolean isWeight, double stock) {
        this.name = name;
        this.isWeight = isWeight;
        this.stock = stock;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }

    public boolean getIsWeight() {
        return isWeight;
    }

    public boolean isWeight() {
        return isWeight;
    }

    public double getStock() {
        return stock;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Product product = (Product) o;

        return name.equals(product.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
