package utils;

public class main {


    public static void main(String[] args) {

        String password = "12345678";
        String hashPassword = BCryptPassword.hashPassword(password);
        System.out.println(BCryptPassword.checkPass(password, "$2a$10$Ur2jfGKGVFxQ4QRRT2ZJTOs3nnsnYFrOTcyq/c.OFh1XiB/V8h8Cu"));

    }
}
