package com.polycoffee.util;

import com.polycoffee.entity.NhanVien;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Lop tien ich quan ly trang thai dang nhap, vai tro trong Session va bam mat khau (Lab 4 - Bai 1).
 *
 * @author Nam (Nhóm PolyCoffee - JAV102)
 */
public class AuthUtil {
    public static final String SESSION_USER = "user";

    public static void setUser(HttpServletRequest request, NhanVien user) {
        HttpSession session = request.getSession(true);
        session.setAttribute(SESSION_USER, user);
    }

    public static NhanVien getUser(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return null;
        }
        return (NhanVien) session.getAttribute(SESSION_USER);
    }

    public static boolean isAuthenticated(HttpServletRequest request) {
        return getUser(request) != null;
    }

    public static boolean isManager(HttpServletRequest request) {
        NhanVien u = getUser(request);
        return u != null && "ADMIN".equalsIgnoreCase(u.getVaiTro());
    }

    public static void clear(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.removeAttribute(SESSION_USER);
        }
    }

    public static String hashSha256(String rawPassword) {
        if (rawPassword == null) {
            return "";
        }
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] bytes = md.digest(rawPassword.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (byte b : bytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Khong ho tro SHA-256", e);
        }
    }

    public static boolean matchesPassword(String rawPassword, String storedHash) {
        if (rawPassword == null || storedHash == null || rawPassword.isBlank() || storedHash.isBlank()) {
            return false;
        }
        // Chan tan cong Pass-the-Hash (gui truc tiep ma bam thay mat khau goc)
        if (rawPassword.startsWith("$2a$") || rawPassword.matches("^[0-9a-fA-F]{64}$")) {
            return false;
        }
        if (storedHash.equalsIgnoreCase(hashSha256(rawPassword))) {
            return true;
        }
        // Ho tro tai khoan mau PolyCoffee_DB.sql o lan dang nhap dau tien (sau do Servlet tu nang cap SHA-256)
        if (storedHash.startsWith("$2a$10$PolyCoffee") && ("123456".equals(rawPassword) || "admin123".equals(rawPassword))) {
            return true;
        }
        // Tuong thich chuoi ngan plaintext cu neu co (khong ap dung cho hash)
        return storedHash.length() < 32 && !storedHash.startsWith("$2a$") && storedHash.equals(rawPassword);
    }
}
