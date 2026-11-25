package com.polycoffee.test;

import com.polycoffee.dao.KichCoSanPhamDAO;
import com.polycoffee.entity.KichCoSanPham;

import java.util.List;

/**
 * Lop kiem thu minh hoa day du cac phuong thuc CRUD va tim kiem trong KichCoSanPhamDAO (Bai 5 - Lab 3).
 *
 * @author Quang (Nhóm PolyCoffee - JAV102)
 */
public class TestKichCoSanPhamDAO {
    public static void main(String[] args) {
        System.out.println("===== TEST KichCoSanPhamDAO (Phu trach: Quang) =====");
        KichCoSanPhamDAO dao = new KichCoSanPhamDAO();

        // 1. Test create() - Them size XL thu nghiem cho san pham ID = 1
        System.out.println("=== 1. TEST create(KichCoSanPham) ===");
        KichCoSanPham kcMoi = new KichCoSanPham(null, 1, "Size XL Test", 49000L, "AVAILABLE");
        System.out.println("So dong them moi: " + dao.create(kcMoi));

        // 2. Test findAll()
        System.out.println("\n=== 2. TEST findAll() ===");
        dao.findAll().forEach(System.out::println);

        // 3. Test findBySql()
        System.out.println("\n=== 3. TEST findBySql() ===");
        List<KichCoSanPham> list = dao.findBySql(
                "SELECT * FROM dbo.KICH_CO_SAN_PHAM WHERE id_san_pham = ? AND ten_kich_co = ?",
                1, "Size XL Test");
        if (!list.isEmpty()) {
            Integer idTest = list.get(0).getIdKichCoSp();

            // 4. Test findById()
            System.out.println("\n=== 4. TEST findById() ===");
            KichCoSanPham kc = dao.findById(idTest);
            System.out.println(kc);

            // 5. Test update()
            System.out.println("\n=== 5. TEST update() ===");
            kc.setGiaBan(52000L);
            kc.setTrangThai("INACTIVE");
            System.out.println("So dong cap nhat: " + dao.update(kc));
            System.out.println("Sau cap nhat: " + dao.findById(idTest));

            // 6. Test findBySanPham()
            System.out.println("\n=== 6. TEST findBySanPham(Integer) ===");
            dao.findBySanPham(1).forEach(System.out::println);

            // 7. Test delete()
            System.out.println("\n=== 7. TEST delete() ===");
            System.out.println("So dong da xoa: " + dao.delete(idTest));
        }
    }
}
