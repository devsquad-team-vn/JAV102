package com.polycoffee.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Entity anh xa bang VI_VOUCHER_KHACH_HANG (Vi Voucher chuan Shopee) trong CSDL polycoffee.
 *
 * @author Khoa (Nhóm PolyCoffee - JAV102)
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ViVoucherKhachHang {
    private Long idViVoucher;
    private Integer idKhachHang;
    private Integer idKhuyenMai;
    private String nguonNhan;
    private Date ngayNhan;
    private Date ngayHetHan;
    private String trangThai;


    public Long getIdViVoucher() {
        return idViVoucher;
    }

    public void setIdViVoucher(Long idViVoucher) {
        this.idViVoucher = idViVoucher;
    }

    public Integer getIdKhachHang() {
        return idKhachHang;
    }

    public void setIdKhachHang(Integer idKhachHang) {
        this.idKhachHang = idKhachHang;
    }

    public Integer getIdKhuyenMai() {
        return idKhuyenMai;
    }

    public void setIdKhuyenMai(Integer idKhuyenMai) {
        this.idKhuyenMai = idKhuyenMai;
    }

    public String getNguonNhan() {
        return nguonNhan;
    }

    public void setNguonNhan(String nguonNhan) {
        this.nguonNhan = nguonNhan;
    }

    public Date getNgayNhan() {
        return ngayNhan;
    }

    public void setNgayNhan(Date ngayNhan) {
        this.ngayNhan = ngayNhan;
    }

    public Date getNgayHetHan() {
        return ngayHetHan;
    }

    public void setNgayHetHan(Date ngayHetHan) {
        this.ngayHetHan = ngayHetHan;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    @Override
    public String toString() {
        return "ViVoucherKhachHang{" +
                "idViVoucher=" + idViVoucher +
                ", idKhachHang=" + idKhachHang +
                ", idKhuyenMai=" + idKhuyenMai +
                ", nguonNhan='" + nguonNhan + '\'' +
                ", trangThai='" + trangThai + '\'' +
                '}';
    }
}
