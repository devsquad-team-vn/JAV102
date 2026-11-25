package com.polycoffee.servlet;

import com.polycoffee.entity.NhanVien;
import com.polycoffee.service.AuthService;
import com.polycoffee.service.ServiceResult;
import com.polycoffee.util.AuthUtil;
import com.polycoffee.util.ParamUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.net.URI;

/**
 * Servlet dieu phoi Dang nhap / Dang xuat noi bo qua AuthService (khong goi DAO truc tiep).
 *
 * @author Nam (Nhóm PolyCoffee - JAV102)
 */
@WebServlet({"/dang-nhap", "/dang-xuat", "/staff/login", "/staff/logout"})
public class AuthServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final AuthService authService;

    public AuthServlet() {
        this(new AuthService());
    }

    public AuthServlet(AuthService authService) {
        this.authService = authService;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String path = resolveServletPath(request);
        if ("/dang-xuat".equals(path) || "/staff/logout".equals(path)) {
            NhanVien current = AuthUtil.getUser(request);
            authService.recordLogout(current, request.getRemoteAddr());
            AuthUtil.clear(request);
            HttpSession session = request.getSession(false);
            if (session != null) {
                session.invalidate();
            }
            response.sendRedirect(safeContextPath(request) + "/dang-nhap");
            return;
        }
        NhanVien existing = AuthUtil.getUser(request);
        if (existing != null && !Boolean.FALSE.equals(existing.getDangHoatDong())) {
            response.sendRedirect(safeContextPath(request) + authService.resolveLandingPath(existing));
            return;
        }
        request.getRequestDispatcher("/views/auth/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String email = ParamUtil.getString(request, "email");
        String password = ParamUtil.getString(request, "password");

        ServiceResult<NhanVien> result = authService.authenticate(email, password, request.getRemoteAddr());
        if (!result.isSuccess()) {
            request.setAttribute("message", result.getMessage());
            request.getRequestDispatcher("/views/auth/login.jsp").forward(request, response);
            return;
        }

        NhanVien user = result.getData();
        HttpSession session = request.getSession(true);
        request.changeSessionId();
        AuthUtil.setUser(request, user);

        String ctx = safeContextPath(request);
        Object redirect = session.getAttribute("REDIRECT_URL");
        if (redirect != null) {
            session.removeAttribute("REDIRECT_URL");
            if (redirect instanceof String candidate && isSafeLocalRedirect(candidate, ctx)) {
                response.sendRedirect(candidate);
                return;
            }
        }
        response.sendRedirect(ctx + authService.resolveLandingPath(user));
    }

    private String resolveServletPath(HttpServletRequest request) {
        String sp = request.getServletPath();
        if (sp != null && !sp.isEmpty()) {
            return sp;
        }
        String uri = request.getRequestURI();
        String ctx = safeContextPath(request);
        if (!ctx.isEmpty() && uri != null && uri.startsWith(ctx)) {
            return uri.substring(ctx.length());
        }
        return uri != null ? uri : "";
    }

    private boolean isSafeLocalRedirect(String url, String ctx) {
        if (url == null || url.isBlank() || url.startsWith("//") || url.contains("://")) {
            return false;
        }
        try {
            URI parsed = URI.create(url.trim());
            if (parsed.isAbsolute() || parsed.getHost() != null) {
                return false;
            }
        } catch (IllegalArgumentException e) {
            return false;
        }
        String path = url.trim();
        return ctx.isEmpty() ? path.startsWith("/") : (path.equals(ctx) || path.startsWith(ctx + "/"));
    }

    private String safeContextPath(HttpServletRequest request) {
        String ctx = request.getContextPath();
        return ctx != null ? ctx : "";
    }
}
