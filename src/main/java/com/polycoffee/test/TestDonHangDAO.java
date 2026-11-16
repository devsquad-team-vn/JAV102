package com.polycoffee.test;

import com.polycoffee.dao.DonHangDAO;
import com.polycoffee.entity.DonHang;

import java.util.Date;
import java.util.List;

/**
 * Lop kiem thu minh hoa day du cac phuong thuc CRUD va tim kiem trong DonHangDAO (Bai 5 - Lab 3).
 *
 * @author Kiệt (Nhóm PolyCoffee - JAV102)
 */
public class TestDonHangDAO {
    public static void main(String[] args) {
        System.out.println("===== TEST DonHangDAO (Phu trach: Kiet) =====");
        DonHangDAO dao = new DonHangDAO();

        // 1. Test create() - Tao don hang thu nghiem POS
        System.out.println("=== 1. TEST create(DonHang) ===");
        DonHang dhMoi = new DonHang(null, "ORD-TEST-9999", 1, 2, 2, null,
                "POS", "TAKE_AWAY", "The rung 08", null, "PC-999",
                70000L, 0L, 0, 0L, 70000L, 7,
                "CASH", 100000L, 30000L, "PAID", "PREPARING",
                null, null, null, null, new Date());
        System.out.println("So dong them moi: " + dao.create(dhMoi));

        // 2. Test findAll()
        System.out.println("\n=== 2. TEST findAll() ===");
        dao.findAll().forEach(System.out::println);

        // 3. Test findBySql()
        System.out.println("\n=== 3. TEST findBySql() ===");
        List<DonHang> list = dao.findBySql("SELECT * FROM dbo.DON_HANG WHERE ma_don_hang = ?", "ORD-TEST-9999");
        if (!list.isEmpty()) {
            Long idTest = list.get(0).getIdDonHang();

            // 4. Test findById()
            System.out.println("\n=== 4. TEST findById() ===");
            DonHang dh = dao.findById(idTest);
            System.out.println(dh);

            // 5. Test update() - Chuyen trang thai don sang COMPLETED va danh gia 5 sao
            System.out.println("\n=== 5. TEST update() ===");
            dh.setTrangThaiDon("COMPLETED");
            dh.setSoSaoDanhGia(5);
            dh.setNoiDungDanhGia("Do uong ngon, pha che nhanh (Updated)");
            System.out.println("So dong cap nhat: " + dao.update(dh));
            System.out.println("Sau cap nhat: " + dao.findById(idTest));

            // 6. Test findByMaDonHang()
            System.out.println("\n=== 6. TEST findByMaDonHang(String) ===");
            System.out.println("Tim theo ma don: " + dao.findByMaDonHang("ORD-TEST-9999"));

            // 7. Test findByTrangThaiDon()
            System.out.println("\n=== 7. TEST findByTrangThaiDon(String) ===");
            dao.findByTrangThaiDon("PREPARING").forEach(System.out::println);

            // 8. Test delete()
            System.out.println("\n=== 8. TEST delete() ===");
            System.out.println("So dong da xoa: " + dao.delete(idTest));
        }
    }
}
