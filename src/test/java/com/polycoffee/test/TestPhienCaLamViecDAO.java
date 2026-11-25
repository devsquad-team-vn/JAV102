package com.polycoffee.test;

import com.polycoffee.dao.PhienCaLamViecDAO;
import com.polycoffee.entity.PhienCaLamViec;

import java.util.Date;
import java.util.List;

/**
 * Lop kiem thu minh hoa day du cac phuong thuc CRUD va tim kiem trong PhienCaLamViecDAO (Bai 5 - Lab 3).
 *
 * @author Nam (Nhóm PolyCoffee - JAV102)
 */
public class TestPhienCaLamViecDAO {
    public static void main(String[] args) {
        System.out.println("===== TEST PhienCaLamViecDAO (Phu trach: Nam) =====");
        PhienCaLamViecDAO dao = new PhienCaLamViecDAO();

        // 1. Test create() - Mo phien ca thu nghiem cho nhan vien ID = 2
        System.out.println("=== 1. TEST create(PhienCaLamViec) ===");
        PhienCaLamViec caMoi = new PhienCaLamViec(null, "CA_TEST_01", 2, new Date(), null,
                1000000L, 0L, 0L, null, null, "OPEN");
        System.out.println("So dong them moi: " + dao.create(caMoi));

        // 2. Test findAll()
        System.out.println("\n=== 2. TEST findAll() ===");
        dao.findAll().forEach(System.out::println);

        // 3. Test findBySql()
        System.out.println("\n=== 3. TEST findBySql() ===");
        List<PhienCaLamViec> list = dao.findBySql("SELECT * FROM dbo.PHIEN_CA_LAM_VIEC WHERE ma_ca_lam = ?", "CA_TEST_01");
        if (!list.isEmpty()) {
            Integer idTest = list.get(0).getIdCaLam();

            // 4. Test findById()
            System.out.println("\n=== 4. TEST findById() ===");
            PhienCaLamViec ca = dao.findById(idTest);
            System.out.println(ca);

            // 5. Test update() - Chot ca va ghi nhan tien mat thuc te
            System.out.println("\n=== 5. TEST update() ===");
            ca.setThoiGianKetCa(new Date());
            ca.setTongTienMatHt(500000L);
            ca.setTienMatThucTe(500000L);
            ca.setGhiChuChenhLech("Khop so quy phien ca test");
            ca.setTrangThai("CLOSED");
            System.out.println("So dong cap nhat: " + dao.update(ca));
            System.out.println("Sau cap nhat: " + dao.findById(idTest));

            // 6. Test findByMaCaLam()
            System.out.println("\n=== 6. TEST findByMaCaLam(String) ===");
            System.out.println("Tim theo ma phien ca: " + dao.findByMaCaLam("CA_TEST_01"));

            // 7. Test delete()
            System.out.println("\n=== 7. TEST delete() ===");
            System.out.println("So dong da xoa: " + dao.delete(idTest));
        }
    }
}
