package com.polycoffee.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entity anh xa bang DANH_MUC trong CSDL polycoffee.
 *
 * @author Quang (Nhóm PolyCoffee - JAV102)
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DanhMuc {
    private Integer idDanhMuc;
    private String maDanhMuc;
    private String tenDanhMuc;
    private String moTa;
    private Integer thuTuHienThi;
    private String trangThai;



    public Integer getIdDanhMuc() {
        return idDanhMuc;
    }

    public void setIdDanhMuc(Integer idDanhMuc) {
        this.idDanhMuc = idDanhMuc;
    }

    public String getMaDanhMuc() {
        return maDanhMuc;
    }

    public void setMaDanhMuc(String maDanhMuc) {
        this.maDanhMuc = maDanhMuc;
    }

    public String getTenDanhMuc() {
        return tenDanhMuc;
    }

    public void setTenDanhMuc(String tenDanhMuc) {
        this.tenDanhMuc = tenDanhMuc;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public Integer getThuTuHienThi() {
        return thuTuHienThi;
    }

    public void setThuTuHienThi(Integer thuTuHienThi) {
        this.thuTuHienThi = thuTuHienThi;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    @Override
    public String toString() {
        return "DanhMuc{" +
                "idDanhMuc=" + idDanhMuc +
                ", maDanhMuc='" + maDanhMuc + '\'' +
                ", tenDanhMuc='" + tenDanhMuc + '\'' +
                ", thuTuHienThi=" + thuTuHienThi +
                ", trangThai='" + trangThai + '\'' +
                '}';
    }
}
