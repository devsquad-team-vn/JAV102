package com.polycoffee.servlet;

import com.polycoffee.dao.DanhMucDAO;
import com.polycoffee.dao.KhuyenMaiDAO;
import com.polycoffee.dao.KichCoSanPhamDAO;
import com.polycoffee.dao.SanPhamDAO;
import com.polycoffee.dao.ToppingDAO;
import com.polycoffee.entity.DanhMuc;
import com.polycoffee.entity.KhuyenMai;
import com.polycoffee.entity.KichCoSanPham;
import com.polycoffee.entity.SanPham;
import com.polycoffee.util.AuthUtil;
import com.polycoffee.util.ParamUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Servlet trang chủ hệ thống PolyCoffee (Portal hiển thị thực đơn và điều hướng người dùng).
 *
 * @author Kiệt (Nhóm PolyCoffee - JAV102)
 */
@WebServlet({"/trang-chu", "/home"})
public class HomeServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private final DanhMucDAO danhMucDAO = new DanhMucDAO();
    private final SanPhamDAO sanPhamDAO = new SanPhamDAO();
    private final KichCoSanPhamDAO kichCoDAO = new KichCoSanPhamDAO();
    private final ToppingDAO toppingDAO = new ToppingDAO();
    private final KhuyenMaiDAO khuyenMaiDAO = new KhuyenMaiDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Kiểm tra và hiển thị thông báo lỗi phân quyền từ AuthFilter (nếu có)
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("AUTH_ERROR") != null) {
            request.setAttribute("authError", session.getAttribute("AUTH_ERROR"));
            session.removeAttribute("AUTH_ERROR");
        }

        // Lọc đồ uống theo danh mục nếu người dùng chọn
        int categoryId = ParamUtil.getInt(request, "categoryId");

        // Lấy danh sách danh mục đang hoạt động
        List<DanhMuc> categories = danhMucDAO.findBySql(
                "SELECT * FROM dbo.DANH_MUC WHERE trang_thai = 'ACTIVE' ORDER BY thu_tu_hien_thi ASC");

        // Lấy danh sách sản phẩm theo danh mục hoặc toàn bộ món khả dụng
        List<SanPham> drinks;
        if (categoryId > 0) {
            drinks = sanPhamDAO.findBySql(
                    "SELECT * FROM dbo.SAN_PHAM WHERE trang_thai = 'AVAILABLE' AND id_danh_muc = ? ORDER BY id_san_pham ASC",
                    categoryId);
        } else {
            drinks = sanPhamDAO.findBySql(
                    "SELECT * FROM dbo.SAN_PHAM WHERE trang_thai = 'AVAILABLE' ORDER BY la_mon_ban_chay DESC, id_san_pham ASC");
        }

        // Danh sách món bán chạy & món mới
        List<SanPham> bestSellers = sanPhamDAO.findBySql(
                "SELECT * FROM dbo.SAN_PHAM WHERE trang_thai = 'AVAILABLE' AND la_mon_ban_chay = 1 ORDER BY id_san_pham ASC");
        List<SanPham> newDrinks = sanPhamDAO.findBySql(
                "SELECT * FROM dbo.SAN_PHAM WHERE trang_thai = 'AVAILABLE' AND la_mon_moi = 1 ORDER BY id_san_pham DESC");

        // Danh sách khuyến mãi còn hiệu lực
        List<KhuyenMai> activeVouchers = khuyenMaiDAO.findBySql(
                "SELECT * FROM dbo.KHUYEN_MAI WHERE trang_thai = 'ACTIVE' ORDER BY ngay_ket_thuc ASC");

        // Map kích cỡ và giá tiền tương ứng cho từng sản phẩm (Size S, M, L)
        Map<Integer, List<KichCoSanPham>> sizeMap = new HashMap<>();
        for (SanPham sp : drinks) {
            sizeMap.put(sp.getIdSanPham(), kichCoDAO.findBySanPham(sp.getIdSanPham()));
        }
        for (SanPham sp : bestSellers) {
            sizeMap.computeIfAbsent(sp.getIdSanPham(), k -> kichCoDAO.findBySanPham(k));
        }
        for (SanPham sp : newDrinks) {
            sizeMap.computeIfAbsent(sp.getIdSanPham(), k -> kichCoDAO.findBySanPham(k));
        }

        // Đẩy dữ liệu ra giao diện JSP
        request.setAttribute("categories", categories);
        request.setAttribute("drinks", drinks);
        request.setAttribute("bestSellers", bestSellers);
        request.setAttribute("newDrinks", newDrinks);
        request.setAttribute("activeVouchers", activeVouchers);
        request.setAttribute("sizeMap", sizeMap);
        request.setAttribute("toppingsCount", toppingDAO.findAll().size());
        request.setAttribute("selectedCategoryId", categoryId);
        request.setAttribute("isLoggedIn", AuthUtil.isAuthenticated(request));
        request.setAttribute("isManager", AuthUtil.isManager(request));

        request.getRequestDispatcher("/views/home/index.jsp").forward(request, response);
    }
}
