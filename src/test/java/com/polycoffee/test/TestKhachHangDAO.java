package com.polycoffee.test;

import com.polycoffee.dao.KhachHangDAO;
import com.polycoffee.entity.KhachHang;

import java.util.Date;
import java.util.List;

/**
 * Lop kiem thu minh hoa day du cac phuong thuc CRUD va tim kiem trong KhachHangDAO (Bai 5 - Lab 3).
 *
 * @author Khoa (Nhóm PolyCoffee - JAV102)
 */
public class TestKhachHangDAO {
    public static void main(String[] args) {
        System.out.println("===== TEST KhachHangDAO (Phu trach: Khoa) =====");
        KhachHangDAO dao = new KhachHangDAO();

        // 1. Test create()
        System.out.println("=== 1. TEST create(KhachHang) ===");
        KhachHang khMoi = new KhachHang(null, "KH_TEST", "0999888777", "Nguyen Khach Test",
                "khtest@gmail.com", "$2a$10$HashKHTest", new Date(), "/assets/avatar/kh.jpg",
                50, 50, "Đồng", "123456", true, 0, null, true, new Date());
        System.out.println("So dong them moi: " + dao.create(khMoi));

        // 2. Test findAll()
        System.out.println("\n=== 2. TEST findAll() ===");
        dao.findAll().forEach(System.out::println);

        // 3. Test findBySql()
        System.out.println("\n=== 3. TEST findBySql() ===");
        List<KhachHang> list = dao.findBySql("SELECT * FROM dbo.KHACH_HANG WHERE ma_khach_hang = ?", "KH_TEST");
        if (!list.isEmpty()) {
            Integer idTest = list.get(0).getIdKhachHang();

            // 4. Test findById()
            System.out.println("\n=== 4. TEST findById() ===");
            KhachHang kh = dao.findById(idTest);
            System.out.println(kh);

            // 5. Test update()
            System.out.println("\n=== 5. TEST update() ===");
            kh.setDiemKhaDung(150);
            kh.setTongDiemTichLuy(250);
            kh.setHangThanhVien("Bạc");
            System.out.println("So dong cap nhat: " + dao.update(kh));
            System.out.println("Sau cap nhat: " + dao.findById(idTest));

            // 6. Test findBySoDienThoai()
            System.out.println("\n=== 6. TEST findBySoDienThoai(String) ===");
            System.out.println("Tim theo SDT: " + dao.findBySoDienThoai("0999888777"));

            // 7. Test delete()
            System.out.println("\n=== 7. TEST delete() ===");
            System.out.println("So dong da xoa: " + dao.delete(idTest));
        }
    }
}
