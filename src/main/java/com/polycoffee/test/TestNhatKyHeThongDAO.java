package com.polycoffee.test;

import com.polycoffee.dao.NhatKyHeThongDAO;
import com.polycoffee.entity.NhatKyHeThong;

import java.util.Date;
import java.util.List;

/**
 * Lop kiem thu minh hoa day du cac phuong thuc CRUD va tim kiem trong NhatKyHeThongDAO (Bai 5 - Lab 3).
 *
 * @author Quý (Nhóm PolyCoffee - JAV102)
 */
public class TestNhatKyHeThongDAO {
    public static void main(String[] args) {
        System.out.println("===== TEST NhatKyHeThongDAO (Phu trach: Quy) =====");
        NhatKyHeThongDAO dao = new NhatKyHeThongDAO();

        // 1. Test create()
        System.out.println("=== 1. TEST create(NhatKyHeThong) ===");
        NhatKyHeThong nkMoi = new NhatKyHeThong(null, 1, "TEST_ACTION", "DANH_MUC", "DM_TEST",
                null, "{\"trang_thai\":\"ACTIVE\"}", "127.0.0.1", new Date());
        System.out.println("So dong them moi: " + dao.create(nkMoi));

        // 2. Test findAll()
        System.out.println("\n=== 2. TEST findAll() ===");
        dao.findAll().forEach(System.out::println);

        // 3. Test findBySql()
        System.out.println("\n=== 3. TEST findBySql() ===");
        List<NhatKyHeThong> list = dao.findBySql(
                "SELECT * FROM dbo.NHAT_KY_HE_THONG WHERE loai_hanh_dong = ? AND ma_ban_ghi = ?",
                "TEST_ACTION", "DM_TEST");
        if (!list.isEmpty()) {
            Long idTest = list.get(0).getIdNhatKy();

            // 4. Test findById()
            System.out.println("\n=== 4. TEST findById() ===");
            NhatKyHeThong nk = dao.findById(idTest);
            System.out.println(nk);

            // 5. Test update()
            System.out.println("\n=== 5. TEST update() ===");
            nk.setDuLieuMoi("{\"trang_thai\":\"INACTIVE\"}");
            nk.setDiaChiIp("192.168.1.99");
            System.out.println("So dong cap nhat: " + dao.update(nk));
            System.out.println("Sau cap nhat: " + dao.findById(idTest));

            // 6. Test findByNhanVien()
            System.out.println("\n=== 6. TEST findByNhanVien(Integer) ===");
            dao.findByNhanVien(1).forEach(System.out::println);

            // 7. Test delete()
            System.out.println("\n=== 7. TEST delete() ===");
            System.out.println("So dong da xoa: " + dao.delete(idTest));
        }
    }
}
