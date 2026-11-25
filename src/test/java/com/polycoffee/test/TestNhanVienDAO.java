package com.polycoffee.test;

import com.polycoffee.dao.NhanVienDAO;
import com.polycoffee.entity.NhanVien;

import java.util.Date;
import java.util.List;

/**
 * Lop kiem thu minh hoa day du cac phuong thuc CRUD va tim kiem trong NhanVienDAO (Bai 5 - Lab 3).
 *
 * @author Nam (Nhóm PolyCoffee - JAV102)
 */
public class TestNhanVienDAO {
    public static void main(String[] args) {
        System.out.println("===== TEST NhanVienDAO (Phu trach: Nam) =====");
        NhanVienDAO dao = new NhanVienDAO();

        // 1. Test create()
        System.out.println("=== 1. TEST create(NhanVien) ===");
        NhanVien nvMoi = new NhanVien(null, "NV_TEST", "staff_test", "test.nv@polycoffee.vn",
                "$2a$10$HashTest123", "Pham Nhan Vien Test", "0909998888",
                "Q.10, TP.HCM", "/assets/avatar/default.jpg", "STAFF",
                0, null, true, new Date());
        System.out.println("So dong them moi: " + dao.create(nvMoi));

        // 2. Test findAll()
        System.out.println("\n=== 2. TEST findAll() ===");
        dao.findAll().forEach(System.out::println);

        // 3. Test findBySql()
        System.out.println("\n=== 3. TEST findBySql() ===");
        List<NhanVien> list = dao.findBySql("SELECT * FROM dbo.NHAN_VIEN WHERE ma_nhan_vien = ?", "NV_TEST");
        if (!list.isEmpty()) {
            Integer idTest = list.get(0).getIdNhanVien();

            // 4. Test findById()
            System.out.println("\n=== 4. TEST findById() ===");
            NhanVien nv = dao.findById(idTest);
            System.out.println(nv);

            // 5. Test update()
            System.out.println("\n=== 5. TEST update() ===");
            nv.setHoTen("Pham Nhan Vien (Updated)");
            nv.setVaiTro("STAFF");
            nv.setDangHoatDong(false);
            System.out.println("So dong cap nhat: " + dao.update(nv));
            System.out.println("Sau cap nhat: " + dao.findById(idTest));

            // 6. Test findByTenDangNhap()
            System.out.println("\n=== 6. TEST findByTenDangNhap(String) ===");
            System.out.println("Tim theo ten dang nhap: " + dao.findByTenDangNhap("staff_test"));

            // 7. Test delete()
            System.out.println("\n=== 7. TEST delete() ===");
            System.out.println("So dong da xoa: " + dao.delete(idTest));
        }
    }
}
