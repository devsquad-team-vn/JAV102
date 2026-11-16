package com.polycoffee.test;

import com.polycoffee.dao.SanPhamDAO;
import com.polycoffee.entity.SanPham;

import java.util.Date;
import java.util.List;

/**
 * Lop kiem thu minh hoa day du cac phuong thuc CRUD va tim kiem trong SanPhamDAO (Bai 5 - Lab 3).
 *
 * @author Quang (Nhóm PolyCoffee - JAV102)
 */
public class TestSanPhamDAO {
    public static void main(String[] args) {
        System.out.println("===== TEST SanPhamDAO (Phu trach: Quang) =====");
        SanPhamDAO dao = new SanPhamDAO();

        // 1. Test create()
        System.out.println("=== 1. TEST create(SanPham) ===");
        SanPham spMoi = new SanPham(null, "SP_TEST", 1, "Ca phe muoi Test",
                "/assets/img/ca-phe-muoi.jpg", "Ca phe phin ket hop kem muoi beo",
                true, true, false, true, "AVAILABLE", new Date());
        System.out.println("So dong them moi: " + dao.create(spMoi));

        // 2. Test findAll()
        System.out.println("\n=== 2. TEST findAll() ===");
        dao.findAll().forEach(System.out::println);

        // 3. Test findBySql()
        System.out.println("\n=== 3. TEST findBySql() ===");
        List<SanPham> dsTim = dao.findBySql("SELECT * FROM dbo.SAN_PHAM WHERE ma_san_pham = ?", "SP_TEST");
        if (!dsTim.isEmpty()) {
            Integer idTest = dsTim.get(0).getIdSanPham();
            System.out.println("Tim thay: " + dsTim.get(0));

            // 4. Test findById()
            System.out.println("\n=== 4. TEST findById() ===");
            SanPham sp = dao.findById(idTest);
            System.out.println(sp);

            // 5. Test update()
            System.out.println("\n=== 5. TEST update() ===");
            sp.setTenSanPham("Ca phe kem muoi Da Lat (Updated)");
            sp.setLaMonBanChay(true);
            sp.setTrangThai("OUT_OF_STOCK");
            System.out.println("So dong cap nhat: " + dao.update(sp));
            System.out.println("Sau cap nhat: " + dao.findById(idTest));

            // 6. Test findByMaSanPham()
            System.out.println("\n=== 6. TEST findByMaSanPham(String) ===");
            System.out.println("Tim theo ma: " + dao.findByMaSanPham("SP_TEST"));

            // 7. Test findByDanhMuc()
            System.out.println("\n=== 7. TEST findByDanhMuc(Integer) ===");
            dao.findByDanhMuc(1).forEach(System.out::println);

            // 8. Test delete()
            System.out.println("\n=== 8. TEST delete() ===");
            System.out.println("So dong da xoa: " + dao.delete(idTest));
        }
    }
}
