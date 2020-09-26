package entity;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Receipt implements Datable{
    Map<Product, Double> productList;
    User user;
    boolean isClosed;

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
