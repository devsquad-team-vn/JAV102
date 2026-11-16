package com.polycoffee.dao;

import com.polycoffee.entity.KhachHang;
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
 * DAO thao tac CRUD voi bang KHACH_HANG trong CSDL polycoffee.
 *
 * @author Khoa (Nhóm PolyCoffee - JAV102)
 */
public class KhachHangDAO implements CrudDAO<KhachHang, Integer> {

    private Timestamp toTimestamp(Date date) {
        return date != null ? new Timestamp(date.getTime()) : null;
    }

    private java.sql.Date toSqlDate(Date date) {
        return date != null ? new java.sql.Date(date.getTime()) : null;
    }

    @Override
    public int create(KhachHang entity) {
        String sql = "INSERT INTO dbo.KHACH_HANG " +
                     "(ma_khach_hang, so_dien_thoai, ho_ten, email, mat_khau_bam, ngay_sinh, anh_dai_dien, " +
                     "diem_kha_dung, tong_diem_tich_luy, hang_thanh_vien, ma_pin_kich_hoat, da_kich_hoat, " +
                     "so_lan_sai_mk, khoa_den_luc, dang_hoat_dong, ngay_tao) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            return JdbcUtil.executeUpdate(sql,
                    entity.getMaKhachHang(),
                    entity.getSoDienThoai(),
                    entity.getHoTen(),
                    entity.getEmail(),
                    entity.getMatKhauBam(),
                    toSqlDate(entity.getNgaySinh()),
                    entity.getAnhDaiDien(),
                    entity.getDiemKhaDung() != null ? entity.getDiemKhaDung() : 0,
                    entity.getTongDiemTichLuy() != null ? entity.getTongDiemTichLuy() : 0,
                    entity.getHangThanhVien() != null ? entity.getHangThanhVien() : "Đồng",
                    entity.getMaPinKichHoat(),
                    entity.getDaKichHoat() != null ? entity.getDaKichHoat() : Boolean.FALSE,
                    entity.getSoLanSaiMk() != null ? entity.getSoLanSaiMk() : 0,
                    toTimestamp(entity.getKhoaDenLuc()),
                    entity.getDangHoatDong() != null ? entity.getDangHoatDong() : Boolean.TRUE,
                    entity.getNgayTao() != null ? toTimestamp(entity.getNgayTao()) : new Timestamp(System.currentTimeMillis()));
        } catch (SQLException e) {
            throw new RuntimeException("Loi them moi KHACH_HANG: " + e.getMessage(), e);
        }
    }

    @Override
    public int update(KhachHang entity) {
        String sql = "UPDATE dbo.KHACH_HANG " +
                     "SET ma_khach_hang = ?, so_dien_thoai = ?, ho_ten = ?, email = ?, mat_khau_bam = ?, " +
                     "ngay_sinh = ?, anh_dai_dien = ?, diem_kha_dung = ?, tong_diem_tich_luy = ?, " +
                     "hang_thanh_vien = ?, ma_pin_kich_hoat = ?, da_kich_hoat = ?, " +
                     "so_lan_sai_mk = ?, khoa_den_luc = ?, dang_hoat_dong = ? " +
                     "WHERE id_khach_hang = ?";
        try {
            return JdbcUtil.executeUpdate(sql,
                    entity.getMaKhachHang(),
                    entity.getSoDienThoai(),
                    entity.getHoTen(),
                    entity.getEmail(),
                    entity.getMatKhauBam(),
                    toSqlDate(entity.getNgaySinh()),
                    entity.getAnhDaiDien(),
                    entity.getDiemKhaDung(),
                    entity.getTongDiemTichLuy(),
                    entity.getHangThanhVien(),
                    entity.getMaPinKichHoat(),
                    entity.getDaKichHoat(),
                    entity.getSoLanSaiMk(),
                    toTimestamp(entity.getKhoaDenLuc()),
                    entity.getDangHoatDong(),
                    entity.getIdKhachHang());
        } catch (SQLException e) {
            throw new RuntimeException("Loi cap nhat KHACH_HANG: " + e.getMessage(), e);
        }
    }

    @Override
    public int delete(Integer id) {
        String sql = "DELETE FROM dbo.KHACH_HANG WHERE id_khach_hang = ?";
        try {
            return JdbcUtil.executeUpdate(sql, id);
        } catch (SQLException e) {
            throw new RuntimeException("Loi xoa KHACH_HANG: " + e.getMessage(), e);
        }
    }

    @Override
    public List<KhachHang> findAll() {
        String sql = "SELECT * FROM dbo.KHACH_HANG ORDER BY id_khach_hang ASC";
        return findBySql(sql);
    }

    @Override
    public KhachHang findById(Integer id) {
        String sql = "SELECT * FROM dbo.KHACH_HANG WHERE id_khach_hang = ?";
        List<KhachHang> list = findBySql(sql, id);
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public List<KhachHang> findBySql(String sql, Object... values) {
        List<KhachHang> list = new ArrayList<>();
        try (ResultSet rs = JdbcUtil.executeQuery(sql, values);
             Statement stmt = rs.getStatement();
             Connection conn = stmt.getConnection()) {
            while (rs.next()) {
                KhachHang kh = new KhachHang();
                kh.setIdKhachHang(rs.getInt("id_khach_hang"));
                kh.setMaKhachHang(rs.getString("ma_khach_hang"));
                kh.setSoDienThoai(rs.getString("so_dien_thoai"));
                kh.setHoTen(rs.getNString("ho_ten"));
                kh.setEmail(rs.getString("email"));
                kh.setMatKhauBam(rs.getString("mat_khau_bam"));
                kh.setNgaySinh(rs.getDate("ngay_sinh"));
                kh.setAnhDaiDien(rs.getString("anh_dai_dien"));
                kh.setDiemKhaDung(rs.getInt("diem_kha_dung"));
                kh.setTongDiemTichLuy(rs.getInt("tong_diem_tich_luy"));
                kh.setHangThanhVien(rs.getNString("hang_thanh_vien"));
                kh.setMaPinKichHoat(rs.getString("ma_pin_kich_hoat"));
                kh.setDaKichHoat(rs.getBoolean("da_kich_hoat"));
                kh.setSoLanSaiMk(rs.getInt("so_lan_sai_mk"));
                kh.setKhoaDenLuc(rs.getTimestamp("khoa_den_luc"));
                kh.setDangHoatDong(rs.getBoolean("dang_hoat_dong"));
                kh.setNgayTao(rs.getTimestamp("ngay_tao"));
                list.add(kh);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Loi truy van KHACH_HANG: " + e.getMessage(), e);
        }
        return list;
    }

    public KhachHang findBySoDienThoai(String soDienThoai) {
        String sql = "SELECT * FROM dbo.KHACH_HANG WHERE so_dien_thoai = ?";
        List<KhachHang> list = findBySql(sql, soDienThoai);
        return list.isEmpty() ? null : list.get(0);
    }
}
