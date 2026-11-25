package com.polycoffee.dao;

import com.polycoffee.entity.Topping;
import com.polycoffee.util.JdbcUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO thao tac CRUD voi bang TOPPING trong CSDL polycoffee (Assignment 1 - Phu trach: Quy).
 *
 * @author Quý (Nhóm PolyCoffee - JAV102)
 */
public class ToppingDAO implements CrudDAO<Topping, Integer> {

    @Override
    public int create(Topping entity) {
        String sql = "INSERT INTO dbo.TOPPING (ma_topping, ten_topping, gia_topping, hinh_anh, trang_thai) " +
                "VALUES (?, ?, ?, ?, ?)";
        try {
            return JdbcUtil.executeUpdate(sql,
                    entity.getMaTopping(),
                    entity.getTenTopping(),
                    entity.getGiaTopping(),
                    entity.getHinhAnh(),
                    entity.getTrangThai() != null ? entity.getTrangThai() : "AVAILABLE");
        } catch (SQLException e) {
            throw new RuntimeException("Loi them moi TOPPING: " + e.getMessage(), e);
        }
    }

    @Override
    public int update(Topping entity) {
        String sql = "UPDATE dbo.TOPPING " +
                "SET ma_topping = ?, ten_topping = ?, gia_topping = ?, hinh_anh = ?, trang_thai = ? " +
                "WHERE id_topping = ?";
        try {
            return JdbcUtil.executeUpdate(sql,
                    entity.getMaTopping(),
                    entity.getTenTopping(),
                    entity.getGiaTopping(),
                    entity.getHinhAnh(),
                    entity.getTrangThai(),
                    entity.getIdTopping());
        } catch (SQLException e) {
            throw new RuntimeException("Loi cap nhat TOPPING: " + e.getMessage(), e);
        }
    }

    @Override
    public int delete(Integer id) {
        String sql = "DELETE FROM dbo.TOPPING WHERE id_topping = ?";
        try {
            return JdbcUtil.executeUpdate(sql, id);
        } catch (SQLException e) {
            throw new RuntimeException("Loi xoa TOPPING: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Topping> findAll() {
        String sql = "SELECT * FROM dbo.TOPPING ORDER BY id_topping ASC";
        return findBySql(sql);
    }

    @Override
    public Topping findById(Integer id) {
        String sql = "SELECT * FROM dbo.TOPPING WHERE id_topping = ?";
        List<Topping> list = findBySql(sql, id);
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public List<Topping> findBySql(String sql, Object... values) {
        List<Topping> list = new ArrayList<>();
        try (ResultSet rs = JdbcUtil.executeQuery(sql, values);
             Statement stmt = rs.getStatement();
             Connection conn = stmt.getConnection()) {
            while (rs.next()) {
                Topping tp = new Topping();
                tp.setIdTopping(rs.getInt("id_topping"));
                tp.setMaTopping(rs.getString("ma_topping"));
                tp.setTenTopping(rs.getNString("ten_topping"));
                tp.setGiaTopping(rs.getLong("gia_topping"));
                tp.setHinhAnh(rs.getString("hinh_anh"));
                tp.setTrangThai(rs.getString("trang_thai"));
                list.add(tp);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Loi truy van TOPPING: " + e.getMessage(), e);
        }
        return list;
    }

    public Topping findByMaTopping(String maTopping) {
        String sql = "SELECT * FROM dbo.TOPPING WHERE ma_topping = ?";
        List<Topping> list = findBySql(sql, maTopping);
        return list.isEmpty() ? null : list.get(0);
    }
}
