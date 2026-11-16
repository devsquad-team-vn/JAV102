package com.polycoffee.test;

import com.polycoffee.dao.DanhMucDAO;
import com.polycoffee.entity.DanhMuc;

import java.util.List;

/**
 * Lop kiem thu minh hoa day du cac phuong thuc CRUD va tim kiem trong DanhMucDAO (Bai 5 - Lab 3).
 *
 * @author Quang (Nhóm PolyCoffee - JAV102)
 */
public class TestDanhMucDAO {
    public static void main(String[] args) {
        System.out.println("===== TEST DanhMucDAO (Phu trach: Quang) =====");
        DanhMucDAO dao = new DanhMucDAO();

        // 1. Test create()
        System.out.println("=== 1. TEST create(DanhMuc) ===");
        DanhMuc dmMoi = new DanhMuc(null, "DM_TEST", "Nuoc ep trai cay Test",
                "Cac loai nuoc ep tuoi nguyen chat", 99, "ACTIVE");
        int rowsCreated = dao.create(dmMoi);
        System.out.println("So dong them moi: " + rowsCreated);

        // 2. Test findAll()
        System.out.println("\n=== 2. TEST findAll() ===");
        List<DanhMuc> tatCa = dao.findAll();
        for (DanhMuc item : tatCa) {
            System.out.println(item);
        }

        // 3. Test findBySql()
        System.out.println("\n=== 3. TEST findBySql(sql, values...) ===");
        List<DanhMuc> theoMa = dao.findBySql("SELECT * FROM dbo.DANH_MUC WHERE ma_danh_muc = ?", "DM_TEST");
        if (!theoMa.isEmpty()) {
            DanhMuc found = theoMa.get(0);
            Integer idTest = found.getIdDanhMuc();
            System.out.println("Tim thay qua findBySql: " + found);

            // 4. Test findById()
            System.out.println("\n=== 4. TEST findById(id) ===");
            DanhMuc chiTiet = dao.findById(idTest);
            System.out.println("Tim thay qua findById: " + chiTiet);

            // 5. Test update()
            System.out.println("\n=== 5. TEST update(DanhMuc) ===");
            chiTiet.setTenDanhMuc("Nuoc ep & Sinh to Test (Updated)");
            chiTiet.setTrangThai("INACTIVE");
            int rowsUpdated = dao.update(chiTiet);
            System.out.println("So dong cap nhat: " + rowsUpdated);
            System.out.println("Sau cap nhat: " + dao.findById(idTest));

            // 6. Test findByMaDanhMuc()
            System.out.println("\n=== 6. TEST findByMaDanhMuc(String) ===");
            System.out.println("Tim thay qua findByMaDanhMuc: " + dao.findByMaDanhMuc("DM_TEST"));

            // 7. Test delete()
            System.out.println("\n=== 7. TEST delete(id) ===");
            int rowsDeleted = dao.delete(idTest);
            System.out.println("So dong da xoa: " + rowsDeleted);
        }
    }
}
