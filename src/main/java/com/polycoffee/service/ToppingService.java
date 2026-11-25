package com.polycoffee.service;

import com.polycoffee.dao.ChiTietToppingDAO;
import com.polycoffee.dao.NhatKyHeThongDAO;
import com.polycoffee.dao.ToppingDAO;
import com.polycoffee.entity.NhanVien;
import com.polycoffee.entity.NhatKyHeThong;
import com.polycoffee.entity.Topping;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Tang Service xu ly nghiep vu CRUD Topping (TOPPING).
 * Validate gia, chong trung ma/ten, xu ly xoa mem neu da phat sinh don va ghi Audit Log.
 *
 * @author Quý (Nhóm PolyCoffee - JAV102)
 */
public class ToppingService {

    private static final Set<String> VALID_STATUSES = Set.of("AVAILABLE", "OUT_OF_STOCK", "INACTIVE");

    private final ToppingDAO toppingDAO;
    private final ChiTietToppingDAO ctToppingDAO;
    private final NhatKyHeThongDAO nhatKyDAO;

    public ToppingService() {
        this(new ToppingDAO(), new ChiTietToppingDAO(), new NhatKyHeThongDAO());
    }

    public ToppingService(ToppingDAO toppingDAO, ChiTietToppingDAO ctToppingDAO, NhatKyHeThongDAO nhatKyDAO) {
        this.toppingDAO = toppingDAO;
        this.ctToppingDAO = ctToppingDAO;
        this.nhatKyDAO = nhatKyDAO;
    }

    public List<Topping> findAll() {
        return toppingDAO.findAll();
    }

    public Topping findById(int id) {
        return id > 0 ? toppingDAO.findById(id) : null;
    }

    public ServiceResult<Topping> create(String maTopping, String tenTopping, String rawGia,
                                         String trangThai, NhanVien actor, String clientIp) {
        Long gia = parseNonNegativeLong(rawGia);
        String effectiveStatus = (trangThai == null || trangThai.isBlank())
                ? "AVAILABLE" : trangThai.trim().toUpperCase();
        String normalizedMa = (maTopping != null && !maTopping.isBlank())
                ? maTopping.trim().toUpperCase() : "";

        Topping draft = Topping.builder()
                .maTopping(normalizedMa)
                .tenTopping(tenTopping)
                .giaTopping(gia != null ? gia : 10000L)
                .hinhAnh("/assets/img/tp-default.jpg")
                .trangThai(VALID_STATUSES.contains(effectiveStatus) ? effectiveStatus : "AVAILABLE")
                .build();

        if (tenTopping == null || tenTopping.isBlank()) {
            return ServiceResult.fail("VALIDATION_ERROR", "Tên Topping không được để trống!", draft);
        }
        if (tenTopping.length() > 100) {
            return ServiceResult.fail("VALIDATION_ERROR", "Tên Topping tối đa 100 ký tự!", draft);
        }
        if (gia == null) {
            return ServiceResult.fail("VALIDATION_ERROR", "Giá Topping phải là số hợp lệ và >= 0!", draft);
        }
        if (!VALID_STATUSES.contains(effectiveStatus)) {
            return ServiceResult.fail("VALIDATION_ERROR", "Trạng thái Topping không hợp lệ!", draft);
        }
        if (!normalizedMa.isEmpty() && !normalizedMa.matches("^[A-Z0-9_-]{2,15}$")) {
            return ServiceResult.fail("VALIDATION_ERROR", "Mã Topping gồm 2-15 ký tự chữ hoa, số, '-' hoặc '_'!", draft);
        }
        if (normalizedMa.isEmpty()) {
            normalizedMa = generateUniqueCode();
            draft.setMaTopping(normalizedMa);
        } else if (toppingDAO.findByMaTopping(normalizedMa) != null) {
            return ServiceResult.fail("DUPLICATE_CODE", "Mã Topping '" + normalizedMa + "' đã tồn tại!", draft);
        }
        if (findByTenTopping(tenTopping) != null) {
            return ServiceResult.fail("DUPLICATE_NAME", "Tên Topping '" + tenTopping + "' đã tồn tại!", draft);
        }

        try {
            Topping tp = Topping.builder()
                    .maTopping(normalizedMa)
                    .tenTopping(tenTopping)
                    .giaTopping(gia)
                    .hinhAnh("/assets/img/tp-default.jpg")
                    .trangThai(effectiveStatus)
                    .build();
            int rs = toppingDAO.create(tp);
            if (rs > 0) {
                logAction(actor, "CREATE_TOPPING", normalizedMa, null,
                        "{\"ten_topping\":\"" + esc(tenTopping) + "\",\"gia_topping\":" + gia + ",\"trang_thai\":\"" + effectiveStatus + "\"}",
                        clientIp);
                Topping saved = toppingDAO.findByMaTopping(normalizedMa);
                return ServiceResult.ok("Thêm Topping thành công!", saved != null ? saved : tp);
            }
            return ServiceResult.fail("CREATE_FAILED", "Thêm Topping thất bại!", draft);
        } catch (Exception e) {
            return ServiceResult.fail("DB_ERROR", "Lỗi thêm Topping: " + e.getMessage(), draft);
        }
    }

    public ServiceResult<Topping> update(int id, String maTopping, String tenTopping, String rawGia,
                                         String trangThai, NhanVien actor, String clientIp) {
        Topping existing = id > 0 ? toppingDAO.findById(id) : null;
        if (existing == null) {
            return ServiceResult.fail("NOT_FOUND", "Không tìm thấy Topping!", null);
        }

        Long gia = parseNonNegativeLong(rawGia);
        String effectiveStatus = (trangThai == null || trangThai.isBlank())
                ? existing.getTrangThai() : trangThai.trim().toUpperCase();
        String targetMa = (maTopping != null && !maTopping.isBlank())
                ? maTopping.trim().toUpperCase() : existing.getMaTopping();

        Topping draft = Topping.builder()
                .idTopping(id)
                .maTopping(targetMa)
                .tenTopping(tenTopping)
                .giaTopping(gia != null ? gia : existing.getGiaTopping())
                .hinhAnh(existing.getHinhAnh())
                .trangThai(VALID_STATUSES.contains(effectiveStatus) ? effectiveStatus : existing.getTrangThai())
                .build();

        if (tenTopping == null || tenTopping.isBlank()) {
            return ServiceResult.fail("VALIDATION_ERROR", "Tên Topping không được để trống!", draft);
        }
        if (tenTopping.length() > 100) {
            return ServiceResult.fail("VALIDATION_ERROR", "Tên Topping tối đa 100 ký tự!", draft);
        }
        if (gia == null) {
            return ServiceResult.fail("VALIDATION_ERROR", "Giá Topping phải là số hợp lệ và >= 0!", draft);
        }
        if (!VALID_STATUSES.contains(effectiveStatus)) {
            return ServiceResult.fail("VALIDATION_ERROR", "Trạng thái Topping không hợp lệ!", draft);
        }
        Topping dupMa = toppingDAO.findByMaTopping(targetMa);
        if (dupMa != null && !dupMa.getIdTopping().equals(id)) {
            return ServiceResult.fail("DUPLICATE_CODE", "Mã Topping '" + targetMa + "' đã được sử dụng!", draft);
        }
        Topping dupTen = findByTenTopping(tenTopping);
        if (dupTen != null && !dupTen.getIdTopping().equals(id)) {
            return ServiceResult.fail("DUPLICATE_NAME", "Tên Topping '" + tenTopping + "' đã tồn tại!", draft);
        }

        String oldJson = "{\"ten_topping\":\"" + esc(existing.getTenTopping())
                + "\",\"gia_topping\":" + existing.getGiaTopping()
                + ",\"trang_thai\":\"" + esc(existing.getTrangThai()) + "\"}";
        existing.setMaTopping(targetMa);
        existing.setTenTopping(tenTopping);
        existing.setGiaTopping(gia);
        existing.setTrangThai(effectiveStatus);

        try {
            int rs = toppingDAO.update(existing);
            if (rs > 0) {
                logAction(actor, "UPDATE_TOPPING", existing.getMaTopping(), oldJson,
                        "{\"ten_topping\":\"" + esc(tenTopping) + "\",\"gia_topping\":" + gia + ",\"trang_thai\":\"" + effectiveStatus + "\"}",
                        clientIp);
                return ServiceResult.ok("Cập nhật Topping thành công!", existing);
            }
            return ServiceResult.fail("UPDATE_FAILED", "Cập nhật Topping thất bại!", existing);
        } catch (Exception e) {
            return ServiceResult.fail("DB_ERROR", "Lỗi cập nhật Topping: " + e.getMessage(), existing);
        }
    }

    public ServiceResult<Void> deleteOrDeactivate(int id, NhanVien actor, String clientIp) {
        Topping tp = id > 0 ? toppingDAO.findById(id) : null;
        if (tp == null) {
            return ServiceResult.fail("NOT_FOUND", "Không tìm thấy Topping cần xóa!", null);
        }

        boolean usedInOrders = !ctToppingDAO.findBySql(
                "SELECT * FROM dbo.CHI_TIET_TOPPING WHERE id_topping = ?", id).isEmpty();
        if (usedInOrders) {
            String oldJson = "{\"trang_thai\":\"" + esc(tp.getTrangThai()) + "\"}";
            tp.setTrangThai("INACTIVE");
            toppingDAO.update(tp);
            logAction(actor, "DEACTIVATE_TOPPING", tp.getMaTopping(), oldJson,
                    "{\"trang_thai\":\"INACTIVE\"}", clientIp);
            return ServiceResult.ok("SOFT_DELETED",
                    "Topping đã phát sinh giao dịch, hệ thống đã chuyển sang INACTIVE!", null);
        }

        String oldJson = "{\"ten_topping\":\"" + esc(tp.getTenTopping())
                + "\",\"gia_topping\":" + tp.getGiaTopping() + "}";
        int rs = toppingDAO.delete(id);
        if (rs > 0) {
            logAction(actor, "DELETE_TOPPING", tp.getMaTopping(), oldJson, null, clientIp);
            return ServiceResult.ok("HARD_DELETED", "Đã xóa Topping thành công!", null);
        }
        return ServiceResult.fail("DELETE_FAILED", "Xóa Topping thất bại!", null);
    }

    private Topping findByTenTopping(String tenTopping) {
        List<Topping> found = toppingDAO.findBySql("SELECT * FROM dbo.TOPPING WHERE ten_topping = ?", tenTopping);
        return found.isEmpty() ? null : found.get(0);
    }

    private String generateUniqueCode() {
        for (int i = 0; i < 10; i++) {
            String candidate = "TP" + (10000 + ThreadLocalRandom.current().nextInt(90000));
            if (toppingDAO.findByMaTopping(candidate) == null) {
                return candidate;
            }
        }
        return "TP" + (System.currentTimeMillis() % 100000);
    }

    private Long parseNonNegativeLong(String raw) {
        if (raw == null || raw.isBlank()) {
            return null;
        }
        try {
            long val = Long.parseLong(raw.trim());
            return val >= 0 ? val : null;
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private void logAction(NhanVien actor, String action, String maBanGhi, String cu, String moi, String ip) {
        try {
            if (actor != null) {
                nhatKyDAO.create(NhatKyHeThong.builder()
                        .idNhanVien(actor.getIdNhanVien())
                        .loaiHanhDong(action)
                        .bangTacDong("TOPPING")
                        .maBanGhi(maBanGhi)
                        .duLieuCu(cu)
                        .duLieuMoi(moi)
                        .diaChiIp(ip)
                        .thoiGian(new Date())
                        .build());
            }
        } catch (Exception ignored) {
        }
    }

    private String esc(String s) {
        return s == null ? "" : s.replace("\\", "\\\\").replace("\"", "\\\"");
    }
}
