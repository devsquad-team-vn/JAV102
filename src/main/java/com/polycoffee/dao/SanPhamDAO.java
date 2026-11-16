package com.polycoffee.dao;

import com.polycoffee.entity.SanPham;
import com.polycoffee.util.JdbcUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * DAO thao tac CRUD voi bang SAN_PHAM trong CSDL polycoffee.
 *
 * @author Quang (Nhóm PolyCoffee - JAV102)
 */
public class SanPhamDAO implements CrudDAO<SanPham, Integer> {

    private Timestamp toTimestamp(Date date) {
        return date != null ? new Timestamp(date.getTime()) : new Timestamp(System.currentTimeMillis());
    }

    @Override
    public int create(SanPham entity) {
        String sql = "INSERT INTO dbo.SAN_PHAM " +
                     "(ma_san_pham, id_danh_muc, ten_san_pham, hinh_anh, mo_ta, " +
                     "cho_phep_duong_da, cho_phep_topping, la_mon_ban_chay, la_mon_moi, trang_thai, ngay_tao) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            return JdbcUtil.executeUpdate(sql,
                    entity.getMaSanPham(),
                    entity.getIdDanhMuc(),
                    entity.getTenSanPham(),
                    entity.getHinhAnh(),
                    entity.getMoTa(),
                    entity.getChoPhepDuongDa() != null ? entity.getChoPhepDuongDa() : Boolean.TRUE,
                    entity.getChoPhepTopping() != null ? entity.getChoPhepTopping() : Boolean.TRUE,
                    entity.getLaMonBanChay() != null ? entity.getLaMonBanChay() : Boolean.FALSE,
                    entity.getLaMonMoi() != null ? entity.getLaMonMoi() : Boolean.FALSE,
                    entity.getTrangThai() != null ? entity.getTrangThai() : "AVAILABLE",
                    toTimestamp(entity.getNgayTao()));
        } catch (SQLException e) {
            throw new RuntimeException("Loi them moi SAN_PHAM: " + e.getMessage(), e);
        }
    }

    @Override
    public int update(SanPham entity) {
        String sql = "UPDATE dbo.SAN_PHAM " +
                     "SET ma_san_pham = ?, id_danh_muc = ?, ten_san_pham = ?, hinh_anh = ?, mo_ta = ?, " +
                     "cho_phep_duong_da = ?, cho_phep_topping = ?, la_mon_ban_chay = ?, la_mon_moi = ?, trang_thai = ? " +
                     "WHERE id_san_pham = ?";
        try {
            return JdbcUtil.executeUpdate(sql,
                    entity.getMaSanPham(),
                    entity.getIdDanhMuc(),
                    entity.getTenSanPham(),
                    entity.getHinhAnh(),
                    entity.getMoTa(),
                    entity.getChoPhepDuongDa(),
                    entity.getChoPhepTopping(),
                    entity.getLaMonBanChay(),
                    entity.getLaMonMoi(),
                    entity.getTrangThai(),
                    entity.getIdSanPham());
        } catch (SQLException e) {
            throw new RuntimeException("Loi cap nhat SAN_PHAM: " + e.getMessage(), e);
        }
    }

    @Override
    public int delete(Integer id) {
        String sql = "DELETE FROM dbo.SAN_PHAM WHERE id_san_pham = ?";
        try {
            return JdbcUtil.executeUpdate(sql, id);
        } catch (SQLException e) {
            throw new RuntimeException("Loi xoa SAN_PHAM: " + e.getMessage(), e);
        }
    }

    @Override
    public List<SanPham> findAll() {
        String sql = "SELECT * FROM dbo.SAN_PHAM ORDER BY id_san_pham ASC";
        return findBySql(sql);
    }

    @Override
    public SanPham findById(Integer id) {
        String sql = "SELECT * FROM dbo.SAN_PHAM WHERE id_san_pham = ?";
        List<SanPham> list = findBySql(sql, id);
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public List<SanPham> findBySql(String sql, Object... values) {
        List<SanPham> list = new ArrayList<>();
        try (ResultSet rs = JdbcUtil.executeQuery(sql, values);
             Statement stmt = rs.getStatement();
             Connection conn = stmt.getConnection()) {
            while (rs.next()) {
                SanPham sp = new SanPham();
                sp.setIdSanPham(rs.getInt("id_san_pham"));
                sp.setMaSanPham(rs.getString("ma_san_pham"));
                sp.setIdDanhMuc(rs.getInt("id_danh_muc"));
                sp.setTenSanPham(rs.getNString("ten_san_pham"));
                sp.setHinhAnh(rs.getString("hinh_anh"));
                sp.setMoTa(rs.getNString("mo_ta"));
                sp.setChoPhepDuongDa(rs.getBoolean("cho_phep_duong_da"));
                sp.setChoPhepTopping(rs.getBoolean("cho_phep_topping"));
                sp.setLaMonBanChay(rs.getBoolean("la_mon_ban_chay"));
                sp.setLaMonMoi(rs.getBoolean("la_mon_moi"));
                sp.setTrangThai(rs.getString("trang_thai"));
                sp.setNgayTao(rs.getTimestamp("ngay_tao"));
                list.add(sp);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Loi truy van SAN_PHAM: " + e.getMessage(), e);
        }
        return list;
    }

    public SanPham findByMaSanPham(String maSanPham) {
        String sql = "SELECT * FROM dbo.SAN_PHAM WHERE ma_san_pham = ?";
        List<SanPham> list = findBySql(sql, maSanPham);
        return list.isEmpty() ? null : list.get(0);
    }

    public List<SanPham> findByDanhMuc(Integer idDanhMuc) {
        String sql = "SELECT * FROM dbo.SAN_PHAM WHERE id_danh_muc = ? ORDER BY id_san_pham ASC";
        return findBySql(sql, idDanhMuc);
    }
}
