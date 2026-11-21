package com.polycoffee.servlet;

import com.polycoffee.dao.DanhMucDAO;
import com.polycoffee.dao.NhatKyHeThongDAO;
import com.polycoffee.entity.DanhMuc;
import com.polycoffee.entity.NhanVien;
import com.polycoffee.entity.NhatKyHeThong;
import com.polycoffee.util.AuthUtil;
import com.polycoffee.util.ParamUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Servlet xử lý các chức năng quản lý danh mục loại đồ uống (Lab 4 - Bài 4).
 * Hỗ trợ CRUD, đếm số lượng món và xóa thông minh (chuyển INACTIVE nếu đã có đồ uống).
 *
 * @author Kiệt (Nhóm PolyCoffee - JAV102)
 */
@WebServlet({"/manager/categories", "/manager/categories/add", "/manager/categories/edit", "/manager/categories/delete"})
public class CategoryServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private final DanhMucDAO danhMucDAO = new DanhMucDAO();
    private final NhatKyHeThongDAO nhatKyDAO = new NhatKyHeThongDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = ParamUtil.getInt(request, "id");
        if (id > 0) {
            DanhMuc category = danhMucDAO.findById(id);
            request.setAttribute("category", category);
        }
        loadCategories(request);
        request.getRequestDispatcher("/views/categories/list.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String uri = request.getRequestURI();

        if (uri.contains("/add")) {
            createCategory(request);
        } else if (uri.contains("/edit")) {
            updateCategory(request);
        } else if (uri.contains("/delete")) {
            deleteCategory(request);
        }

        loadCategories(request);
        request.getRequestDispatcher("/views/categories/list.jsp").forward(request, response);
    }

    /**
     * Tải danh sách danh mục và số lượng món tương ứng của từng danh mục
     */
    private void loadCategories(HttpServletRequest request) {
        List<DanhMuc> list = danhMucDAO.findAll();
        Map<Integer, Integer> drinkCounts = new HashMap<>();
        for (DanhMuc dm : list) {
            drinkCounts.put(dm.getIdDanhMuc(), danhMucDAO.countDrinkInCategory(dm.getIdDanhMuc()));
        }
        request.setAttribute("list", list);
        request.setAttribute("drinkCounts", drinkCounts);
    }

    /**
     * Thêm mới danh mục loại đồ uống
     */
    private void createCategory(HttpServletRequest request) {
        String ma = ParamUtil.getString(request, "maDanhMuc");
        String name = ParamUtil.getString(request, "tenDanhMuc");
        String moTa = ParamUtil.getString(request, "moTa");
        int thuTu = Math.max(0, ParamUtil.getInt(request, "thuTuHienThi"));
        String trangThai = ParamUtil.getString(request, "trangThai");

        // Validate dữ liệu đầu vào
        if (name == null || name.isBlank()) {
            request.setAttribute("error", "Tên loại đồ uống không được để trống!");
            return;
        }

        // Tự sinh mã nếu để trống
        if (ma == null || ma.isBlank()) {
            ma = "DM" + (System.currentTimeMillis() % 100000);
        }

        if (trangThai == null || (!"ACTIVE".equals(trangThai) && !"INACTIVE".equals(trangThai))) {
            trangThai = "ACTIVE";
        }

        // Kiểm tra trùng mã danh mục
        if (danhMucDAO.findByMaDanhMuc(ma) != null) {
            request.setAttribute("error", "Mã danh mục '" + ma + "' đã tồn tại, vui lòng chọn mã khác!");
            return;
        }

        try {
            DanhMuc dm = DanhMuc.builder()
                    .maDanhMuc(ma)
                    .tenDanhMuc(name)
                    .moTa(moTa)
                    .thuTuHienThi(thuTu)
                    .trangThai(trangThai)
                    .build();

            int result = danhMucDAO.create(dm);
            if (result > 0) {
                logAction(request, "CREATE_CATEGORY", ma, null, "Thêm mới danh mục: " + name);
                request.setAttribute("message", "Thêm mới danh mục thành công!");
            } else {
                request.setAttribute("error", "Thêm mới danh mục thất bại!");
            }
        } catch (Exception e) {
            request.setAttribute("error", "Lỗi khi thêm danh mục: " + e.getMessage());
        }
    }

    /**
     * Cập nhật thông tin danh mục loại đồ uống
     */
    private void updateCategory(HttpServletRequest request) {
        int id = ParamUtil.getInt(request, "idDanhMuc");
        String ma = ParamUtil.getString(request, "maDanhMuc");
        String name = ParamUtil.getString(request, "tenDanhMuc");
        String moTa = ParamUtil.getString(request, "moTa");
        int thuTu = Math.max(0, ParamUtil.getInt(request, "thuTuHienThi"));
        String trangThai = ParamUtil.getString(request, "trangThai");

        DanhMuc category = danhMucDAO.findById(id);
        if (category == null) {
            request.setAttribute("error", "Loại đồ uống không tồn tại hoặc đã bị xóa!");
            return;
        }

        if (name == null || name.isBlank()) {
            request.setAttribute("error", "Tên loại đồ uống không được để trống!");
            request.setAttribute("category", category);
            return;
        }

        String oldInfo = "Tên: " + category.getTenDanhMuc() + ", Trạng thái: " + category.getTrangThai();

        if (ma != null && !ma.isBlank()) {
            category.setMaDanhMuc(ma);
        }
        category.setTenDanhMuc(name);
        category.setMoTa(moTa);
        category.setThuTuHienThi(thuTu);
        if ("ACTIVE".equals(trangThai) || "INACTIVE".equals(trangThai)) {
            category.setTrangThai(trangThai);
        }

        try {
            int result = danhMucDAO.update(category);
            if (result > 0) {
                String newInfo = "Tên: " + name + ", Trạng thái: " + category.getTrangThai();
                logAction(request, "UPDATE_CATEGORY", category.getMaDanhMuc(), oldInfo, newInfo);
                request.setAttribute("message", "Cập nhật danh mục thành công!");
            } else {
                request.setAttribute("error", "Cập nhật danh mục thất bại!");
            }
            request.setAttribute("category", category);
        } catch (Exception e) {
            request.setAttribute("error", "Lỗi khi cập nhật danh mục: " + e.getMessage());
            request.setAttribute("category", category);
        }
    }

    /**
     * Xóa danh mục - áp dụng xóa thông minh (chuyển sang INACTIVE nếu danh mục đã có đồ uống)
     */
    private void deleteCategory(HttpServletRequest request) {
        int id = ParamUtil.getInt(request, "idDanhMuc");
        if (id <= 0) {
            id = ParamUtil.getInt(request, "id");
        }

        DanhMuc category = id > 0 ? danhMucDAO.findById(id) : null;
        if (category == null) {
            request.setAttribute("error", "Không tìm thấy danh mục cần xóa!");
            return;
        }

        // Kiểm tra số lượng đồ uống hiện có trong danh mục
        int countDrink = danhMucDAO.countDrinkInCategory(id);
        if (countDrink > 0) {
            // Xóa thông minh: Chuyển sang INACTIVE để bảo toàn khóa ngoại và lịch sử đơn hàng
            category.setTrangThai("INACTIVE");
            danhMucDAO.update(category);
            logAction(request, "DEACTIVATE_CATEGORY", category.getMaDanhMuc(), "ACTIVE", "INACTIVE");
            request.setAttribute("message", "Danh mục đang có " + countDrink + " đồ uống, hệ thống đã chuyển sang ẩn (INACTIVE) để bảo toàn dữ liệu!");
        } else {
            // Danh mục rỗng: Xóa vĩnh viễn
            danhMucDAO.delete(id);
            logAction(request, "DELETE_CATEGORY", category.getMaDanhMuc(), "Xóa danh mục: " + category.getTenDanhMuc(), null);
            request.setAttribute("message", "Đã xóa vĩnh viễn danh mục thành công!");
        }
        // Xóa thông tin form đang chọn
        request.setAttribute("category", null);
    }

    /**
     * Ghi nhật ký hệ thống khi có thao tác tác động vào bảng DANH_MUC
     */
    private void logAction(HttpServletRequest request, String action, String maBanGhi, String cu, String moi) {
        try {
            NhanVien actor = AuthUtil.getUser(request);
            if (actor != null) {
                nhatKyDAO.create(NhatKyHeThong.builder()
                        .idNhanVien(actor.getIdNhanVien())
                        .loaiHanhDong(action)
                        .bangTacDong("DANH_MUC")
                        .maBanGhi(maBanGhi)
                        .duLieuCu(cu)
                        .duLieuMoi(moi)
                        .diaChiIp(request.getRemoteAddr())
                        .thoiGian(new Date())
                        .build());
            }
        } catch (Exception ignored) {
            // Bỏ qua lỗi ghi log để không làm gián đoạn luồng chính
        }
    }
}
