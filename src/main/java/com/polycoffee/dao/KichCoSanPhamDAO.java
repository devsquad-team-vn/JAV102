package com.polycoffee.dao;

import com.polycoffee.entity.KichCoSanPham;
import com.polycoffee.util.JdbcUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO thao tac CRUD voi bang KICH_CO_SAN_PHAM trong CSDL polycoffee.
 *
 * @author Quang (Nhóm PolyCoffee - JAV102)
 */
public class KichCoSanPhamDAO implements CrudDAO<KichCoSanPham, Integer> {

    @Override
    public int create(KichCoSanPham entity) {
        String sql = "INSERT INTO dbo.KICH_CO_SAN_PHAM (id_san_pham, ten_kich_co, gia_ban, trang_thai) " +
                     "VALUES (?, ?, ?, ?)";
        try {
            return JdbcUtil.executeUpdate(sql,
                    entity.getIdSanPham(),
                    entity.getTenKichCo(),
                    entity.getGiaBan(),
                    entity.getTrangThai() != null ? entity.getTrangThai() : "AVAILABLE");
        } catch (SQLException e) {
            throw new RuntimeException("Loi them moi KICH_CO_SAN_PHAM: " + e.getMessage(), e);
        }
    }

    @Override
    public int update(KichCoSanPham entity) {
        String sql = "UPDATE dbo.KICH_CO_SAN_PHAM " +
                     "SET id_san_pham = ?, ten_kich_co = ?, gia_ban = ?, trang_thai = ? " +
                     "WHERE id_kich_co_sp = ?";
        try {
            return JdbcUtil.executeUpdate(sql,
                    entity.getIdSanPham(),
                    entity.getTenKichCo(),
                    entity.getGiaBan(),
                    entity.getTrangThai(),
                    entity.getIdKichCoSp());
        } catch (SQLException e) {
            throw new RuntimeException("Loi cap nhat KICH_CO_SAN_PHAM: " + e.getMessage(), e);
        }
    }

    @Override
    public int delete(Integer id) {
        String sql = "DELETE FROM dbo.KICH_CO_SAN_PHAM WHERE id_kich_co_sp = ?";
        try {
            return JdbcUtil.executeUpdate(sql, id);
        } catch (SQLException e) {
            throw new RuntimeException("Loi xoa KICH_CO_SAN_PHAM: " + e.getMessage(), e);
        }
    }

    @Override
    public List<KichCoSanPham> findAll() {
        String sql = "SELECT * FROM dbo.KICH_CO_SAN_PHAM ORDER BY id_san_pham ASC, gia_ban ASC";
        return findBySql(sql);
    }

    @Override
    public KichCoSanPham findById(Integer id) {
        String sql = "SELECT * FROM dbo.KICH_CO_SAN_PHAM WHERE id_kich_co_sp = ?";
        List<KichCoSanPham> list = findBySql(sql, id);
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public List<KichCoSanPham> findBySql(String sql, Object... values) {
        List<KichCoSanPham> list = new ArrayList<>();
        try (ResultSet rs = JdbcUtil.executeQuery(sql, values);
             Statement stmt = rs.getStatement();
             Connection conn = stmt.getConnection()) {
            while (rs.next()) {
                KichCoSanPham kc = new KichCoSanPham();
                kc.setIdKichCoSp(rs.getInt("id_kich_co_sp"));
                kc.setIdSanPham(rs.getInt("id_san_pham"));
                kc.setTenKichCo(rs.getNString("ten_kich_co"));
                kc.setGiaBan(rs.getLong("gia_ban"));
                kc.setTrangThai(rs.getString("trang_thai"));
                list.add(kc);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Loi truy van KICH_CO_SAN_PHAM: " + e.getMessage(), e);
        }
        return list;
    }

    public List<KichCoSanPham> findBySanPham(Integer idSanPham) {
        String sql = "SELECT * FROM dbo.KICH_CO_SAN_PHAM WHERE id_san_pham = ? ORDER BY gia_ban ASC";
        return findBySql(sql, idSanPham);
    }
}
