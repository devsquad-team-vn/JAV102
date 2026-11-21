package com.polycoffee.servlet;

import com.polycoffee.dao.NhanVienDAO;
import com.polycoffee.dao.NhatKyHeThongDAO;
import com.polycoffee.entity.NhanVien;
import com.polycoffee.entity.NhatKyHeThong;
import com.polycoffee.util.AuthUtil;
import com.polycoffee.util.ParamUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Date;

/**
 * Servlet xu ly Dang nhap, Dang xuat, Khoa Brute-Force va ghi Nhat ky he thong (Lab 4 - Bai 3).
 *
 * @author Nam (Nhóm PolyCoffee - JAV102)
 */
@WebServlet({"/dang-nhap", "/dang-xuat", "/staff/login", "/staff/logout"})
public class AuthServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final int MAX_FAILED_ATTEMPTS = 5;
    private static final long LOCK_DURATION_MS = 15L * 60 * 1000;

    private final NhanVienDAO nhanVienDAO = new NhanVienDAO();
    private final NhatKyHeThongDAO nhatKyDAO = new NhatKyHeThongDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String uri = request.getRequestURI();
        if (uri.endsWith("/dang-xuat") || uri.endsWith("/staff/logout")) {
            NhanVien current = AuthUtil.getUser(request);
            if (current != null) {
                logAuth(current.getIdNhanVien(), "LOGOUT", current.getMaNhanVien(), request.getRemoteAddr());
            }
            AuthUtil.clear(request);
            HttpSession session = request.getSession(false);
            if (session != null) {
                session.invalidate();
            }
            response.sendRedirect(request.getContextPath() + "/dang-nhap");
            return;
        }
        NhanVien existing = AuthUtil.getUser(request);
        if (existing != null) {
            response.sendRedirect(request.getContextPath() + defaultPathForRole(existing));
            return;
        }
        request.getRequestDispatcher("/views/auth/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String email = ParamUtil.getString(request, "email");
        String password = ParamUtil.getString(request, "password");

        if (email == null || email.isBlank() || password == null || password.isBlank()) {
            request.setAttribute("message", "Vui long nhap day du Email/Ten dang nhap va Mat khau!");
            request.getRequestDispatcher("/views/auth/login.jsp").forward(request, response);
            return;
        }

        NhanVien user = nhanVienDAO.findByEmail(email);
        if (user == null) {
            request.setAttribute("message", "Tai khoan khong dung!");
            request.getRequestDispatcher("/views/auth/login.jsp").forward(request, response);
            return;
        }

        if (Boolean.FALSE.equals(user.getDangHoatDong())) {
            request.setAttribute("message", "Tai khoan da bi khoa!");
            request.getRequestDispatcher("/views/auth/login.jsp").forward(request, response);
            return;
        }

        Date now = new Date();
        if (user.getKhoaDenLuc() != null && user.getKhoaDenLuc().after(now)) {
            long minsLeft = Math.max(1, (user.getKhoaDenLuc().getTime() - now.getTime() + 59999) / 60000);
            request.setAttribute("message", "Tai khoan dang bi tam khoa do sai mat khau nhieu lan. Thu lai sau " + minsLeft + " phut!");
            request.getRequestDispatcher("/views/auth/login.jsp").forward(request, response);
            return;
        }

        if (!AuthUtil.matchesPassword(password, user.getMatKhauBam())) {
            int fails = (user.getSoLanSaiMk() != null ? user.getSoLanSaiMk() : 0) + 1;
            user.setSoLanSaiMk(fails);
            if (fails >= MAX_FAILED_ATTEMPTS) {
                user.setKhoaDenLuc(new Date(System.currentTimeMillis() + LOCK_DURATION_MS));
                nhanVienDAO.update(user);
                request.setAttribute("message", "Ban da nhap sai qua " + MAX_FAILED_ATTEMPTS + " lan. Tai khoan bi tam khoa 15 phut!");
            } else {
                nhanVienDAO.update(user);
                request.setAttribute("message", "Mat khau khong dung! (" + fails + "/" + MAX_FAILED_ATTEMPTS + ")");
            }
            request.getRequestDispatcher("/views/auth/login.jsp").forward(request, response);
            return;
        }

        // Dang nhap thanh cong -> reset bo dem sai MK va nang cap hash mau sang SHA-256
        boolean needUpdate = (user.getSoLanSaiMk() != null && user.getSoLanSaiMk() > 0) || user.getKhoaDenLuc() != null;
        if (user.getMatKhauBam() != null && user.getMatKhauBam().startsWith("$2a$10$PolyCoffee")) {
            user.setMatKhauBam(AuthUtil.hashSha256(password));
            needUpdate = true;
        }
        if (needUpdate) {
            user.setSoLanSaiMk(0);
            user.setKhoaDenLuc(null);
            nhanVienDAO.update(user);
        }

        AuthUtil.setUser(request, user);
        logAuth(user.getIdNhanVien(), "LOGIN", user.getMaNhanVien(), request.getRemoteAddr());

        HttpSession session = request.getSession(false);
        String redirectUrl = request.getContextPath() + defaultPathForRole(user);
        if (session != null && session.getAttribute("REDIRECT_URL") != null) {
            redirectUrl = (String) session.getAttribute("REDIRECT_URL");
            session.removeAttribute("REDIRECT_URL");
        }
        response.sendRedirect(redirectUrl);
    }

    private String defaultPathForRole(NhanVien user) {
        if (user != null && "ADMIN".equalsIgnoreCase(user.getVaiTro())) {
            return "/manager/categories";
        }
        return "/trang-chu";
    }

    private void logAuth(Integer idNhanVien, String loaiHanhDong, String maBanGhi, String ip) {
        try {
            nhatKyDAO.create(NhatKyHeThong.builder()
                    .idNhanVien(idNhanVien)
                    .loaiHanhDong(loaiHanhDong)
                    .bangTacDong("NHAN_VIEN")
                    .maBanGhi(maBanGhi)
                    .duLieuMoi("{\"trang_thai\":\"" + loaiHanhDong + "\"}")
                    .diaChiIp(ip)
                    .thoiGian(new Date())
                    .build());
        } catch (Exception ignored) {
        }
    }
}
