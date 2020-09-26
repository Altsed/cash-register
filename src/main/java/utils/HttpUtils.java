package utils;

import javax.servlet.ServletRequest;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HttpUtils {

    private static final String roleAttribute = "role";

    public static void setRoleToSession(HttpServletRequest request, String roleName){
        request.getSession().setAttribute(roleAttribute, roleName);

    }
    public static void storeRoleInCookie(HttpServletResponse response, String roleName){
        Cookie cookie = new Cookie("role", roleName);
        cookie.setMaxAge(10*60);
        response.addCookie(cookie);
    }

    public static String getRoleFromSession(HttpServletRequest request){
        return (String) request.getSession().getAttribute(roleAttribute);

    }


}
