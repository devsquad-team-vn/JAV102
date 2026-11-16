package com.polycoffee.dao;

import com.polycoffee.entity.NhatKyHeThong;
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
 * DAO thao tac CRUD voi bang NHAT_KY_HE_THONG trong CSDL polycoffee.
 *
 * @author Quý (Nhóm PolyCoffee - JAV102)
 */
public class NhatKyHeThongDAO implements CrudDAO<NhatKyHeThong, Long> {

    private Timestamp toTimestamp(Date date) {
        return date != null ? new Timestamp(date.getTime()) : new Timestamp(System.currentTimeMillis());
    }

    @Override
    public int create(NhatKyHeThong entity) {
        String sql = "INSERT INTO dbo.NHAT_KY_HE_THONG " +
                     "(id_nhan_vien, loai_hanh_dong, bang_tac_dong, ma_ban_ghi, " +
                     "du_lieu_cu, du_lieu_moi, dia_chi_ip, thoi_gian) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            return JdbcUtil.executeUpdate(sql,
                    entity.getIdNhanVien(),
                    entity.getLoaiHanhDong(),
                    entity.getBangTacDong(),
                    entity.getMaBanGhi(),
                    entity.getDuLieuCu(),
                    entity.getDuLieuMoi(),
                    entity.getDiaChiIp(),
                    toTimestamp(entity.getThoiGian()));
        } catch (SQLException e) {
            throw new RuntimeException("Loi them moi NHAT_KY_HE_THONG: " + e.getMessage(), e);
        }
    }

    @Override
    public int update(NhatKyHeThong entity) {
        String sql = "UPDATE dbo.NHAT_KY_HE_THONG " +
                     "SET id_nhan_vien = ?, loai_hanh_dong = ?, bang_tac_dong = ?, ma_ban_ghi = ?, " +
                     "du_lieu_cu = ?, du_lieu_moi = ?, dia_chi_ip = ?, thoi_gian = ? " +
                     "WHERE id_nhat_ky = ?";
        try {
            return JdbcUtil.executeUpdate(sql,
                    entity.getIdNhanVien(),
                    entity.getLoaiHanhDong(),
                    entity.getBangTacDong(),
                    entity.getMaBanGhi(),
                    entity.getDuLieuCu(),
                    entity.getDuLieuMoi(),
                    entity.getDiaChiIp(),
                    toTimestamp(entity.getThoiGian()),
                    entity.getIdNhatKy());
        } catch (SQLException e) {
            throw new RuntimeException("Loi cap nhat NHAT_KY_HE_THONG: " + e.getMessage(), e);
        }
    }

    @Override
    public int delete(Long id) {
        String sql = "DELETE FROM dbo.NHAT_KY_HE_THONG WHERE id_nhat_ky = ?";
        try {
            return JdbcUtil.executeUpdate(sql, id);
        } catch (SQLException e) {
            throw new RuntimeException("Loi xoa NHAT_KY_HE_THONG: " + e.getMessage(), e);
        }
    }

    @Override
    public List<NhatKyHeThong> findAll() {
        String sql = "SELECT * FROM dbo.NHAT_KY_HE_THONG ORDER BY thoi_gian DESC";
        return findBySql(sql);
    }

    @Override
    public NhatKyHeThong findById(Long id) {
        String sql = "SELECT * FROM dbo.NHAT_KY_HE_THONG WHERE id_nhat_ky = ?";
        List<NhatKyHeThong> list = findBySql(sql, id);
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public List<NhatKyHeThong> findBySql(String sql, Object... values) {
        List<NhatKyHeThong> list = new ArrayList<>();
        try (ResultSet rs = JdbcUtil.executeQuery(sql, values);
             Statement stmt = rs.getStatement();
             Connection conn = stmt.getConnection()) {
            while (rs.next()) {
                NhatKyHeThong nk = new NhatKyHeThong();
                nk.setIdNhatKy(rs.getLong("id_nhat_ky"));
                nk.setIdNhanVien(rs.getInt("id_nhan_vien"));
                nk.setLoaiHanhDong(rs.getString("loai_hanh_dong"));
                nk.setBangTacDong(rs.getString("bang_tac_dong"));
                nk.setMaBanGhi(rs.getString("ma_ban_ghi"));
                nk.setDuLieuCu(rs.getNString("du_lieu_cu"));
                nk.setDuLieuMoi(rs.getNString("du_lieu_moi"));
                nk.setDiaChiIp(rs.getString("dia_chi_ip"));
                nk.setThoiGian(rs.getTimestamp("thoi_gian"));
                list.add(nk);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Loi truy van NHAT_KY_HE_THONG: " + e.getMessage(), e);
        }
        return list;
    }

    public List<NhatKyHeThong> findByNhanVien(Integer idNhanVien) {
        String sql = "SELECT * FROM dbo.NHAT_KY_HE_THONG WHERE id_nhan_vien = ? ORDER BY thoi_gian DESC";
        return findBySql(sql, idNhanVien);
    }
}
