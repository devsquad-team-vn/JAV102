package com.polycoffee.test;

import com.polycoffee.dao.ToppingDAO;
import com.polycoffee.entity.Topping;

import java.util.List;

/**
 * Lop kiem thu minh hoa day du cac phuong thuc CRUD va tim kiem trong ToppingDAO (Assignment 1 - Phu trach: Quy).
 *
 * @author Quý (Nhóm PolyCoffee - JAV102)
 */
public class TestToppingDAO {
    public static void main(String[] args) {
        System.out.println("===== TEST ToppingDAO (Phu trach: Quy) =====");
        ToppingDAO dao = new ToppingDAO();

        // 1. Test create()
        System.out.println("=== 1. TEST create(Topping) ===");
        Topping tpMoi = new Topping(null, "TP_TEST", "Tran chau duong den Test",
                12000L, "/assets/img/tp-test.jpg", "AVAILABLE");
        System.out.println("So dong them moi: " + dao.create(tpMoi));

        // 2. Test findAll()
        System.out.println("\n=== 2. TEST findAll() ===");
        dao.findAll().forEach(System.out::println);

        // 3. Test findBySql()
        System.out.println("\n=== 3. TEST findBySql() ===");
        List<Topping> list = dao.findBySql("SELECT * FROM dbo.TOPPING WHERE ma_topping = ?", "TP_TEST");
        if (!list.isEmpty()) {
            Integer idTest = list.get(0).getIdTopping();

            // 4. Test findById()
            System.out.println("\n=== 4. TEST findById() ===");
            Topping tp = dao.findById(idTest);
            System.out.println(tp);

            // 5. Test update()
            System.out.println("\n=== 5. TEST update() ===");
            tp.setGiaTopping(15000L);
            tp.setTrangThai("OUT_OF_STOCK");
            System.out.println("So dong cap nhat: " + dao.update(tp));
            System.out.println("Sau cap nhat: " + dao.findById(idTest));

            // 6. Test findByMaTopping()
            System.out.println("\n=== 6. TEST findByMaTopping(String) ===");
            System.out.println("Tim theo ma topping: " + dao.findByMaTopping("TP_TEST"));

            // 7. Test delete()
            System.out.println("\n=== 7. TEST delete() ===");
            System.out.println("So dong da xoa: " + dao.delete(idTest));
        }
    }
}
