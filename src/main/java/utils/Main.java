package utils;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class Main {

private static final Logger log = LogManager.getLogger(Main.class);

    public static void main(String[] args) {


        log.info(Main.class.getClass().getName());
        log.error("test message");
        log.fatal("test message");
        log.warn("test message");
        log.info("test message");


//        String password = "12345678";
//        String hashPassword = BCryptPassword.hashPassword(password);
//        System.out.println(BCryptPassword.checkPass(password, "$2a$10$Ur2jfGKGVFxQ4QRRT2ZJTOs3nnsnYFrOTcyq/c.OFh1XiB/V8h8Cu"));

    }
}
