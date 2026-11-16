package com.polycoffee.dao;

import com.polycoffee.entity.PhienCaLamViec;
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
 * DAO thao tac CRUD voi bang PHIEN_CA_LAM_VIEC trong CSDL polycoffee.
 *
 * @author Nam (Nhóm PolyCoffee - JAV102)
 */
public class PhienCaLamViecDAO implements CrudDAO<PhienCaLamViec, Integer> {

    private Timestamp toTimestamp(Date date) {
        return date != null ? new Timestamp(date.getTime()) : null;
    }

    @Override
    public int create(PhienCaLamViec entity) {
        String sql = "INSERT INTO dbo.PHIEN_CA_LAM_VIEC " +
                "(ma_ca_lam, id_nhan_vien, thoi_gian_mo_ca, thoi_gian_ket_ca, tien_dau_ca, " +
                "tong_tien_mat_ht, tong_tien_qr_ht, tien_mat_thuc_te, ghi_chu_chenh_lech, trang_thai) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            return JdbcUtil.executeUpdate(sql,
                    entity.getMaCaLam(),
                    entity.getIdNhanVien(),
                    entity.getThoiGianMoCa() != null ? toTimestamp(entity.getThoiGianMoCa()) : new Timestamp(System.currentTimeMillis()),
                    toTimestamp(entity.getThoiGianKetCa()),
                    entity.getTienDauCa(),
                    entity.getTongTienMatHt() != null ? entity.getTongTienMatHt() : 0L,
                    entity.getTongTienQrHt() != null ? entity.getTongTienQrHt() : 0L,
                    entity.getTienMatThucTe(),
                    entity.getGhiChuChenhLech(),
                    entity.getTrangThai() != null ? entity.getTrangThai() : "OPEN");
        } catch (SQLException e) {
            throw new RuntimeException("Loi them moi PHIEN_CA_LAM_VIEC: " + e.getMessage(), e);
        }
    }

    @Override
    public int update(PhienCaLamViec entity) {
        String sql = "UPDATE dbo.PHIEN_CA_LAM_VIEC " +
                "SET ma_ca_lam = ?, id_nhan_vien = ?, thoi_gian_mo_ca = ?, thoi_gian_ket_ca = ?, " +
                "tien_dau_ca = ?, tong_tien_mat_ht = ?, tong_tien_qr_ht = ?, " +
                "tien_mat_thuc_te = ?, ghi_chu_chenh_lech = ?, trang_thai = ? " +
                "WHERE id_ca_lam = ?";
        try {
            return JdbcUtil.executeUpdate(sql,
                    entity.getMaCaLam(),
                    entity.getIdNhanVien(),
                    toTimestamp(entity.getThoiGianMoCa()),
                    toTimestamp(entity.getThoiGianKetCa()),
                    entity.getTienDauCa(),
                    entity.getTongTienMatHt(),
                    entity.getTongTienQrHt(),
                    entity.getTienMatThucTe(),
                    entity.getGhiChuChenhLech(),
                    entity.getTrangThai(),
                    entity.getIdCaLam());
        } catch (SQLException e) {
            throw new RuntimeException("Loi cap nhat PHIEN_CA_LAM_VIEC: " + e.getMessage(), e);
        }
    }

    @Override
    public int delete(Integer id) {
        String sql = "DELETE FROM dbo.PHIEN_CA_LAM_VIEC WHERE id_ca_lam = ?";
        try {
            return JdbcUtil.executeUpdate(sql, id);
        } catch (SQLException e) {
            throw new RuntimeException("Loi xoa PHIEN_CA_LAM_VIEC: " + e.getMessage(), e);
        }
    }

    @Override
    public List<PhienCaLamViec> findAll() {
        String sql = "SELECT * FROM dbo.PHIEN_CA_LAM_VIEC ORDER BY thoi_gian_mo_ca DESC";
        return findBySql(sql);
    }

    @Override
    public PhienCaLamViec findById(Integer id) {
        String sql = "SELECT * FROM dbo.PHIEN_CA_LAM_VIEC WHERE id_ca_lam = ?";
        List<PhienCaLamViec> list = findBySql(sql, id);
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public List<PhienCaLamViec> findBySql(String sql, Object... values) {
        List<PhienCaLamViec> list = new ArrayList<>();
        try (ResultSet rs = JdbcUtil.executeQuery(sql, values);
             Statement stmt = rs.getStatement();
             Connection conn = stmt.getConnection()) {
            while (rs.next()) {
                PhienCaLamViec ca = new PhienCaLamViec();
                ca.setIdCaLam(rs.getInt("id_ca_lam"));
                ca.setMaCaLam(rs.getString("ma_ca_lam"));
                ca.setIdNhanVien(rs.getInt("id_nhan_vien"));
                ca.setThoiGianMoCa(rs.getTimestamp("thoi_gian_mo_ca"));
                ca.setThoiGianKetCa(rs.getTimestamp("thoi_gian_ket_ca"));
                ca.setTienDauCa(rs.getLong("tien_dau_ca"));
                ca.setTongTienMatHt(rs.getLong("tong_tien_mat_ht"));
                ca.setTongTienQrHt(rs.getLong("tong_tien_qr_ht"));
                long tienThucTe = rs.getLong("tien_mat_thuc_te");
                ca.setTienMatThucTe(rs.wasNull() ? null : tienThucTe);
                ca.setGhiChuChenhLech(rs.getNString("ghi_chu_chenh_lech"));
                ca.setTrangThai(rs.getString("trang_thai"));
                list.add(ca);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Loi truy van PHIEN_CA_LAM_VIEC: " + e.getMessage(), e);
        }
        return list;
    }

    public PhienCaLamViec findByMaCaLam(String maCaLam) {
        String sql = "SELECT * FROM dbo.PHIEN_CA_LAM_VIEC WHERE ma_ca_lam = ?";
        List<PhienCaLamViec> list = findBySql(sql, maCaLam);
        return list.isEmpty() ? null : list.get(0);
    }
}
