package utils;

import javax.servlet.ServletRequest;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Arrays;

public class HttpUtils {

    private static final String roleAttribute = "role";
    private static final String userAttribute = "user_id";

    public static void setRoleToSession(HttpServletRequest request, String roleName){
        request.getSession().setAttribute(roleAttribute, roleName);

   }   public static String getUserFromSession(HttpServletRequest request){
        return (String) request.getSession().getAttribute(userAttribute);
   }
    public static void storeRoleInCookie(HttpServletResponse response, String roleName){
        Cookie cookie = new Cookie("role", roleName);
        cookie.setMaxAge(10*60);
        response.addCookie(cookie);
    }

    public static String getRoleFromSession(HttpServletRequest request){
        return (String) request.getSession().getAttribute(roleAttribute);

    }

    public static void deleteCookie(HttpServletRequest request, HttpServletResponse response){
        Cookie[] cookies = request.getCookies();
        if (cookies != null)
            for (Cookie cookie : cookies) {
                cookie.setValue("");
                cookie.setPath("/");
                cookie.setMaxAge(0);
                response.addCookie(cookie);
            }

    }
    public static void deleteSession(){

    }



    public static void storeLoginIdCookie(HttpServletResponse response, int loginId) {
        Cookie cookie = new Cookie("user_id", String.valueOf(loginId));
        cookie.setMaxAge(10*60);
        response.addCookie(cookie);
    }
    public static int getUserIdFromCookie(HttpServletRequest request){
        if (request.getCookies() == null) {
            return 0;
        }
        for (Cookie cookie : request.getCookies()){
            if ("user_id".equals(cookie.getName())){
                return Integer.parseInt(cookie.getValue());
            }
        }
       return 0;
    }
    public static String getRoleFromCookie(HttpServletRequest request) {
        if (request.getCookies() == null) {
            return "null";
        }
        for (Cookie cookie : request.getCookies()) {
            if ("role".equals(cookie.getName())) {
                return cookie.getValue();
            }
        }
        return "null";

    }

}
