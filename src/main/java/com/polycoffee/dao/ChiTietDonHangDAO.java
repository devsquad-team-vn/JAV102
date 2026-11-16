package com.polycoffee.dao;

import com.polycoffee.entity.ChiTietDonHang;
import com.polycoffee.util.JdbcUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO thao tac CRUD voi bang CHI_TIET_DON_HANG trong CSDL polycoffee.
 *
 * @author Kiệt (Nhóm PolyCoffee - JAV102)
 */
public class ChiTietDonHangDAO implements CrudDAO<ChiTietDonHang, Long> {

    @Override
    public int create(ChiTietDonHang entity) {
        String sql = "INSERT INTO dbo.CHI_TIET_DON_HANG " +
                     "(id_don_hang, id_kich_co_sp, so_luong, don_gia_chot, tong_tien_topping, " +
                     "muc_duong, muc_da, ghi_chu_pha_che) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            return JdbcUtil.executeUpdate(sql,
                    entity.getIdDonHang(),
                    entity.getIdKichCoSp(),
                    entity.getSoLuong(),
                    entity.getDonGiaChot(),
                    entity.getTongTienTopping() != null ? entity.getTongTienTopping() : 0L,
                    entity.getMucDuong(),
                    entity.getMucDa(),
                    entity.getGhiChuPhaChe());
        } catch (SQLException e) {
            throw new RuntimeException("Loi them moi CHI_TIET_DON_HANG: " + e.getMessage(), e);
        }
    }

    @Override
    public int update(ChiTietDonHang entity) {
        String sql = "UPDATE dbo.CHI_TIET_DON_HANG " +
                     "SET id_don_hang = ?, id_kich_co_sp = ?, so_luong = ?, don_gia_chot = ?, " +
                     "tong_tien_topping = ?, muc_duong = ?, muc_da = ?, ghi_chu_pha_che = ? " +
                     "WHERE id_ct_don_hang = ?";
        try {
            return JdbcUtil.executeUpdate(sql,
                    entity.getIdDonHang(),
                    entity.getIdKichCoSp(),
                    entity.getSoLuong(),
                    entity.getDonGiaChot(),
                    entity.getTongTienTopping(),
                    entity.getMucDuong(),
                    entity.getMucDa(),
                    entity.getGhiChuPhaChe(),
                    entity.getIdCtDonHang());
        } catch (SQLException e) {
            throw new RuntimeException("Loi cap nhat CHI_TIET_DON_HANG: " + e.getMessage(), e);
        }
    }

    @Override
    public int delete(Long id) {
        String sql = "DELETE FROM dbo.CHI_TIET_DON_HANG WHERE id_ct_don_hang = ?";
        try {
            return JdbcUtil.executeUpdate(sql, id);
        } catch (SQLException e) {
            throw new RuntimeException("Loi xoa CHI_TIET_DON_HANG: " + e.getMessage(), e);
        }
    }

    @Override
    public List<ChiTietDonHang> findAll() {
        String sql = "SELECT * FROM dbo.CHI_TIET_DON_HANG ORDER BY id_ct_don_hang ASC";
        return findBySql(sql);
    }

    @Override
    public ChiTietDonHang findById(Long id) {
        String sql = "SELECT * FROM dbo.CHI_TIET_DON_HANG WHERE id_ct_don_hang = ?";
        List<ChiTietDonHang> list = findBySql(sql, id);
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public List<ChiTietDonHang> findBySql(String sql, Object... values) {
        List<ChiTietDonHang> list = new ArrayList<>();
        try (ResultSet rs = JdbcUtil.executeQuery(sql, values);
             Statement stmt = rs.getStatement();
             Connection conn = stmt.getConnection()) {
            while (rs.next()) {
                ChiTietDonHang ct = new ChiTietDonHang();
                ct.setIdCtDonHang(rs.getLong("id_ct_don_hang"));
                ct.setIdDonHang(rs.getLong("id_don_hang"));
                ct.setIdKichCoSp(rs.getInt("id_kich_co_sp"));
                ct.setSoLuong(rs.getInt("so_luong"));
                ct.setDonGiaChot(rs.getLong("don_gia_chot"));
                ct.setTongTienTopping(rs.getLong("tong_tien_topping"));
                ct.setMucDuong(rs.getNString("muc_duong"));
                ct.setMucDa(rs.getNString("muc_da"));
                ct.setGhiChuPhaChe(rs.getNString("ghi_chu_pha_che"));
                list.add(ct);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Loi truy van CHI_TIET_DON_HANG: " + e.getMessage(), e);
        }
        return list;
    }

    public List<ChiTietDonHang> findByDonHang(Long idDonHang) {
        String sql = "SELECT * FROM dbo.CHI_TIET_DON_HANG WHERE id_don_hang = ?";
        return findBySql(sql, idDonHang);
    }
}
