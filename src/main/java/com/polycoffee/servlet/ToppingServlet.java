package com.polycoffee.servlet;

import com.polycoffee.dao.ChiTietToppingDAO;
import com.polycoffee.dao.NhatKyHeThongDAO;
import com.polycoffee.dao.ToppingDAO;
import com.polycoffee.entity.NhanVien;
import com.polycoffee.entity.NhatKyHeThong;
import com.polycoffee.entity.Topping;
import com.polycoffee.util.AuthUtil;
import com.polycoffee.util.ParamUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Date;

/**
 * Servlet quan ly CRUD danh muc Topping (TOPPING - Lab 4).
 *
 * @author Quý (Nhóm PolyCoffee - JAV102)
 */
@WebServlet({"/manager/toppings", "/manager/toppings/add", "/manager/toppings/edit", "/manager/toppings/delete"})
public class ToppingServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final ToppingDAO toppingDAO = new ToppingDAO();
    private final ChiTietToppingDAO ctToppingDAO = new ChiTietToppingDAO();
    private final NhatKyHeThongDAO nhatKyDAO = new NhatKyHeThongDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = ParamUtil.getInt(request, "id");
        if (id > 0) {
            request.setAttribute("topping", toppingDAO.findById(id));
        }
        request.setAttribute("list", toppingDAO.findAll());
        request.getRequestDispatcher("/views/topping/list.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String uri = request.getRequestURI();
        if (uri.contains("/add")) {
            create(request);
        } else if (uri.contains("/edit")) {
            update(request);
        } else if (uri.contains("/delete")) {
            delete(request);
        }
        request.setAttribute("list", toppingDAO.findAll());
        request.getRequestDispatcher("/views/topping/list.jsp").forward(request, response);
    }

    private void create(HttpServletRequest request) {
        String ma = ParamUtil.getString(request, "maTopping");
        String ten = ParamUtil.getString(request, "tenTopping");
        long gia = ParamUtil.getLong(request, "giaTopping");
        String trangThai = ParamUtil.getString(request, "trangThai");

        if (ten == null || ten.isBlank() || gia < 0) {
            request.setAttribute("error", "Ten topping khong duoc de trong va gia phai >= 0!");
            return;
        }
        if (ma == null || ma.isBlank()) {
            ma = "TP" + (System.currentTimeMillis() % 100000);
        }
        if (toppingDAO.findByMaTopping(ma) != null) {
            request.setAttribute("error", "Ma topping '" + ma + "' da ton tai!");
            return;
        }
        try {
            Topping tp = Topping.builder()
                    .maTopping(ma)
                    .tenTopping(ten)
                    .giaTopping(gia)
                    .hinhAnh("/assets/img/tp-default.jpg")
                    .trangThai(trangThai != null && !trangThai.isBlank() ? trangThai : "AVAILABLE")
                    .build();
            int rs = toppingDAO.create(tp);
            if (rs > 0) {
                logAction(request, "CREATE_TOPPING", ma, null,
                        "{\"ten_topping\":\"" + esc(ten) + "\",\"gia_topping\":" + gia + ",\"trang_thai\":\"" + tp.getTrangThai() + "\"}");
            }
            request.setAttribute(rs > 0 ? "message" : "error", rs > 0 ? "Them topping thanh cong!" : "Them topping that bai!");
        } catch (Exception e) {
            request.setAttribute("error", "Loi them topping: " + e.getMessage());
        }
    }

    private void update(HttpServletRequest request) {
        int id = ParamUtil.getInt(request, "idTopping");
        String ten = ParamUtil.getString(request, "tenTopping");
        long gia = ParamUtil.getLong(request, "giaTopping");
        String trangThai = ParamUtil.getString(request, "trangThai");

        Topping tp = toppingDAO.findById(id);
        if (tp == null) {
            request.setAttribute("error", "Khong tim thay topping!");
            return;
        }
        if (ten == null || ten.isBlank() || gia < 0) {
            request.setAttribute("error", "Ten topping khong hop le hoac gia < 0!");
            request.setAttribute("topping", tp);
            return;
        }
        String oldJson = "{\"ten_topping\":\"" + esc(tp.getTenTopping()) + "\",\"gia_topping\":" + tp.getGiaTopping() + ",\"trang_thai\":\"" + tp.getTrangThai() + "\"}";
        tp.setTenTopping(ten);
        tp.setGiaTopping(gia);
        if (trangThai != null && !trangThai.isBlank()) {
            tp.setTrangThai(trangThai);
        }
        try {
            int rs = toppingDAO.update(tp);
            if (rs > 0) {
                logAction(request, "UPDATE_TOPPING", tp.getMaTopping(), oldJson,
                        "{\"ten_topping\":\"" + esc(ten) + "\",\"gia_topping\":" + gia + ",\"trang_thai\":\"" + tp.getTrangThai() + "\"}");
            }
            request.setAttribute(rs > 0 ? "message" : "error", rs > 0 ? "Cap nhat topping thanh cong!" : "Cap nhat that bai!");
            request.setAttribute("topping", tp);
        } catch (Exception e) {
            request.setAttribute("error", "Loi cap nhat topping: " + e.getMessage());
        }
    }

    private void delete(HttpServletRequest request) {
        int id = ParamUtil.getInt(request, "idTopping");
        if (id <= 0) {
            id = ParamUtil.getInt(request, "id");
        }
        Topping tp = id > 0 ? toppingDAO.findById(id) : null;
        if (tp == null) {
            request.setAttribute("error", "Khong tim thay topping can xoa!");
            return;
        }
        boolean usedInOrders = !ctToppingDAO.findBySql("SELECT * FROM dbo.CHI_TIET_TOPPING WHERE id_topping = ?", id).isEmpty();
        if (usedInOrders) {
            String oldJson = "{\"trang_thai\":\"" + tp.getTrangThai() + "\"}";
            tp.setTrangThai("INACTIVE");
            toppingDAO.update(tp);
            logAction(request, "DEACTIVATE_TOPPING", tp.getMaTopping(), oldJson, "{\"trang_thai\":\"INACTIVE\"}");
            request.setAttribute("message", "Topping da phat sinh giao dich, da chuyen sang INACTIVE!");
        } else {
            String oldJson = "{\"ten_topping\":\"" + esc(tp.getTenTopping()) + "\",\"gia_topping\":" + tp.getGiaTopping() + "}";
            toppingDAO.delete(id);
            logAction(request, "DELETE_TOPPING", tp.getMaTopping(), oldJson, null);
            request.setAttribute("message", "Da xoa topping thanh cong!");
        }
    }

    private void logAction(HttpServletRequest req, String action, String maBanGhi, String cu, String moi) {
        try {
            NhanVien actor = AuthUtil.getUser(req);
            if (actor != null) {
                nhatKyDAO.create(NhatKyHeThong.builder()
                        .idNhanVien(actor.getIdNhanVien())
                        .loaiHanhDong(action)
                        .bangTacDong("TOPPING")
                        .maBanGhi(maBanGhi)
                        .duLieuCu(cu)
                        .duLieuMoi(moi)
                        .diaChiIp(req.getRemoteAddr())
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
