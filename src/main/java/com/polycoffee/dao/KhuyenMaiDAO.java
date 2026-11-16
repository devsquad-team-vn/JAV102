package com.polycoffee.dao;

import com.polycoffee.entity.KhuyenMai;
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
 * DAO thao tac CRUD voi bang KHUYEN_MAI trong CSDL polycoffee.
 *
 * @author Khoa (Nhóm PolyCoffee - JAV102)
 */
public class KhuyenMaiDAO implements CrudDAO<KhuyenMai, Integer> {

    private Timestamp toTimestamp(Date date) {
        return date != null ? new Timestamp(date.getTime()) : null;
    }

    @Override
    public int create(KhuyenMai entity) {
        String sql = "INSERT INTO dbo.KHUYEN_MAI " +
                     "(ma_code_km, ten_khuyen_mai, mo_ta, loai_giam_gia, gia_tri_giam, giam_toi_da, " +
                     "don_toi_thieu, pham_vi_ap_dung, hang_toi_thieu, diem_can_doi, tong_luot_phat_hanh, so_luot_da_dung, " +
                     "gioi_han_moi_khach, ngay_bat_dau, ngay_ket_thuc, trang_thai) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            return JdbcUtil.executeUpdate(sql,
                    entity.getMaCodeKm(),
                    entity.getTenKhuyenMai(),
                    entity.getMoTa(),
                    entity.getLoaiGiamGia(),
                    entity.getGiaTriGiam(),
                    entity.getGiamToiDa(),
                    entity.getDonToiThieu() != null ? entity.getDonToiThieu() : 0L,
                    entity.getPhamViApDung(),
                    entity.getHangToiThieu(),
                    entity.getDiemCanDoi() != null ? entity.getDiemCanDoi() : 0,
                    entity.getTongLuotPhatHanh(),
                    entity.getSoLuotDaDung() != null ? entity.getSoLuotDaDung() : 0,
                    entity.getGioiHanMoiKhach() != null ? entity.getGioiHanMoiKhach() : 1,
                    toTimestamp(entity.getNgayBatDau()),
                    toTimestamp(entity.getNgayKetThuc()),
                    entity.getTrangThai() != null ? entity.getTrangThai() : "ACTIVE");
        } catch (SQLException e) {
            throw new RuntimeException("Loi them moi KHUYEN_MAI: " + e.getMessage(), e);
        }
    }

    @Override
    public int update(KhuyenMai entity) {
        String sql = "UPDATE dbo.KHUYEN_MAI " +
                     "SET ma_code_km = ?, ten_khuyen_mai = ?, mo_ta = ?, loai_giam_gia = ?, gia_tri_giam = ?, " +
                     "giam_toi_da = ?, don_toi_thieu = ?, pham_vi_ap_dung = ?, hang_toi_thieu = ?, diem_can_doi = ?, " +
                     "tong_luot_phat_hanh = ?, so_luot_da_dung = ?, gioi_han_moi_khach = ?, " +
                     "ngay_bat_dau = ?, ngay_ket_thuc = ?, trang_thai = ? " +
                     "WHERE id_khuyen_mai = ?";
        try {
            return JdbcUtil.executeUpdate(sql,
                    entity.getMaCodeKm(),
                    entity.getTenKhuyenMai(),
                    entity.getMoTa(),
                    entity.getLoaiGiamGia(),
                    entity.getGiaTriGiam(),
                    entity.getGiamToiDa(),
                    entity.getDonToiThieu(),
                    entity.getPhamViApDung(),
                    entity.getHangToiThieu(),
                    entity.getDiemCanDoi(),
                    entity.getTongLuotPhatHanh(),
                    entity.getSoLuotDaDung(),
                    entity.getGioiHanMoiKhach(),
                    toTimestamp(entity.getNgayBatDau()),
                    toTimestamp(entity.getNgayKetThuc()),
                    entity.getTrangThai(),
                    entity.getIdKhuyenMai());
        } catch (SQLException e) {
            throw new RuntimeException("Loi cap nhat KHUYEN_MAI: " + e.getMessage(), e);
        }
    }

    @Override
    public int delete(Integer id) {
        String sql = "DELETE FROM dbo.KHUYEN_MAI WHERE id_khuyen_mai = ?";
        try {
            return JdbcUtil.executeUpdate(sql, id);
        } catch (SQLException e) {
            throw new RuntimeException("Loi xoa KHUYEN_MAI: " + e.getMessage(), e);
        }
    }

    @Override
    public List<KhuyenMai> findAll() {
        String sql = "SELECT * FROM dbo.KHUYEN_MAI ORDER BY ngay_bat_dau DESC";
        return findBySql(sql);
    }

    @Override
    public KhuyenMai findById(Integer id) {
        String sql = "SELECT * FROM dbo.KHUYEN_MAI WHERE id_khuyen_mai = ?";
        List<KhuyenMai> list = findBySql(sql, id);
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public List<KhuyenMai> findBySql(String sql, Object... values) {
        List<KhuyenMai> list = new ArrayList<>();
        try (ResultSet rs = JdbcUtil.executeQuery(sql, values);
             Statement stmt = rs.getStatement();
             Connection conn = stmt.getConnection()) {
            while (rs.next()) {
                KhuyenMai km = new KhuyenMai();
                km.setIdKhuyenMai(rs.getInt("id_khuyen_mai"));
                km.setMaCodeKm(rs.getString("ma_code_km"));
                km.setTenKhuyenMai(rs.getNString("ten_khuyen_mai"));
                km.setMoTa(rs.getNString("mo_ta"));
                km.setLoaiGiamGia(rs.getString("loai_giam_gia"));
                km.setGiaTriGiam(rs.getLong("gia_tri_giam"));
                long giamToiDa = rs.getLong("giam_toi_da");
                km.setGiamToiDa(rs.wasNull() ? null : giamToiDa);
                km.setDonToiThieu(rs.getLong("don_toi_thieu"));
                km.setPhamViApDung(rs.getString("pham_vi_ap_dung"));
                km.setHangToiThieu(rs.getNString("hang_toi_thieu"));
                km.setDiemCanDoi(rs.getInt("diem_can_doi"));
                km.setTongLuotPhatHanh(rs.getInt("tong_luot_phat_hanh"));
                km.setSoLuotDaDung(rs.getInt("so_luot_da_dung"));
                km.setGioiHanMoiKhach(rs.getInt("gioi_han_moi_khach"));
                km.setNgayBatDau(rs.getTimestamp("ngay_bat_dau"));
                km.setNgayKetThuc(rs.getTimestamp("ngay_ket_thuc"));
                km.setTrangThai(rs.getString("trang_thai"));
                list.add(km);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Loi truy van KHUYEN_MAI: " + e.getMessage(), e);
        }
        return list;
    }

    public KhuyenMai findByMaCode(String maCodeKm) {
        String sql = "SELECT * FROM dbo.KHUYEN_MAI WHERE ma_code_km = ?";
        List<KhuyenMai> list = findBySql(sql, maCodeKm);
        return list.isEmpty() ? null : list.get(0);
    }
}
