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

    public int getId() {
        return id;
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
}
