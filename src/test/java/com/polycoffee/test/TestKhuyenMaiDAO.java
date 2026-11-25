package com.polycoffee.test;

import com.polycoffee.dao.KhuyenMaiDAO;
import com.polycoffee.entity.KhuyenMai;

import java.util.Date;
import java.util.List;

/**
 * Lop kiem thu minh hoa day du cac phuong thuc CRUD va tim kiem trong KhuyenMaiDAO (Bai 5 - Lab 3).
 *
 * @author Khoa (Nhóm PolyCoffee - JAV102)
 */
public class TestKhuyenMaiDAO {
    public static void main(String[] args) {
        System.out.println("===== TEST KhuyenMaiDAO (Phu trach: Khoa) =====");
        KhuyenMaiDAO dao = new KhuyenMaiDAO();
        Date now = new Date();
        Date nextMonth = new Date(now.getTime() + 30L * 24 * 60 * 60 * 1000);

        // 1. Test create()
        System.out.println("=== 1. TEST create(KhuyenMai) ===");
        KhuyenMai kmMoi = new KhuyenMai(null, "TEST15K", "Giam 15K don thu nghiem",
                "Ma giam gia danh cho kiem thu DAO", "FIXED_AMOUNT", 15000L, 15000L,
                50000L, "TIER_REWARD", "Bạc", 0, 100, 0, 1, now, nextMonth, "ACTIVE");
        System.out.println("So dong them moi: " + dao.create(kmMoi));

        // 2. Test findAll()
        System.out.println("\n=== 2. TEST findAll() ===");
        dao.findAll().forEach(System.out::println);

        // 3. Test findBySql()
        System.out.println("\n=== 3. TEST findBySql() ===");
        List<KhuyenMai> list = dao.findBySql("SELECT * FROM dbo.KHUYEN_MAI WHERE ma_code_km = ?", "TEST15K");
        if (!list.isEmpty()) {
            Integer idTest = list.get(0).getIdKhuyenMai();

            // 4. Test findById()
            System.out.println("\n=== 4. TEST findById() ===");
            KhuyenMai km = dao.findById(idTest);
            System.out.println(km);

            // 5. Test update()
            System.out.println("\n=== 5. TEST update() ===");
            km.setSoLuotDaDung(1);
            km.setTrangThai("INACTIVE");
            System.out.println("So dong cap nhat: " + dao.update(km));
            System.out.println("Sau cap nhat: " + dao.findById(idTest));

            // 6. Test findByMaCode()
            System.out.println("\n=== 6. TEST findByMaCode(String) ===");
            System.out.println("Tim theo ma code: " + dao.findByMaCode("TEST15K"));

            // 7. Test delete()
            System.out.println("\n=== 7. TEST delete() ===");
            System.out.println("So dong da xoa: " + dao.delete(idTest));
        }
    }
}
