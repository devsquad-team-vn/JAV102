package com.polycoffee.service;

import com.polycoffee.dao.DanhMucDAO;
import com.polycoffee.dao.NhatKyHeThongDAO;
import com.polycoffee.entity.DanhMuc;
import com.polycoffee.entity.NhanVien;
import com.polycoffee.entity.NhatKyHeThong;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Tang Service xu ly nghiep vu CRUD Loai do uong DANH_MUC.
 * Validate du lieu Server-side, chong trung ma/ten, xu ly xoa cung / xoa mem va ghi Audit Log.
 *
 * @author Kiệt (Nhóm PolyCoffee - JAV102)
 */
public class CategoryService {

    private final DanhMucDAO danhMucDAO;
    private final NhatKyHeThongDAO nhatKyDAO;

    public CategoryService() {
        this(new DanhMucDAO(), new NhatKyHeThongDAO());
    }

    public CategoryService(DanhMucDAO danhMucDAO, NhatKyHeThongDAO nhatKyDAO) {
        this.danhMucDAO = danhMucDAO;
        this.nhatKyDAO = nhatKyDAO;
    }

    public List<DanhMuc> findAll() {
        return danhMucDAO.findAll();
    }

    public DanhMuc findById(int id) {
        return id > 0 ? danhMucDAO.findById(id) : null;
    }

    public Map<Integer, Integer> getDrinkCounts(List<DanhMuc> list) {
        Map<Integer, Integer> drinkCounts = new HashMap<>();
        if (list != null) {
            for (DanhMuc dm : list) {
                drinkCounts.put(dm.getIdDanhMuc(), danhMucDAO.countDrinkInCategory(dm.getIdDanhMuc()));
            }
        }
        return drinkCounts;
    }

    public ServiceResult<DanhMuc> create(String maDanhMuc, String tenDanhMuc, String moTa,
                                         String rawThuTu, String trangThai, NhanVien actor, String clientIp) {
        Integer thuTu = parseNonNegativeInt(rawThuTu, 1);
        String effectiveStatus = (trangThai == null || trangThai.isBlank())
                ? "ACTIVE" : trangThai.trim().toUpperCase();
        String normalizedMa = (maDanhMuc != null && !maDanhMuc.isBlank())
                ? maDanhMuc.trim().toUpperCase() : "";

        DanhMuc draft = DanhMuc.builder()
                .maDanhMuc(normalizedMa)
                .tenDanhMuc(tenDanhMuc)
                .moTa(moTa)
                .thuTuHienThi(thuTu != null ? thuTu : 1)
                .trangThai("INACTIVE".equals(effectiveStatus) ? "INACTIVE" : "ACTIVE")
                .build();

        if (tenDanhMuc == null || tenDanhMuc.isBlank()) {
            return ServiceResult.fail("VALIDATION_ERROR", "Tên loại đồ uống không được để trống!", draft);
        }
        if (tenDanhMuc.length() > 100) {
            return ServiceResult.fail("VALIDATION_ERROR", "Tên loại đồ uống tối đa 100 ký tự!", draft);
        }
        if (moTa != null && moTa.length() > 300) {
            return ServiceResult.fail("VALIDATION_ERROR", "Mô tả danh mục tối đa 300 ký tự!", draft);
        }
        if (thuTu == null) {
            return ServiceResult.fail("VALIDATION_ERROR", "Thứ tự hiển thị phải là số nguyên >= 0!", draft);
        }
        if (!"ACTIVE".equals(effectiveStatus) && !"INACTIVE".equals(effectiveStatus)) {
            return ServiceResult.fail("VALIDATION_ERROR", "Trạng thái danh mục không hợp lệ!", draft);
        }
        if (!normalizedMa.isEmpty() && !normalizedMa.matches("^[A-Z0-9_-]{2,15}$")) {
            return ServiceResult.fail("VALIDATION_ERROR", "Mã danh mục gồm 2-15 ký tự chữ hoa, số, '-' hoặc '_'!", draft);
        }
        if (normalizedMa.isEmpty()) {
            normalizedMa = generateUniqueCode();
            draft.setMaDanhMuc(normalizedMa);
        } else if (danhMucDAO.findByMaDanhMuc(normalizedMa) != null) {
            return ServiceResult.fail("DUPLICATE_CODE", "Mã danh mục '" + normalizedMa + "' đã tồn tại!", draft);
        }

        if (findByTenDanhMuc(tenDanhMuc) != null) {
            return ServiceResult.fail("DUPLICATE_NAME", "Tên danh mục '" + tenDanhMuc + "' đã tồn tại!", draft);
        }

        try {
            DanhMuc dm = DanhMuc.builder()
                    .maDanhMuc(normalizedMa)
                    .tenDanhMuc(tenDanhMuc)
                    .moTa(moTa)
                    .thuTuHienThi(thuTu)
                    .trangThai(effectiveStatus)
                    .build();
            int rs = danhMucDAO.create(dm);
            if (rs > 0) {
                logAction(actor, "CREATE_CATEGORY", normalizedMa, null,
                        "{\"ten_danh_muc\":\"" + esc(tenDanhMuc) + "\",\"trang_thai\":\"" + effectiveStatus + "\"}",
                        clientIp);
                DanhMuc saved = danhMucDAO.findByMaDanhMuc(normalizedMa);
                return ServiceResult.ok("Thêm mới danh mục thành công!", saved != null ? saved : dm);
            }
            return ServiceResult.fail("CREATE_FAILED", "Thêm mới danh mục thất bại!", draft);
        } catch (Exception e) {
            return ServiceResult.fail("DB_ERROR", "Không thể thêm danh mục: " + e.getMessage(), draft);
        }
    }

    public ServiceResult<DanhMuc> update(int id, String maDanhMuc, String tenDanhMuc, String moTa,
                                         String rawThuTu, String trangThai, NhanVien actor, String clientIp) {
        DanhMuc existing = id > 0 ? danhMucDAO.findById(id) : null;
        if (existing == null) {
            return ServiceResult.fail("NOT_FOUND", "Loại đồ uống không tồn tại!", null);
        }

        Integer thuTu = parseNonNegativeInt(rawThuTu, existing.getThuTuHienThi());
        String effectiveStatus = (trangThai == null || trangThai.isBlank())
                ? existing.getTrangThai() : trangThai.trim().toUpperCase();
        String targetMa = (maDanhMuc != null && !maDanhMuc.isBlank())
                ? maDanhMuc.trim().toUpperCase() : existing.getMaDanhMuc();

        DanhMuc draft = DanhMuc.builder()
                .idDanhMuc(id)
                .maDanhMuc(targetMa)
                .tenDanhMuc(tenDanhMuc)
                .moTa(moTa)
                .thuTuHienThi(thuTu != null ? thuTu : existing.getThuTuHienThi())
                .trangThai("INACTIVE".equals(effectiveStatus) ? "INACTIVE" : "ACTIVE")
                .build();

        if (tenDanhMuc == null || tenDanhMuc.isBlank()) {
            return ServiceResult.fail("VALIDATION_ERROR", "Tên danh mục không được để trống!", draft);
        }
        if (tenDanhMuc.length() > 100) {
            return ServiceResult.fail("VALIDATION_ERROR", "Tên danh mục tối đa 100 ký tự!", draft);
        }
        if (moTa != null && moTa.length() > 300) {
            return ServiceResult.fail("VALIDATION_ERROR", "Mô tả danh mục tối đa 300 ký tự!", draft);
        }
        if (thuTu == null) {
            return ServiceResult.fail("VALIDATION_ERROR", "Thứ tự hiển thị phải là số nguyên >= 0!", draft);
        }
        if (!"ACTIVE".equals(effectiveStatus) && !"INACTIVE".equals(effectiveStatus)) {
            return ServiceResult.fail("VALIDATION_ERROR", "Trạng thái danh mục không hợp lệ!", draft);
        }
        DanhMuc dupMa = danhMucDAO.findByMaDanhMuc(targetMa);
        if (dupMa != null && !dupMa.getIdDanhMuc().equals(id)) {
            return ServiceResult.fail("DUPLICATE_CODE", "Mã danh mục '" + targetMa + "' đã được sử dụng!", draft);
        }
        DanhMuc dupTen = findByTenDanhMuc(tenDanhMuc);
        if (dupTen != null && !dupTen.getIdDanhMuc().equals(id)) {
            return ServiceResult.fail("DUPLICATE_NAME", "Tên danh mục '" + tenDanhMuc + "' đã tồn tại!", draft);
        }

        String oldJson = "{\"ten_danh_muc\":\"" + esc(existing.getTenDanhMuc())
                + "\",\"trang_thai\":\"" + esc(existing.getTrangThai()) + "\"}";
        existing.setMaDanhMuc(targetMa);
        existing.setTenDanhMuc(tenDanhMuc);
        existing.setMoTa(moTa);
        existing.setThuTuHienThi(thuTu);
        existing.setTrangThai(effectiveStatus);

        try {
            int rs = danhMucDAO.update(existing);
            if (rs > 0) {
                logAction(actor, "UPDATE_CATEGORY", existing.getMaDanhMuc(), oldJson,
                        "{\"ten_danh_muc\":\"" + esc(tenDanhMuc) + "\",\"trang_thai\":\"" + effectiveStatus + "\"}",
                        clientIp);
                return ServiceResult.ok("Cập nhật danh mục thành công!", existing);
            }
            return ServiceResult.fail("UPDATE_FAILED", "Cập nhật danh mục thất bại!", existing);
        } catch (Exception e) {
            return ServiceResult.fail("DB_ERROR", "Lỗi cập nhật danh mục: " + e.getMessage(), existing);
        }
    }

    public ServiceResult<Void> deleteOrDeactivate(int id, NhanVien actor, String clientIp) {
        DanhMuc category = id > 0 ? danhMucDAO.findById(id) : null;
        if (category == null) {
            return ServiceResult.fail("NOT_FOUND", "Không tìm thấy loại đồ uống cần xóa!", null);
        }

        int countDrink = danhMucDAO.countDrinkInCategory(id);
        if (countDrink > 0) {
            String oldStatus = category.getTrangThai();
            category.setTrangThai("INACTIVE");
            danhMucDAO.update(category);
            logAction(actor, "DEACTIVATE_CATEGORY", category.getMaDanhMuc(),
                    "{\"trang_thai\":\"" + esc(oldStatus) + "\"}",
                    "{\"trang_thai\":\"INACTIVE\"}",
                    clientIp);
            return ServiceResult.ok("SOFT_DELETED",
                    "Danh mục đang có " + countDrink + " đồ uống, hệ thống đã chuyển sang INACTIVE để bảo toàn dữ liệu!",
                    null);
        }

        int rs = danhMucDAO.delete(id);
        if (rs > 0) {
            logAction(actor, "DELETE_CATEGORY", category.getMaDanhMuc(),
                    "{\"ten_danh_muc\":\"" + esc(category.getTenDanhMuc()) + "\"}",
                    null,
                    clientIp);
            return ServiceResult.ok("HARD_DELETED", "Đã xóa vĩnh viễn danh mục trống thành công!", null);
        }
        return ServiceResult.fail("DELETE_FAILED", "Xóa danh mục thất bại!", null);
    }

    private DanhMuc findByTenDanhMuc(String tenDanhMuc) {
        List<DanhMuc> found = danhMucDAO.findBySql("SELECT * FROM dbo.DANH_MUC WHERE ten_danh_muc = ?", tenDanhMuc);
        return found.isEmpty() ? null : found.get(0);
    }

    private String generateUniqueCode() {
        for (int i = 0; i < 10; i++) {
            String candidate = "DM" + (10000 + ThreadLocalRandom.current().nextInt(90000));
            if (danhMucDAO.findByMaDanhMuc(candidate) == null) {
                return candidate;
            }
        }
        return "DM" + (System.currentTimeMillis() % 100000);
    }

    private Integer parseNonNegativeInt(String raw, Integer defaultIfBlank) {
        if (raw == null || raw.isBlank()) {
            return defaultIfBlank;
        }
        try {
            int val = Integer.parseInt(raw.trim());
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
                        .bangTacDong("DANH_MUC")
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
