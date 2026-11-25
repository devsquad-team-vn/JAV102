package com.polycoffee.test;

import com.polycoffee.dao.ChiTietToppingDAO;
import com.polycoffee.entity.ChiTietTopping;

import java.util.List;

/**
 * Lop kiem thu minh hoa day du cac phuong thuc CRUD va tim kiem trong ChiTietToppingDAO (Assignment 1 - Phu trach: Quy).
 *
 * @author Quý (Nhóm PolyCoffee - JAV102)
 */
public class TestChiTietToppingDAO {
    public static void main(String[] args) {
        System.out.println("===== TEST ChiTietToppingDAO (Phu trach: Quy) =====");
        ChiTietToppingDAO dao = new ChiTietToppingDAO();

        // 1. Test create() - Them topping ID = 2 vao chi tiet don hang ID = 1
        System.out.println("=== 1. TEST create(ChiTietTopping) ===");
        ChiTietTopping ctpMoi = new ChiTietTopping(null, 1L, 2, 1, 12000L);
        System.out.println("So dong them moi: " + dao.create(ctpMoi));

        // 2. Test findAll()
        System.out.println("\n=== 2. TEST findAll() ===");
        dao.findAll().forEach(System.out::println);

        // 3. Test findBySql()
        System.out.println("\n=== 3. TEST findBySql() ===");
        List<ChiTietTopping> list = dao.findBySql(
                "SELECT * FROM dbo.CHI_TIET_TOPPING WHERE id_ct_don_hang = ? AND id_topping = ?",
                1L, 2);
        if (!list.isEmpty()) {
            Long idTest = list.get(0).getIdCtTopping();

            // 4. Test findById()
            System.out.println("\n=== 4. TEST findById() ===");
            ChiTietTopping ctp = dao.findById(idTest);
            System.out.println(ctp);

            // 5. Test update()
            System.out.println("\n=== 5. TEST update() ===");
            ctp.setSoLuong(2);
            System.out.println("So dong cap nhat: " + dao.update(ctp));
            System.out.println("Sau cap nhat: " + dao.findById(idTest));

            // 6. Test findByCtDonHang()
            System.out.println("\n=== 6. TEST findByCtDonHang(Long) ===");
            dao.findByCtDonHang(1L).forEach(System.out::println);

            // 7. Test delete()
            System.out.println("\n=== 7. TEST delete() ===");
            System.out.println("So dong da xoa: " + dao.delete(idTest));
        }
    }
}
