package controller.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;



@WebFilter(filterName = "languageFilter", urlPatterns = { "/*" })
public class LanguageFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        if (request.getParameter("language") != null) {
            request.getSession().setAttribute("language", "ua");
        }
        System.out.println("locale set to " + request.getParameter("language"));
        filterChain.doFilter(request, response);


    }




}
