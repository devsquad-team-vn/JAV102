package com.polycoffee.dao;

import com.polycoffee.entity.DonHang;
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
 * DAO thao tac CRUD voi bang DON_HANG trong CSDL polycoffee.
 *
 * @author Kiệt (Nhóm PolyCoffee - JAV102)
 */
public class DonHangDAO implements CrudDAO<DonHang, Long> {

    private Timestamp toTimestamp(Date date) {
        return date != null ? new Timestamp(date.getTime()) : null;
    }

    private Integer getNullableInt(ResultSet rs, String col) throws SQLException {
        int val = rs.getInt(col);
        return rs.wasNull() ? null : val;
    }

    private Long getNullableLong(ResultSet rs, String col) throws SQLException {
        long val = rs.getLong(col);
        return rs.wasNull() ? null : val;
    }

    @Override
    public int create(DonHang entity) {
        String sql = "INSERT INTO dbo.DON_HANG " +
                     "(ma_don_hang, id_khach_hang, id_nhan_vien, id_ca_lam, id_khuyen_mai, kenh_ban, " +
                     "hinh_thuc_phuc_vu, so_ban_the_rung, gio_hen_lay, ma_nhan_do, tong_tien_mon, " +
                     "tien_giam_voucher, diem_su_dung, tien_giam_diem, tong_thanh_toan, diem_cong_them, " +
                     "phuong_thuc_tt, tien_khach_dua, tien_thoi_lai, trang_thai_tt, trang_thai_don, " +
                     "ly_do_huy, ghi_chu_hoan_tien, so_sao_danh_gia, noi_dung_danh_gia, ngay_tao) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            return JdbcUtil.executeUpdate(sql,
                    entity.getMaDonHang(),
                    entity.getIdKhachHang(),
                    entity.getIdNhanVien(),
                    entity.getIdCaLam(),
                    entity.getIdKhuyenMai(),
                    entity.getKenhBan(),
                    entity.getHinhThucPhucVu(),
                    entity.getSoBanTheRung(),
                    toTimestamp(entity.getGioHenLay()),
                    entity.getMaNhanDo(),
                    entity.getTongTienMon(),
                    entity.getTienGiamVoucher() != null ? entity.getTienGiamVoucher() : 0L,
                    entity.getDiemSuDung() != null ? entity.getDiemSuDung() : 0,
                    entity.getTienGiamDiem() != null ? entity.getTienGiamDiem() : 0L,
                    entity.getTongThanhToan(),
                    entity.getDiemCongThem() != null ? entity.getDiemCongThem() : 0,
                    entity.getPhuongThucTt(),
                    entity.getTienKhachDua(),
                    entity.getTienThoiLai(),
                    entity.getTrangThaiTt(),
                    entity.getTrangThaiDon(),
                    entity.getLyDoHuy(),
                    entity.getGhiChuHoanTien(),
                    entity.getSoSaoDanhGia(),
                    entity.getNoiDungDanhGia(),
                    entity.getNgayTao() != null ? toTimestamp(entity.getNgayTao()) : new Timestamp(System.currentTimeMillis()));
        } catch (SQLException e) {
            throw new RuntimeException("Loi them moi DON_HANG: " + e.getMessage(), e);
        }
    }

    @Override
    public int update(DonHang entity) {
        String sql = "UPDATE dbo.DON_HANG " +
                     "SET ma_don_hang = ?, id_khach_hang = ?, id_nhan_vien = ?, id_ca_lam = ?, id_khuyen_mai = ?, " +
                     "kenh_ban = ?, hinh_thuc_phuc_vu = ?, so_ban_the_rung = ?, gio_hen_lay = ?, ma_nhan_do = ?, " +
                     "tong_tien_mon = ?, tien_giam_voucher = ?, diem_su_dung = ?, tien_giam_diem = ?, " +
                     "tong_thanh_toan = ?, diem_cong_them = ?, phuong_thuc_tt = ?, tien_khach_dua = ?, " +
                     "tien_thoi_lai = ?, trang_thai_tt = ?, trang_thai_don = ?, ly_do_huy = ?, " +
                     "ghi_chu_hoan_tien = ?, so_sao_danh_gia = ?, noi_dung_danh_gia = ? " +
                     "WHERE id_don_hang = ?";
        try {
            return JdbcUtil.executeUpdate(sql,
                    entity.getMaDonHang(),
                    entity.getIdKhachHang(),
                    entity.getIdNhanVien(),
                    entity.getIdCaLam(),
                    entity.getIdKhuyenMai(),
                    entity.getKenhBan(),
                    entity.getHinhThucPhucVu(),
                    entity.getSoBanTheRung(),
                    toTimestamp(entity.getGioHenLay()),
                    entity.getMaNhanDo(),
                    entity.getTongTienMon(),
                    entity.getTienGiamVoucher(),
                    entity.getDiemSuDung(),
                    entity.getTienGiamDiem(),
                    entity.getTongThanhToan(),
                    entity.getDiemCongThem(),
                    entity.getPhuongThucTt(),
                    entity.getTienKhachDua(),
                    entity.getTienThoiLai(),
                    entity.getTrangThaiTt(),
                    entity.getTrangThaiDon(),
                    entity.getLyDoHuy(),
                    entity.getGhiChuHoanTien(),
                    entity.getSoSaoDanhGia(),
                    entity.getNoiDungDanhGia(),
                    entity.getIdDonHang());
        } catch (SQLException e) {
            throw new RuntimeException("Loi cap nhat DON_HANG: " + e.getMessage(), e);
        }
    }

    @Override
    public int delete(Long id) {
        String sql = "DELETE FROM dbo.DON_HANG WHERE id_don_hang = ?";
        try {
            return JdbcUtil.executeUpdate(sql, id);
        } catch (SQLException e) {
            throw new RuntimeException("Loi xoa DON_HANG: " + e.getMessage(), e);
        }
    }

    @Override
    public List<DonHang> findAll() {
        String sql = "SELECT * FROM dbo.DON_HANG ORDER BY ngay_tao DESC";
        return findBySql(sql);
    }

    @Override
    public DonHang findById(Long id) {
        String sql = "SELECT * FROM dbo.DON_HANG WHERE id_don_hang = ?";
        List<DonHang> list = findBySql(sql, id);
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public List<DonHang> findBySql(String sql, Object... values) {
        List<DonHang> list = new ArrayList<>();
        try (ResultSet rs = JdbcUtil.executeQuery(sql, values);
             Statement stmt = rs.getStatement();
             Connection conn = stmt.getConnection()) {
            while (rs.next()) {
                DonHang dh = new DonHang();
                dh.setIdDonHang(rs.getLong("id_don_hang"));
                dh.setMaDonHang(rs.getString("ma_don_hang"));
                dh.setIdKhachHang(getNullableInt(rs, "id_khach_hang"));
                dh.setIdNhanVien(getNullableInt(rs, "id_nhan_vien"));
                dh.setIdCaLam(getNullableInt(rs, "id_ca_lam"));
                dh.setIdKhuyenMai(getNullableInt(rs, "id_khuyen_mai"));
                dh.setKenhBan(rs.getString("kenh_ban"));
                dh.setHinhThucPhucVu(rs.getString("hinh_thuc_phuc_vu"));
                dh.setSoBanTheRung(rs.getNString("so_ban_the_rung"));
                dh.setGioHenLay(rs.getTimestamp("gio_hen_lay"));
                dh.setMaNhanDo(rs.getString("ma_nhan_do"));
                dh.setTongTienMon(rs.getLong("tong_tien_mon"));
                dh.setTienGiamVoucher(rs.getLong("tien_giam_voucher"));
                dh.setDiemSuDung(rs.getInt("diem_su_dung"));
                dh.setTienGiamDiem(rs.getLong("tien_giam_diem"));
                dh.setTongThanhToan(rs.getLong("tong_thanh_toan"));
                dh.setDiemCongThem(rs.getInt("diem_cong_them"));
                dh.setPhuongThucTt(rs.getString("phuong_thuc_tt"));
                dh.setTienKhachDua(getNullableLong(rs, "tien_khach_dua"));
                dh.setTienThoiLai(getNullableLong(rs, "tien_thoi_lai"));
                dh.setTrangThaiTt(rs.getString("trang_thai_tt"));
                dh.setTrangThaiDon(rs.getString("trang_thai_don"));
                dh.setLyDoHuy(rs.getNString("ly_do_huy"));
                dh.setGhiChuHoanTien(rs.getNString("ghi_chu_hoan_tien"));
                dh.setSoSaoDanhGia(getNullableInt(rs, "so_sao_danh_gia"));
                dh.setNoiDungDanhGia(rs.getNString("noi_dung_danh_gia"));
                dh.setNgayTao(rs.getTimestamp("ngay_tao"));
                list.add(dh);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Loi truy van DON_HANG: " + e.getMessage(), e);
        }
        return list;
    }

    public DonHang findByMaDonHang(String maDonHang) {
        String sql = "SELECT * FROM dbo.DON_HANG WHERE ma_don_hang = ?";
        List<DonHang> list = findBySql(sql, maDonHang);
        return list.isEmpty() ? null : list.get(0);
    }

    public List<DonHang> findByTrangThaiDon(String trangThaiDon) {
        String sql = "SELECT * FROM dbo.DON_HANG WHERE trang_thai_don = ? ORDER BY ngay_tao ASC";
        return findBySql(sql, trangThaiDon);
    }
}
