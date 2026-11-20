package com.polycoffee.dao;

import com.polycoffee.entity.DanhMuc;
import com.polycoffee.util.JdbcUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO thao tac CRUD voi bang DANH_MUC trong CSDL polycoffee.
 * Cai dat theo chuan Bai 3 - Lab 3 JAV102.
 *
 * @author Quang (Nhóm PolyCoffee - JAV102)
 */
public class DanhMucDAO implements CrudDAO<DanhMuc, Integer> {

    @Override
    public int create(DanhMuc entity) {
        String sql = "INSERT INTO dbo.DANH_MUC (ma_danh_muc, ten_danh_muc, mo_ta, thu_tu_hien_thi, trang_thai) " +
                     "VALUES (?, ?, ?, ?, ?)";
        try {
            return JdbcUtil.executeUpdate(sql,
                    entity.getMaDanhMuc(),
                    entity.getTenDanhMuc(),
                    entity.getMoTa(),
                    entity.getThuTuHienThi() != null ? entity.getThuTuHienThi() : 0,
                    entity.getTrangThai() != null ? entity.getTrangThai() : "ACTIVE");
        } catch (SQLException e) {
            throw new RuntimeException("Loi them moi DANH_MUC: " + e.getMessage(), e);
        }
    }

    @Override
    public int update(DanhMuc entity) {
        String sql = "UPDATE dbo.DANH_MUC " +
                     "SET ma_danh_muc = ?, ten_danh_muc = ?, mo_ta = ?, thu_tu_hien_thi = ?, trang_thai = ? " +
                     "WHERE id_danh_muc = ?";
        try {
            return JdbcUtil.executeUpdate(sql,
                    entity.getMaDanhMuc(),
                    entity.getTenDanhMuc(),
                    entity.getMoTa(),
                    entity.getThuTuHienThi(),
                    entity.getTrangThai(),
                    entity.getIdDanhMuc());
        } catch (SQLException e) {
            throw new RuntimeException("Loi cap nhat DANH_MUC: " + e.getMessage(), e);
        }
    }

    @Override
    public int delete(Integer id) {
        String sql = "DELETE FROM dbo.DANH_MUC WHERE id_danh_muc = ?";
        try {
            return JdbcUtil.executeUpdate(sql, id);
        } catch (SQLException e) {
            throw new RuntimeException("Loi xoa DANH_MUC: " + e.getMessage(), e);
        }
    }

    @Override
    public List<DanhMuc> findAll() {
        String sql = "SELECT * FROM dbo.DANH_MUC ORDER BY thu_tu_hien_thi ASC, id_danh_muc ASC";
        return findBySql(sql);
    }

    @Override
    public DanhMuc findById(Integer id) {
        String sql = "SELECT * FROM dbo.DANH_MUC WHERE id_danh_muc = ?";
        List<DanhMuc> list = findBySql(sql, id);
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public List<DanhMuc> findBySql(String sql, Object... values) {
        List<DanhMuc> list = new ArrayList<>();
        try (ResultSet rs = JdbcUtil.executeQuery(sql, values);
             Statement stmt = rs.getStatement();
             Connection conn = stmt.getConnection()) {
            while (rs.next()) {
                DanhMuc dm = new DanhMuc();
                dm.setIdDanhMuc(rs.getInt("id_danh_muc"));
                dm.setMaDanhMuc(rs.getString("ma_danh_muc"));
                dm.setTenDanhMuc(rs.getNString("ten_danh_muc"));
                dm.setMoTa(rs.getNString("mo_ta"));
                dm.setThuTuHienThi(rs.getInt("thu_tu_hien_thi"));
                dm.setTrangThai(rs.getString("trang_thai"));
                list.add(dm);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Loi truy van DANH_MUC: " + e.getMessage(), e);
        }
        return list;
    }

    /**
     * Tim danh muc theo ma danh muc duy nhat.
     */
    public DanhMuc findByMaDanhMuc(String maDanhMuc) {
        String sql = "SELECT * FROM dbo.DANH_MUC WHERE ma_danh_muc = ?";
        List<DanhMuc> list = findBySql(sql, maDanhMuc);
        return list.isEmpty() ? null : list.get(0);
    }

    /**
     * Đếm số lượng sản phẩm thuộc danh mục (Lab 4 - Bài 4).
     * Dùng để kiểm tra trước khi xóa danh mục (nếu có đồ uống thì chuyển INACTIVE để bảo toàn dữ liệu).
     *
     * @author Kiệt (Nhóm PolyCoffee - JAV102)
     */
    public int countDrinkInCategory(int categoryId) {
        String sql = "SELECT COUNT(*) FROM dbo.SAN_PHAM WHERE id_danh_muc = ?";
        try (ResultSet rs = JdbcUtil.executeQuery(sql, categoryId);
             Statement stmt = rs.getStatement();
             Connection conn = stmt.getConnection()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi đếm sản phẩm trong danh mục: " + e.getMessage(), e);
        }
        return 0;
    }
}
