package com.polycoffee.filter;

import com.polycoffee.entity.NhanVien;
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
import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Filter xac thuc & phan quyen chuan 2 Role noi bo: ADMIN (Quan ly) va STAFF (Nhan vien).
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

        String path = resolvePath(req);
        if (path == null) {
            sendForbidden(req, res, "Đường dẫn không hợp lệ (403)!");
            return;
        }

        NhanVien user = AuthUtil.getUser(req);
        if (user == null || Boolean.FALSE.equals(user.getDangHoatDong())) {
            if (user != null) {
                AuthUtil.clear(req);
            }
            req.getSession(true).setAttribute("REDIRECT_URL", buildFullRequestTarget(req, path));
            res.sendRedirect(safeContextPath(req) + "/dang-nhap");
            return;
        }

        String role = user.getVaiTro() != null ? user.getVaiTro().trim().toUpperCase() : "";
        if (!isStaffRoleAuthorized(role, path)) {
            sendForbidden(req, res, buildForbiddenMessage(role, path));
            return;
        }

        chain.doFilter(req, res);
    }

    private boolean isStaffRoleAuthorized(String role, String path) {
        if (path.equals("/manager") || path.startsWith("/manager/")) {
            return "ADMIN".equals(role);
        }
        if ("ADMIN".equals(role)) {
            return true;
        }
        if ("STAFF".equals(role)) {
            return path.equals("/employee") || path.startsWith("/employee/")
                    || path.equals("/pos") || path.startsWith("/pos/");
        }
        return false;
    }

    private String buildForbiddenMessage(String role, String path) {
        if (path.equals("/manager") || path.startsWith("/manager/")) {
            return "Tài khoản Nhân viên (" + role + ") không có quyền Quản lý (403) để truy cập khu vực /manager/*!";
        }
        return "Vai trò hiện tại không có quyền truy cập chức năng này (403)!";
    }

    private void sendForbidden(HttpServletRequest req, HttpServletResponse res, String message)
            throws IOException, ServletException {
        res.setStatus(HttpServletResponse.SC_FORBIDDEN);
        req.setAttribute("errorMessage", message);
        req.setAttribute("statusCode", HttpServletResponse.SC_FORBIDDEN);
        req.getRequestDispatcher("/views/error/403.jsp").forward(req, res);
    }

    private String resolvePath(HttpServletRequest req) {
        String servletPath = req.getServletPath() != null ? req.getServletPath() : "";
        String pathInfo = req.getPathInfo() != null ? req.getPathInfo() : "";
        String combined = (servletPath + pathInfo).replace('\\', '/');
        if (combined.indexOf('\0') >= 0) {
            return null;
        }
        Deque<String> segments = new ArrayDeque<>();
        for (String rawSegment : combined.split("/")) {
            int semiIdx = rawSegment.indexOf(';');
            String segment = semiIdx >= 0 ? rawSegment.substring(0, semiIdx) : rawSegment;
            if (segment.isEmpty() || ".".equals(segment)) {
                continue;
            }
            if ("..".equals(segment)) {
                if (segments.isEmpty()) {
                    return null;
                }
                segments.removeLast();
                continue;
            }
            segments.addLast(segment);
        }
        if (segments.isEmpty()) {
            return "/";
        }
        return "/" + String.join("/", segments);
    }

    private String buildFullRequestTarget(HttpServletRequest req, String path) {
        String ctx = safeContextPath(req);
        String query = req.getQueryString();
        return query != null && !query.isBlank() ? ctx + path + "?" + query : ctx + path;
    }

    private String safeContextPath(HttpServletRequest request) {
        String ctx = request.getContextPath();
        return ctx != null ? ctx : "";
    }
}
