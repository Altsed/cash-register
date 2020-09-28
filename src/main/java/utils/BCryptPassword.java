package utils;

import org.mindrot.jbcrypt.BCrypt;

public class BCryptPassword {

    public static boolean checkPass(String plainPassword, String hashedPassword) {
        return BCrypt.checkpw(plainPassword, hashedPassword);
    }
    public static String hashPassword(String plainTextPassword){
        return BCrypt.hashpw(plainTextPassword, BCrypt.gensalt());
    }

    public static void main(String[] args) {
        System.out.println(BCrypt.hashpw("3", BCrypt.gensalt()));
    }
}
