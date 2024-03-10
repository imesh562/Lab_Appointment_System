package com.imesh.lab.utils.filters;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomerRedirectFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) {}

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        boolean isUserLogged = req.getSession().getAttribute("id") != null;
        if (isUserLogged) {
            int userType = (int) req.getSession().getAttribute("user_type");
            if(userType == 2){
                RequestDispatcher requestDispatcher = req.getRequestDispatcher("/customer_index.jsp");
                requestDispatcher.forward(req, res);
            }
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {}

}
