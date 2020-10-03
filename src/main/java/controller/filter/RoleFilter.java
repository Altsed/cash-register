package controller.filter;

import service.CashRegisterService;
import service.CashRegisterServiceImpl;
import utils.HttpUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Optional;


@WebFilter(filterName = "roleFilter", urlPatterns = { "/*" })
public class RoleFilter implements Filter {
    List<String> listOfRoles;


    @Override
    public void init(FilterConfig filterConfig) {
        CashRegisterService cashRegisterService = new CashRegisterServiceImpl();
        listOfRoles = new ArrayList<>();
        cashRegisterService.getRoles().stream().forEach(x->listOfRoles.add(x.getName()));
    }


    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        try {
            String roleInSession = HttpUtils.getRoleFromCookie(request);
            String roleFromRequest = getRoleFromRequest(request);
            checkParametersFromRequest(request);
            if (roleFromRequest != "null" && roleInSession == "null") {
                response.sendRedirect("/");
                return;
            }

            if (roleFromRequest != "null" && !roleInSession.equals(roleFromRequest)) {
                response.sendRedirect("/");
                return;
            }
            filterChain.doFilter(request, response);
        }catch (Throwable ex){
            ex.printStackTrace();
            response.sendRedirect("/");
        }
    }



    @Override
    public void destroy() {

    }

    private String getRoleFromRequest(HttpServletRequest request){
        String Url = request.getRequestURI();
        return listOfRoles.stream().filter(x->Url.contains(x)).findFirst().orElse("null");
    }
    private boolean checkParametersFromRequest(HttpServletRequest request){
        Enumeration enumeration = request.getParameterNames();
        while (enumeration.hasMoreElements()){
            System.out.println("parameters names: " + enumeration.nextElement());
        }

        return request.getParameterNames().hasMoreElements();

    }
}