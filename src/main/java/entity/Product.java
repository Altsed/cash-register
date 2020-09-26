package entity;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Product implements Datable{
    private int id;
    private String name;
    private boolean isWeight;

    List<String> getFields(){
        Field[] field = Receipt.class.getDeclaredFields();
        ArrayList<String> result = new ArrayList<>();
        Arrays.stream(field).forEach(x->result.add(x.getName()));
        return result;
    }

}
