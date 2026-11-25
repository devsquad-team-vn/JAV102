package com.polycoffee.dao;

import com.polycoffee.entity.ChiTietTopping;
import com.polycoffee.util.JdbcUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO thao tac CRUD voi bang CHI_TIET_TOPPING trong CSDL polycoffee (Assignment 1 - Phu trach: Quy).
 *
 * @author Quý (Nhóm PolyCoffee - JAV102)
 */
public class ChiTietToppingDAO implements CrudDAO<ChiTietTopping, Long> {

    @Override
    public int create(ChiTietTopping entity) {
        String sql = "INSERT INTO dbo.CHI_TIET_TOPPING (id_ct_don_hang, id_topping, so_luong, don_gia_chot) " +
                     "VALUES (?, ?, ?, ?)";
        try {
            return JdbcUtil.executeUpdate(sql,
                    entity.getIdCtDonHang(),
                    entity.getIdTopping(),
                    entity.getSoLuong(),
                    entity.getDonGiaChot());
        } catch (SQLException e) {
            throw new RuntimeException("Loi them moi CHI_TIET_TOPPING: " + e.getMessage(), e);
        }
    }

    @Override
    public int update(ChiTietTopping entity) {
        String sql = "UPDATE dbo.CHI_TIET_TOPPING " +
                     "SET id_ct_don_hang = ?, id_topping = ?, so_luong = ?, don_gia_chot = ? " +
                     "WHERE id_ct_topping = ?";
        try {
            return JdbcUtil.executeUpdate(sql,
                    entity.getIdCtDonHang(),
                    entity.getIdTopping(),
                    entity.getSoLuong(),
                    entity.getDonGiaChot(),
                    entity.getIdCtTopping());
        } catch (SQLException e) {
            throw new RuntimeException("Loi cap nhat CHI_TIET_TOPPING: " + e.getMessage(), e);
        }
    }

    @Override
    public int delete(Long id) {
        String sql = "DELETE FROM dbo.CHI_TIET_TOPPING WHERE id_ct_topping = ?";
        try {
            return JdbcUtil.executeUpdate(sql, id);
        } catch (SQLException e) {
            throw new RuntimeException("Loi xoa CHI_TIET_TOPPING: " + e.getMessage(), e);
        }
    }

    @Override
    public List<ChiTietTopping> findAll() {
        String sql = "SELECT * FROM dbo.CHI_TIET_TOPPING ORDER BY id_ct_topping ASC";
        return findBySql(sql);
    }

    @Override
    public ChiTietTopping findById(Long id) {
        String sql = "SELECT * FROM dbo.CHI_TIET_TOPPING WHERE id_ct_topping = ?";
        List<ChiTietTopping> list = findBySql(sql, id);
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public List<ChiTietTopping> findBySql(String sql, Object... values) {
        List<ChiTietTopping> list = new ArrayList<>();
        try (ResultSet rs = JdbcUtil.executeQuery(sql, values);
             Statement stmt = rs.getStatement();
             Connection conn = stmt.getConnection()) {
            while (rs.next()) {
                ChiTietTopping ctp = new ChiTietTopping();
                ctp.setIdCtTopping(rs.getLong("id_ct_topping"));
                ctp.setIdCtDonHang(rs.getLong("id_ct_don_hang"));
                ctp.setIdTopping(rs.getInt("id_topping"));
                ctp.setSoLuong(rs.getInt("so_luong"));
                ctp.setDonGiaChot(rs.getLong("don_gia_chot"));
                list.add(ctp);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Loi truy van CHI_TIET_TOPPING: " + e.getMessage(), e);
        }
        return list;
    }

    public List<ChiTietTopping> findByCtDonHang(Long idCtDonHang) {
        String sql = "SELECT * FROM dbo.CHI_TIET_TOPPING WHERE id_ct_don_hang = ?";
        return findBySql(sql, idCtDonHang);
    }
}
