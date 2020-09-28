package entity;

import java.lang.reflect.Field;
import java.util.*;

public class Receipt implements Datable{
    int id;
    Map<Product, Double> productList = new HashMap<>();
    User user;
    boolean isClosed;

    public int getId() {
        return id;
    }

    public Map<Product, Double> getProductList() {
        return productList;
    }

    public User getUser() {
        return user;
    }

    public void setProductList(Map<Product, Double> productList) {
        this.productList = productList;
    }

    public void setUser(User user) {
        this.user = user;
    }

    List<String> getFields(){
        Field[] field = Receipt.class.getDeclaredFields();
      ArrayList<String> result = new ArrayList<>();
        Arrays.stream(field).forEach(x->result.add(x.getName()));
      return result;
    }





    public static void main(String[] args) {
        Receipt receipt = new Receipt();
        System.out.println(receipt.getFields());
    }


}
