package com.polycoffee.test;

import com.polycoffee.dao.ChiTietDonHangDAO;
import com.polycoffee.entity.ChiTietDonHang;

import java.util.List;

/**
 * Lop kiem thu minh hoa day du cac phuong thuc CRUD va tim kiem trong ChiTietDonHangDAO (Bai 5 - Lab 3).
 *
 * @author Kiệt (Nhóm PolyCoffee - JAV102)
 */
public class TestChiTietDonHangDAO {
    public static void main(String[] args) {
        System.out.println("===== TEST ChiTietDonHangDAO (Phu trach: Kiet) =====");
        ChiTietDonHangDAO dao = new ChiTietDonHangDAO();

        // 1. Test create() - Them dong mon thu nghiem vao Don Hang ID = 2
        System.out.println("=== 1. TEST create(ChiTietDonHang) ===");
        ChiTietDonHang ctMoi = new ChiTietDonHang(null, 2L, 4, 1, 29000L, 0L,
                "50%", "50%", "Ghi chu pha che thu nghiem");
        System.out.println("So dong them moi: " + dao.create(ctMoi));

        // 2. Test findAll()
        System.out.println("\n=== 2. TEST findAll() ===");
        dao.findAll().forEach(System.out::println);

        // 3. Test findBySql()
        System.out.println("\n=== 3. TEST findBySql() ===");
        List<ChiTietDonHang> list = dao.findBySql(
                "SELECT * FROM dbo.CHI_TIET_DON_HANG WHERE id_don_hang = ? AND ghi_chu_pha_che = ?",
                2L, "Ghi chu pha che thu nghiem");
        if (!list.isEmpty()) {
            Long idTest = list.get(0).getIdCtDonHang();

            // 4. Test findById()
            System.out.println("\n=== 4. TEST findById() ===");
            ChiTietDonHang ct = dao.findById(idTest);
            System.out.println(ct);

            // 5. Test update()
            System.out.println("\n=== 5. TEST update() ===");
            ct.setSoLuong(2);
            ct.setMucDuong("70%");
            ct.setGhiChuPhaChe("Da cap nhat so luong & muc duong");
            System.out.println("So dong cap nhat: " + dao.update(ct));
            System.out.println("Sau cap nhat: " + dao.findById(idTest));

            // 6. Test findByDonHang()
            System.out.println("\n=== 6. TEST findByDonHang(Long) ===");
            dao.findByDonHang(2L).forEach(System.out::println);

            // 7. Test delete()
            System.out.println("\n=== 7. TEST delete() ===");
            System.out.println("So dong da xoa: " + dao.delete(idTest));
        }
    }
}
