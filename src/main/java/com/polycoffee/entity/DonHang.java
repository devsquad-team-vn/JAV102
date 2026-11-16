package com.polycoffee.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Entity anh xa bang DON_HANG trong CSDL polycoffee.
 *
 * @author Kiệt (Nhóm PolyCoffee - JAV102)
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DonHang {
    private Long idDonHang;
    private String maDonHang;
    private Integer idKhachHang;
    private Integer idNhanVien;
    private Integer idCaLam;
    private Integer idKhuyenMai;
    private String kenhBan;
    private String hinhThucPhucVu;
    private String soBanTheRung;
    private Date gioHenLay;
    private String maNhanDo;
    private Long tongTienMon;
    private Long tienGiamVoucher;
    private Integer diemSuDung;
    private Long tienGiamDiem;
    private Long tongThanhToan;
    private Integer diemCongThem;
    private String phuongThucTt;
    private Long tienKhachDua;
    private Long tienThoiLai;
    private String trangThaiTt;
    private String trangThaiDon;
    private String lyDoHuy;
    private String ghiChuHoanTien;
    private Integer soSaoDanhGia;
    private String noiDungDanhGia;
    private Date ngayTao;

    public DonHang() {
    }

    public DonHang(Long idDonHang, String maDonHang, Integer idKhachHang, Integer idNhanVien,
                   Integer idCaLam, Integer idKhuyenMai, String kenhBan, String hinhThucPhucVu,
                   String soBanTheRung, Date gioHenLay, String maNhanDo, Long tongTienMon,
                   Long tienGiamVoucher, Integer diemSuDung, Long tienGiamDiem, Long tongThanhToan,
                   Integer diemCongThem, String phuongThucTt, Long tienKhachDua, Long tienThoiLai,
                   String trangThaiTt, String trangThaiDon, String lyDoHuy, String ghiChuHoanTien,
                   Integer soSaoDanhGia, String noiDungDanhGia, Date ngayTao) {
        this.idDonHang = idDonHang;
        this.maDonHang = maDonHang;
        this.idKhachHang = idKhachHang;
        this.idNhanVien = idNhanVien;
        this.idCaLam = idCaLam;
        this.idKhuyenMai = idKhuyenMai;
        this.kenhBan = kenhBan;
        this.hinhThucPhucVu = hinhThucPhucVu;
        this.soBanTheRung = soBanTheRung;
        this.gioHenLay = gioHenLay;
        this.maNhanDo = maNhanDo;
        this.tongTienMon = tongTienMon;
        this.tienGiamVoucher = tienGiamVoucher;
        this.diemSuDung = diemSuDung;
        this.tienGiamDiem = tienGiamDiem;
        this.tongThanhToan = tongThanhToan;
        this.diemCongThem = diemCongThem;
        this.phuongThucTt = phuongThucTt;
        this.tienKhachDua = tienKhachDua;
        this.tienThoiLai = tienThoiLai;
        this.trangThaiTt = trangThaiTt;
        this.trangThaiDon = trangThaiDon;
        this.lyDoHuy = lyDoHuy;
        this.ghiChuHoanTien = ghiChuHoanTien;
        this.soSaoDanhGia = soSaoDanhGia;
        this.noiDungDanhGia = noiDungDanhGia;
        this.ngayTao = ngayTao;
    }

    public Long getIdDonHang() {
        return idDonHang;
    }

    public void setIdDonHang(Long idDonHang) {
        this.idDonHang = idDonHang;
    }

    public String getMaDonHang() {
        return maDonHang;
    }

    public void setMaDonHang(String maDonHang) {
        this.maDonHang = maDonHang;
    }

    public Integer getIdKhachHang() {
        return idKhachHang;
    }

    public void setIdKhachHang(Integer idKhachHang) {
        this.idKhachHang = idKhachHang;
    }

    public Integer getIdNhanVien() {
        return idNhanVien;
    }

    public void setIdNhanVien(Integer idNhanVien) {
        this.idNhanVien = idNhanVien;
    }

    public Integer getIdCaLam() {
        return idCaLam;
    }

    public void setIdCaLam(Integer idCaLam) {
        this.idCaLam = idCaLam;
    }

    public Integer getIdKhuyenMai() {
        return idKhuyenMai;
    }

    public void setIdKhuyenMai(Integer idKhuyenMai) {
        this.idKhuyenMai = idKhuyenMai;
    }

    public String getKenhBan() {
        return kenhBan;
    }

    public void setKenhBan(String kenhBan) {
        this.kenhBan = kenhBan;
    }

    public String getHinhThucPhucVu() {
        return hinhThucPhucVu;
    }

    public void setHinhThucPhucVu(String hinhThucPhucVu) {
        this.hinhThucPhucVu = hinhThucPhucVu;
    }

    public String getSoBanTheRung() {
        return soBanTheRung;
    }

    public void setSoBanTheRung(String soBanTheRung) {
        this.soBanTheRung = soBanTheRung;
    }

    public Date getGioHenLay() {
        return gioHenLay;
    }

    public void setGioHenLay(Date gioHenLay) {
        this.gioHenLay = gioHenLay;
    }

    public String getMaNhanDo() {
        return maNhanDo;
    }

    public void setMaNhanDo(String maNhanDo) {
        this.maNhanDo = maNhanDo;
    }

    public Long getTongTienMon() {
        return tongTienMon;
    }

    public void setTongTienMon(Long tongTienMon) {
        this.tongTienMon = tongTienMon;
    }

    public Long getTienGiamVoucher() {
        return tienGiamVoucher;
    }

    public void setTienGiamVoucher(Long tienGiamVoucher) {
        this.tienGiamVoucher = tienGiamVoucher;
    }

    public Integer getDiemSuDung() {
        return diemSuDung;
    }

    public void setDiemSuDung(Integer diemSuDung) {
        this.diemSuDung = diemSuDung;
    }

    public Long getTienGiamDiem() {
        return tienGiamDiem;
    }

    public void setTienGiamDiem(Long tienGiamDiem) {
        this.tienGiamDiem = tienGiamDiem;
    }

    public Long getTongThanhToan() {
        return tongThanhToan;
    }

    public void setTongThanhToan(Long tongThanhToan) {
        this.tongThanhToan = tongThanhToan;
    }

    public Integer getDiemCongThem() {
        return diemCongThem;
    }

    public void setDiemCongThem(Integer diemCongThem) {
        this.diemCongThem = diemCongThem;
    }

    public String getPhuongThucTt() {
        return phuongThucTt;
    }

    public void setPhuongThucTt(String phuongThucTt) {
        this.phuongThucTt = phuongThucTt;
    }

    public Long getTienKhachDua() {
        return tienKhachDua;
    }

    public void setTienKhachDua(Long tienKhachDua) {
        this.tienKhachDua = tienKhachDua;
    }

    public Long getTienThoiLai() {
        return tienThoiLai;
    }

    public void setTienThoiLai(Long tienThoiLai) {
        this.tienThoiLai = tienThoiLai;
    }

    public String getTrangThaiTt() {
        return trangThaiTt;
    }

    public void setTrangThaiTt(String trangThaiTt) {
        this.trangThaiTt = trangThaiTt;
    }

    public String getTrangThaiDon() {
        return trangThaiDon;
    }

    public void setTrangThaiDon(String trangThaiDon) {
        this.trangThaiDon = trangThaiDon;
    }

    public String getLyDoHuy() {
        return lyDoHuy;
    }

    public void setLyDoHuy(String lyDoHuy) {
        this.lyDoHuy = lyDoHuy;
    }

    public String getGhiChuHoanTien() {
        return ghiChuHoanTien;
    }

    public void setGhiChuHoanTien(String ghiChuHoanTien) {
        this.ghiChuHoanTien = ghiChuHoanTien;
    }

    public Integer getSoSaoDanhGia() {
        return soSaoDanhGia;
    }

    public void setSoSaoDanhGia(Integer soSaoDanhGia) {
        this.soSaoDanhGia = soSaoDanhGia;
    }

    public String getNoiDungDanhGia() {
        return noiDungDanhGia;
    }

    public void setNoiDungDanhGia(String noiDungDanhGia) {
        this.noiDungDanhGia = noiDungDanhGia;
    }

    public Date getNgayTao() {
        return ngayTao;
    }

    public void setNgayTao(Date ngayTao) {
        this.ngayTao = ngayTao;
    }

    @Override
    public String toString() {
        return "DonHang{" +
                "idDonHang=" + idDonHang +
                ", maDonHang='" + maDonHang + '\'' +
                ", kenhBan='" + kenhBan + '\'' +
                ", tongThanhToan=" + tongThanhToan +
                ", trangThaiTt='" + trangThaiTt + '\'' +
                ", trangThaiDon='" + trangThaiDon + '\'' +
                '}';
    }
}
