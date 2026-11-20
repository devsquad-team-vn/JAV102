package com.polycoffee.filter;

import com.polycoffee.util.AuthUtil;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * Filter kiem tra dang nhap va phan quyen ADMIN/STAFF (Lab 4 - Bai 2).
 *
 * @author Khoa (Nhóm PolyCoffee - JAV102)
 */
@WebFilter({"/manager/*", "/employee/*", "/pos/*"})
public class AuthFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String uriString = req.getRequestURI();
        String query = req.getQueryString();
        String fullUri = query != null ? uriString + "?" + query : uriString;

        if (!AuthUtil.isAuthenticated(req)) {
            req.getSession(true).setAttribute("REDIRECT_URL", fullUri);
            res.sendRedirect(req.getContextPath() + "/dang-nhap");
            return;
        }

        if (uriString.contains("/manager") && !AuthUtil.isManager(req)) {
            req.getSession(true).setAttribute("AUTH_ERROR", "Ban khong co quyen Quan ly (403) de truy cap chuc nang nay!");
            res.sendRedirect(req.getContextPath() + "/trang-chu?error=403");
            return;
        }

        chain.doFilter(req, res);
    }
}
