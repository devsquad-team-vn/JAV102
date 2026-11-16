package com.polycoffee.dao;

import com.polycoffee.entity.ViVoucherKhachHang;
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
 * DAO thao tac CRUD voi bang VI_VOUCHER_KHACH_HANG trong CSDL polycoffee.
 *
 * @author Khoa (Nhóm PolyCoffee - JAV102)
 */
public class ViVoucherKhachHangDAO implements CrudDAO<ViVoucherKhachHang, Long> {

    private Timestamp toTimestamp(Date date) {
        return date != null ? new Timestamp(date.getTime()) : null;
    }

    @Override
    public int create(ViVoucherKhachHang entity) {
        String sql = "INSERT INTO dbo.VI_VOUCHER_KHACH_HANG " +
                     "(id_khach_hang, id_khuyen_mai, nguon_nhan, ngay_nhan, ngay_het_han, trang_thai) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";
        Date now = new Date();
        try {
            return JdbcUtil.executeUpdate(sql,
                    entity.getIdKhachHang(),
                    entity.getIdKhuyenMai(),
                    entity.getNguonNhan() != null ? entity.getNguonNhan() : "COLLECT",
                    toTimestamp(entity.getNgayNhan() != null ? entity.getNgayNhan() : now),
                    toTimestamp(entity.getNgayHetHan()),
                    entity.getTrangThai() != null ? entity.getTrangThai() : "UNUSED");
        } catch (SQLException e) {
            throw new RuntimeException("Loi them moi VI_VOUCHER_KHACH_HANG: " + e.getMessage(), e);
        }
    }

    @Override
    public int update(ViVoucherKhachHang entity) {
        String sql = "UPDATE dbo.VI_VOUCHER_KHACH_HANG " +
                     "SET id_khach_hang = ?, id_khuyen_mai = ?, nguon_nhan = ?, " +
                     "ngay_nhan = ?, ngay_het_han = ?, trang_thai = ? " +
                     "WHERE id_vi_voucher = ?";
        try {
            return JdbcUtil.executeUpdate(sql,
                    entity.getIdKhachHang(),
                    entity.getIdKhuyenMai(),
                    entity.getNguonNhan(),
                    toTimestamp(entity.getNgayNhan()),
                    toTimestamp(entity.getNgayHetHan()),
                    entity.getTrangThai(),
                    entity.getIdViVoucher());
        } catch (SQLException e) {
            throw new RuntimeException("Loi cap nhat VI_VOUCHER_KHACH_HANG: " + e.getMessage(), e);
        }
    }

    @Override
    public int delete(Long id) {
        String sql = "DELETE FROM dbo.VI_VOUCHER_KHACH_HANG WHERE id_vi_voucher = ?";
        try {
            return JdbcUtil.executeUpdate(sql, id);
        } catch (SQLException e) {
            throw new RuntimeException("Loi xoa VI_VOUCHER_KHACH_HANG: " + e.getMessage(), e);
        }
    }

    @Override
    public List<ViVoucherKhachHang> findAll() {
        String sql = "SELECT * FROM dbo.VI_VOUCHER_KHACH_HANG ORDER BY ngay_nhan DESC";
        return findBySql(sql);
    }

    @Override
    public ViVoucherKhachHang findById(Long id) {
        String sql = "SELECT * FROM dbo.VI_VOUCHER_KHACH_HANG WHERE id_vi_voucher = ?";
        List<ViVoucherKhachHang> list = findBySql(sql, id);
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public List<ViVoucherKhachHang> findBySql(String sql, Object... values) {
        List<ViVoucherKhachHang> list = new ArrayList<>();
        try (ResultSet rs = JdbcUtil.executeQuery(sql, values);
             Statement stmt = rs.getStatement();
             Connection conn = stmt.getConnection()) {
            while (rs.next()) {
                ViVoucherKhachHang vv = new ViVoucherKhachHang();
                vv.setIdViVoucher(rs.getLong("id_vi_voucher"));
                vv.setIdKhachHang(rs.getInt("id_khach_hang"));
                vv.setIdKhuyenMai(rs.getInt("id_khuyen_mai"));
                vv.setNguonNhan(rs.getString("nguon_nhan"));
                vv.setNgayNhan(rs.getTimestamp("ngay_nhan"));
                vv.setNgayHetHan(rs.getTimestamp("ngay_het_han"));
                vv.setTrangThai(rs.getString("trang_thai"));
                list.add(vv);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Loi truy van VI_VOUCHER_KHACH_HANG: " + e.getMessage(), e);
        }
        return list;
    }

    public List<ViVoucherKhachHang> findByKhachHang(Integer idKhachHang) {
        String sql = "SELECT * FROM dbo.VI_VOUCHER_KHACH_HANG WHERE id_khach_hang = ? ORDER BY ngay_het_han ASC";
        return findBySql(sql, idKhachHang);
    }
}
