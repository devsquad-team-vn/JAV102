package com.polycoffee.test;

import com.polycoffee.dao.ViVoucherKhachHangDAO;
import com.polycoffee.entity.ViVoucherKhachHang;

import java.util.Date;
import java.util.List;

/**
 * Lop kiem thu minh hoa day du cac phuong thuc CRUD va tim kiem trong ViVoucherKhachHangDAO (Bai 5 - Lab 3).
 *
 * @author Khoa (Nhóm PolyCoffee - JAV102)
 */
public class TestViVoucherKhachHangDAO {
    public static void main(String[] args) {
        System.out.println("===== TEST ViVoucherKhachHangDAO (Phu trach: Khoa) =====");
        ViVoucherKhachHangDAO dao = new ViVoucherKhachHangDAO();
        Date now = new Date();
        Date nextWeek = new Date(now.getTime() + 7L * 24 * 60 * 60 * 1000);

        // 1. Test create()
        System.out.println("=== 1. TEST create(ViVoucherKhachHang) ===");
        ViVoucherKhachHang vvMoi = new ViVoucherKhachHang(null, 1, 2, "REDEEM_POINTS", now, nextWeek, "UNUSED");
        System.out.println("So dong them moi: " + dao.create(vvMoi));

        // 2. Test findAll()
        System.out.println("\n=== 2. TEST findAll() ===");
        dao.findAll().forEach(System.out::println);

        // 3. Test findBySql()
        System.out.println("\n=== 3. TEST findBySql() ===");
        List<ViVoucherKhachHang> list = dao.findBySql(
                "SELECT * FROM dbo.VI_VOUCHER_KHACH_HANG WHERE id_khach_hang = ? AND id_khuyen_mai = ?",
                1, 2);
        if (!list.isEmpty()) {
            Long idTest = list.get(0).getIdViVoucher();

            // 4. Test findById()
            System.out.println("\n=== 4. TEST findById() ===");
            ViVoucherKhachHang vv = dao.findById(idTest);
            System.out.println(vv);

            // 5. Test update()
            System.out.println("\n=== 5. TEST update() ===");
            vv.setTrangThai("USED");
            System.out.println("So dong cap nhat: " + dao.update(vv));
            System.out.println("Sau cap nhat: " + dao.findById(idTest));

            // 6. Test findByKhachHang()
            System.out.println("\n=== 6. TEST findByKhachHang(Integer) ===");
            dao.findByKhachHang(1).forEach(System.out::println);

            // 7. Test delete()
            System.out.println("\n=== 7. TEST delete() ===");
            System.out.println("So dong da xoa: " + dao.delete(idTest));
        }
    }
}
