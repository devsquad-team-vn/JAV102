package com.polycoffee.dao;

import com.polycoffee.entity.NhanVien;
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
 * DAO thao tac CRUD voi bang NHAN_VIEN trong CSDL polycoffee.
 *
 * @author Nam (Nhóm PolyCoffee - JAV102)
 */
public class NhanVienDAO implements CrudDAO<NhanVien, Integer> {

    private Timestamp toTimestamp(Date date) {
        return date != null ? new Timestamp(date.getTime()) : null;
    }

    @Override
    public int create(NhanVien entity) {
        String sql = "INSERT INTO dbo.NHAN_VIEN " +
                "(ma_nhan_vien, ten_dang_nhap, email, mat_khau_bam, ho_ten, so_dien_thoai, " +
                "dia_chi, anh_dai_dien, vai_tro, so_lan_sai_mk, khoa_den_luc, dang_hoat_dong, ngay_tao) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            return JdbcUtil.executeUpdate(sql,
                    entity.getMaNhanVien(),
                    entity.getTenDangNhap(),
                    entity.getEmail(),
                    entity.getMatKhauBam(),
                    entity.getHoTen(),
                    entity.getSoDienThoai(),
                    entity.getDiaChi(),
                    entity.getAnhDaiDien(),
                    entity.getVaiTro(),
                    entity.getSoLanSaiMk() != null ? entity.getSoLanSaiMk() : 0,
                    toTimestamp(entity.getKhoaDenLuc()),
                    entity.getDangHoatDong() != null ? entity.getDangHoatDong() : Boolean.TRUE,
                    entity.getNgayTao() != null ? toTimestamp(entity.getNgayTao()) : new Timestamp(System.currentTimeMillis()));
        } catch (SQLException e) {
            throw new RuntimeException("Loi them moi NHAN_VIEN: " + e.getMessage(), e);
        }
    }

    @Override
    public int update(NhanVien entity) {
        String sql = "UPDATE dbo.NHAN_VIEN " +
                "SET ma_nhan_vien = ?, ten_dang_nhap = ?, email = ?, mat_khau_bam = ?, ho_ten = ?, " +
                "so_dien_thoai = ?, dia_chi = ?, anh_dai_dien = ?, vai_tro = ?, " +
                "so_lan_sai_mk = ?, khoa_den_luc = ?, dang_hoat_dong = ? " +
                "WHERE id_nhan_vien = ?";
        try {
            return JdbcUtil.executeUpdate(sql,
                    entity.getMaNhanVien(),
                    entity.getTenDangNhap(),
                    entity.getEmail(),
                    entity.getMatKhauBam(),
                    entity.getHoTen(),
                    entity.getSoDienThoai(),
                    entity.getDiaChi(),
                    entity.getAnhDaiDien(),
                    entity.getVaiTro(),
                    entity.getSoLanSaiMk(),
                    toTimestamp(entity.getKhoaDenLuc()),
                    entity.getDangHoatDong(),
                    entity.getIdNhanVien());
        } catch (SQLException e) {
            throw new RuntimeException("Loi cap nhat NHAN_VIEN: " + e.getMessage(), e);
        }
    }

    @Override
    public int delete(Integer id) {
        String sql = "DELETE FROM dbo.NHAN_VIEN WHERE id_nhan_vien = ?";
        try {
            return JdbcUtil.executeUpdate(sql, id);
        } catch (SQLException e) {
            throw new RuntimeException("Loi xoa NHAN_VIEN: " + e.getMessage(), e);
        }
    }

    @Override
    public List<NhanVien> findAll() {
        String sql = "SELECT * FROM dbo.NHAN_VIEN ORDER BY id_nhan_vien ASC";
        return findBySql(sql);
    }

    @Override
    public NhanVien findById(Integer id) {
        String sql = "SELECT * FROM dbo.NHAN_VIEN WHERE id_nhan_vien = ?";
        List<NhanVien> list = findBySql(sql, id);
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public List<NhanVien> findBySql(String sql, Object... values) {
        List<NhanVien> list = new ArrayList<>();
        try (ResultSet rs = JdbcUtil.executeQuery(sql, values);
             Statement stmt = rs.getStatement();
             Connection conn = stmt.getConnection()) {
            while (rs.next()) {
                NhanVien nv = new NhanVien();
                nv.setIdNhanVien(rs.getInt("id_nhan_vien"));
                nv.setMaNhanVien(rs.getString("ma_nhan_vien"));
                nv.setTenDangNhap(rs.getString("ten_dang_nhap"));
                nv.setEmail(rs.getString("email"));
                nv.setMatKhauBam(rs.getString("mat_khau_bam"));
                nv.setHoTen(rs.getNString("ho_ten"));
                nv.setSoDienThoai(rs.getString("so_dien_thoai"));
                nv.setDiaChi(rs.getNString("dia_chi"));
                nv.setAnhDaiDien(rs.getString("anh_dai_dien"));
                nv.setVaiTro(rs.getString("vai_tro"));
                nv.setSoLanSaiMk(rs.getInt("so_lan_sai_mk"));
                nv.setKhoaDenLuc(rs.getTimestamp("khoa_den_luc"));
                nv.setDangHoatDong(rs.getBoolean("dang_hoat_dong"));
                nv.setNgayTao(rs.getTimestamp("ngay_tao"));
                list.add(nv);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Loi truy van NHAN_VIEN: " + e.getMessage(), e);
        }
        return list;
    }

    public NhanVien findByTenDangNhap(String tenDangNhap) {
        String sql = "SELECT * FROM dbo.NHAN_VIEN WHERE ten_dang_nhap = ?";
        List<NhanVien> list = findBySql(sql, tenDangNhap);
        return list.isEmpty() ? null : list.get(0);
    }

    // TODO (Nam): Bo sung phuong thuc findByEmail cho chuc nang dang nhap Lab 4
}
