package dao;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.util.*;

public class testDB implements A, B {
    public static void main(String[] args) {
        Properties props = new Properties();

        props.setProperty("name","jdbc/desk_db");
        props.setProperty("type","javax.sql.DataSource");
        props.setProperty("url","jdbc:postgresql://127.0.0.1:5432/desk_base");
        props.setProperty("username", "user");
        props.setProperty("password", "user123");
        props.setProperty("driverClassName","org.postgresql.Driver");


        Context ctx = null;
        try {
            ctx = new InitialContext(props);
           // DataSource ds = (DataSource)ctx.lookup("java:/comp/env/jdbc/desk_db");


        } catch (NamingException e) {
            e.printStackTrace();
        }

        Map<Map<String, String>, String> map = new HashMap<>();
        Map<String, String> map1 = new HashMap<>();
        map.put(map1, "WTF");


        String result = map.get(map);
        List<Number> list = new ArrayList<>();
        list.add(1);
        list.add(1L);
        list.add(10.1);
        System.out.println(list.get(1) instanceof Long);

    }
}
interface A{
    default void a(){
        System.out.println("a");
    }
}
interface B{

    }
}