package com.polycoffee.service;

import com.polycoffee.dao.DanhMucDAO;
import com.polycoffee.dao.KhuyenMaiDAO;
import com.polycoffee.dao.KichCoSanPhamDAO;
import com.polycoffee.dao.SanPhamDAO;
import com.polycoffee.dao.ToppingDAO;
import com.polycoffee.entity.DanhMuc;
import com.polycoffee.entity.KhuyenMai;
import com.polycoffee.entity.KichCoSanPham;
import com.polycoffee.entity.SanPham;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Tang Service truy van du lieu thuc don trang chu cho HomeServlet.
 * Luong: HomeServlet -> HomeService -> (DanhMucDAO, SanPhamDAO, KichCoSanPhamDAO, ToppingDAO, KhuyenMaiDAO) -> DB.
 *
 * @author Kiệt (Nhóm PolyCoffee - JAV102)
 */
public class HomeService {

    private final DanhMucDAO danhMucDAO;
    private final SanPhamDAO sanPhamDAO;
    private final KichCoSanPhamDAO kichCoDAO;
    private final ToppingDAO toppingDAO;
    private final KhuyenMaiDAO khuyenMaiDAO;

    public HomeService() {
        this(new DanhMucDAO(), new SanPhamDAO(), new KichCoSanPhamDAO(), new ToppingDAO(), new KhuyenMaiDAO());
    }

    public HomeService(DanhMucDAO danhMucDAO, SanPhamDAO sanPhamDAO, KichCoSanPhamDAO kichCoDAO,
                       ToppingDAO toppingDAO, KhuyenMaiDAO khuyenMaiDAO) {
        this.danhMucDAO = danhMucDAO;
        this.sanPhamDAO = sanPhamDAO;
        this.kichCoDAO = kichCoDAO;
        this.toppingDAO = toppingDAO;
        this.khuyenMaiDAO = khuyenMaiDAO;
    }

    public HomeViewData loadHomeData(int categoryId) {
        List<DanhMuc> categories = danhMucDAO.findBySql(
                "SELECT * FROM dbo.DANH_MUC WHERE trang_thai = 'ACTIVE' ORDER BY thu_tu_hien_thi ASC");
        List<SanPham> drinks = categoryId > 0
                ? sanPhamDAO.findBySql(
                        "SELECT * FROM dbo.SAN_PHAM WHERE trang_thai = 'AVAILABLE' AND id_danh_muc = ? ORDER BY id_san_pham ASC",
                        categoryId)
                : sanPhamDAO.findBySql(
                        "SELECT * FROM dbo.SAN_PHAM WHERE trang_thai = 'AVAILABLE' ORDER BY la_mon_ban_chay DESC, id_san_pham ASC");
        List<SanPham> bestSellers = sanPhamDAO.findBySql(
                "SELECT * FROM dbo.SAN_PHAM WHERE trang_thai = 'AVAILABLE' AND la_mon_ban_chay = 1 ORDER BY id_san_pham ASC");
        List<SanPham> newDrinks = sanPhamDAO.findBySql(
                "SELECT * FROM dbo.SAN_PHAM WHERE trang_thai = 'AVAILABLE' AND la_mon_moi = 1 ORDER BY id_san_pham DESC");
        List<KhuyenMai> activeVouchers = khuyenMaiDAO.findBySql(
                "SELECT * FROM dbo.KHUYEN_MAI WHERE trang_thai = 'ACTIVE' ORDER BY ngay_ket_thuc ASC");

        Map<Integer, List<KichCoSanPham>> sizeMap = new HashMap<>();
        for (SanPham sp : drinks) {
            sizeMap.put(sp.getIdSanPham(), kichCoDAO.findBySanPham(sp.getIdSanPham()));
        }
        for (SanPham sp : bestSellers) {
            sizeMap.computeIfAbsent(sp.getIdSanPham(), k -> kichCoDAO.findBySanPham(k));
        }
        for (SanPham sp : newDrinks) {
            sizeMap.computeIfAbsent(sp.getIdSanPham(), k -> kichCoDAO.findBySanPham(k));
        }

        int toppingsCount = toppingDAO.findAll().size();
        return new HomeViewData(categories, drinks, bestSellers, newDrinks, activeVouchers, sizeMap, toppingsCount);
    }

    public static class HomeViewData {
        private final List<DanhMuc> categories;
        private final List<SanPham> drinks;
        private final List<SanPham> bestSellers;
        private final List<SanPham> newDrinks;
        private final List<KhuyenMai> activeVouchers;
        private final Map<Integer, List<KichCoSanPham>> sizeMap;
        private final int toppingsCount;

        public HomeViewData(List<DanhMuc> categories, List<SanPham> drinks,
                            List<SanPham> bestSellers, List<SanPham> newDrinks,
                            List<KhuyenMai> activeVouchers,
                            Map<Integer, List<KichCoSanPham>> sizeMap, int toppingsCount) {
            this.categories = categories;
            this.drinks = drinks;
            this.bestSellers = bestSellers;
            this.newDrinks = newDrinks;
            this.activeVouchers = activeVouchers;
            this.sizeMap = sizeMap;
            this.toppingsCount = toppingsCount;
        }

        public List<DanhMuc> getCategories() {
            return categories;
        }

        public List<SanPham> getDrinks() {
            return drinks;
        }

        public List<SanPham> getBestSellers() {
            return bestSellers;
        }

        public List<SanPham> getNewDrinks() {
            return newDrinks;
        }

        public List<KhuyenMai> getActiveVouchers() {
            return activeVouchers;
        }

        public Map<Integer, List<KichCoSanPham>> getSizeMap() {
            return sizeMap;
        }

        public int getToppingsCount() {
            return toppingsCount;
        }
    }
}
