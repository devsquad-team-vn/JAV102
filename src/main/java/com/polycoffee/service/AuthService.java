package com.polycoffee.service;

import com.polycoffee.dao.NhanVienDAO;
import com.polycoffee.dao.NhatKyHeThongDAO;
import com.polycoffee.entity.NhanVien;
import com.polycoffee.entity.NhatKyHeThong;
import com.polycoffee.util.AuthUtil;

import java.util.Date;

/**
 * Tang Service xu ly nghiep vu Xac thuc, Khoa Brute-Force, Nang cap Hash & Audit Log.
 * Luong: AuthServlet -> AuthService -> NhanVienDAO/NhatKyHeThongDAO -> DB.
 *
 * @author Nam (Nhóm PolyCoffee - JAV102)
 */
public class AuthService {

    public static final int MAX_FAILED_ATTEMPTS = 5;
    public static final long LOCK_DURATION_MS = 15L * 60 * 1000;

    private final NhanVienDAO nhanVienDAO;
    private final NhatKyHeThongDAO nhatKyDAO;

    public AuthService() {
        this(new NhanVienDAO(), new NhatKyHeThongDAO());
    }

    public AuthService(NhanVienDAO nhanVienDAO, NhatKyHeThongDAO nhatKyDAO) {
        this.nhanVienDAO = nhanVienDAO;
        this.nhatKyDAO = nhatKyDAO;
    }

    public ServiceResult<NhanVien> authenticate(String identity, String password, String clientIp) {
        if (identity == null || identity.isBlank() || password == null || password.isBlank()) {
            return ServiceResult.fail("EMPTY_INPUT",
                    "Vui lòng nhập đầy đủ Email/Tên đăng nhập và Mật khẩu!", null);
        }

        String cleanIdentity = identity.trim();
        NhanVien user = nhanVienDAO.findByEmail(cleanIdentity);
        if (user == null) {
            return ServiceResult.fail("INVALID_CREDENTIALS",
                    "Tài khoản hoặc mật khẩu không đúng!", null);
        }

        if (Boolean.FALSE.equals(user.getDangHoatDong())) {
            logAuth(user.getIdNhanVien(), "LOGIN_FAILED", user.getMaNhanVien(), clientIp);
            return ServiceResult.fail("ACCOUNT_DISABLED",
                    "Tài khoản đã bị khóa!", null);
        }

        Date now = new Date();
        boolean lockExpired = false;
        if (user.getKhoaDenLuc() != null) {
            if (user.getKhoaDenLuc().after(now)) {
                logAuth(user.getIdNhanVien(), "LOGIN_FAILED_LOCKED", user.getMaNhanVien(), clientIp);
                long minsLeft = Math.max(1, (user.getKhoaDenLuc().getTime() - now.getTime() + 59999) / 60000);
                return ServiceResult.fail("ACCOUNT_LOCKED",
                        "Tài khoản đang bị tạm khóa do sai mật khẩu nhiều lần. Thử lại sau " + minsLeft + " phút!",
                        null);
            }
            lockExpired = true;
        }

        if (!AuthUtil.matchesPassword(password, user.getMatKhauBam())) {
            int priorFails = lockExpired ? 0 : (user.getSoLanSaiMk() != null ? user.getSoLanSaiMk() : 0);
            int fails = priorFails + 1;
            user.setSoLanSaiMk(fails);
            if (fails >= MAX_FAILED_ATTEMPTS) {
                user.setKhoaDenLuc(new Date(System.currentTimeMillis() + LOCK_DURATION_MS));
                nhanVienDAO.update(user);
                logAuth(user.getIdNhanVien(), "LOGIN_LOCKED", user.getMaNhanVien(), clientIp);
                return ServiceResult.fail("ACCOUNT_LOCKED",
                        "Bạn đã nhập sai quá " + MAX_FAILED_ATTEMPTS + " lần. Tài khoản bị tạm khóa 15 phút!",
                        null);
            }
            if (lockExpired) {
                user.setKhoaDenLuc(null);
            }
            nhanVienDAO.update(user);
            logAuth(user.getIdNhanVien(), "LOGIN_FAILED", user.getMaNhanVien(), clientIp);
            return ServiceResult.fail("INVALID_CREDENTIALS",
                    "Mật khẩu không đúng! (" + fails + "/" + MAX_FAILED_ATTEMPTS + ")", null);
        }

        // Reset bo dem sai mat khau va nang cap hash mau tu PolyCoffee_DB.sql sang SHA-256
        boolean needUpdate = (user.getSoLanSaiMk() != null && user.getSoLanSaiMk() > 0)
                || user.getKhoaDenLuc() != null;
        if (user.getMatKhauBam() != null && user.getMatKhauBam().startsWith("$2a$10$PolyCoffee")) {
            user.setMatKhauBam(AuthUtil.hashSha256(password));
            needUpdate = true;
        }
        if (needUpdate) {
            user.setSoLanSaiMk(0);
            user.setKhoaDenLuc(null);
            nhanVienDAO.update(user);
        }

        logAuth(user.getIdNhanVien(), "LOGIN", user.getMaNhanVien(), clientIp);
        return ServiceResult.ok("Đăng nhập thành công!", user);
    }

    public void recordLogout(NhanVien user, String clientIp) {
        if (user != null) {
            logAuth(user.getIdNhanVien(), "LOGOUT", user.getMaNhanVien(), clientIp);
        }
    }

    public String resolveLandingPath(NhanVien user) {
        if (user != null && "ADMIN".equalsIgnoreCase(user.getVaiTro())) {
            return "/manager/categories";
        }
        return "/employee/workspace";
    }

    private void logAuth(Integer idNhanVien, String loaiHanhDong, String maBanGhi, String ip) {
        try {
            if (idNhanVien != null) {
                nhatKyDAO.create(NhatKyHeThong.builder()
                        .idNhanVien(idNhanVien)
                        .loaiHanhDong(loaiHanhDong)
                        .bangTacDong("NHAN_VIEN")
                        .maBanGhi(maBanGhi)
                        .duLieuMoi("{\"trang_thai\":\"" + loaiHanhDong + "\"}")
                        .diaChiIp(ip)
                        .thoiGian(new Date())
                        .build());
            }
        } catch (Exception ignored) {
        }
    }
}
