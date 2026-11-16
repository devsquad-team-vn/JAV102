package com.polycoffee.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Entity anh xa bang SAN_PHAM trong CSDL polycoffee.
 *
 * @author Quang (Nhóm PolyCoffee - JAV102)
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SanPham {
    private Integer idSanPham;
    private String maSanPham;
    private Integer idDanhMuc;
    private String tenSanPham;
    private String hinhAnh;
    private String moTa;
    private Boolean choPhepDuongDa;
    private Boolean choPhepTopping;
    private Boolean laMonBanChay;
    private Boolean laMonMoi;
    private String trangThai;
    private Date ngayTao;



    public Integer getIdSanPham() {
        return idSanPham;
    }

    public void setIdSanPham(Integer idSanPham) {
        this.idSanPham = idSanPham;
    }

    public String getMaSanPham() {
        return maSanPham;
    }

    public void setMaSanPham(String maSanPham) {
        this.maSanPham = maSanPham;
    }

    public Integer getIdDanhMuc() {
        return idDanhMuc;
    }

    public void setIdDanhMuc(Integer idDanhMuc) {
        this.idDanhMuc = idDanhMuc;
    }

    public String getTenSanPham() {
        return tenSanPham;
    }

    public void setTenSanPham(String tenSanPham) {
        this.tenSanPham = tenSanPham;
    }

    public String getHinhAnh() {
        return hinhAnh;
    }

    public void setHinhAnh(String hinhAnh) {
        this.hinhAnh = hinhAnh;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public Boolean getChoPhepDuongDa() {
        return choPhepDuongDa;
    }

    public void setChoPhepDuongDa(Boolean choPhepDuongDa) {
        this.choPhepDuongDa = choPhepDuongDa;
    }

    public Boolean getChoPhepTopping() {
        return choPhepTopping;
    }

    public void setChoPhepTopping(Boolean choPhepTopping) {
        this.choPhepTopping = choPhepTopping;
    }

    public Boolean getLaMonBanChay() {
        return laMonBanChay;
    }

    public void setLaMonBanChay(Boolean laMonBanChay) {
        this.laMonBanChay = laMonBanChay;
    }

    public Boolean getLaMonMoi() {
        return laMonMoi;
    }

    public void setLaMonMoi(Boolean laMonMoi) {
        this.laMonMoi = laMonMoi;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public Date getNgayTao() {
        return ngayTao;
    }

    public void setNgayTao(Date ngayTao) {
        this.ngayTao = ngayTao;
    }

    @Override
    public String toString() {
        return "SanPham{" +
                "idSanPham=" + idSanPham +
                ", maSanPham='" + maSanPham + '\'' +
                ", idDanhMuc=" + idDanhMuc +
                ", tenSanPham='" + tenSanPham + '\'' +
                ", trangThai='" + trangThai + '\'' +
                '}';
    }
}
