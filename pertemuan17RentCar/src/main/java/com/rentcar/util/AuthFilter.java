package com.rentcar.util;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.http.*;

public class AuthFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        HttpSession session = req.getSession(false);

        String uri = req.getRequestURI();
        boolean isLogin = uri.endsWith("login.jsp") || uri.endsWith("/login");
        boolean isCSS = uri.contains("/css/") || uri.contains("/js/") || uri.contains("/images/");

        if (isLogin || isCSS) {
            chain.doFilter(request, response);
            return;
        }

        if (session == null || session.getAttribute("username") == null) {
            res.sendRedirect(req.getContextPath() + "/login.jsp");
            return;
        }
        chain.doFilter(request, response);
    }

    @Override public void init(FilterConfig fc) {}
    @Override public void destroy() {}
}
