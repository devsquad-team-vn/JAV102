package com.polycoffee.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Entity anh xa bang KHUYEN_MAI trong CSDL polycoffee.
 *
 * @author Khoa (Nhóm PolyCoffee - JAV102)
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class KhuyenMai {
    private Integer idKhuyenMai;
    private String maCodeKm;
    private String tenKhuyenMai;
    private String moTa;
    private String loaiGiamGia;
    private Long giaTriGiam;
    private Long giamToiDa;
    private Long donToiThieu;
    private String phamViApDung;
    private String hangToiThieu;
    private Integer diemCanDoi;
    private Integer tongLuotPhatHanh;
    private Integer soLuotDaDung;
    private Integer gioiHanMoiKhach;
    private Date ngayBatDau;
    private Date ngayKetThuc;
    private String trangThai;



    public Integer getIdKhuyenMai() {
        return idKhuyenMai;
    }

    public void setIdKhuyenMai(Integer idKhuyenMai) {
        this.idKhuyenMai = idKhuyenMai;
    }

    public String getMaCodeKm() {
        return maCodeKm;
    }

    public void setMaCodeKm(String maCodeKm) {
        this.maCodeKm = maCodeKm;
    }

    public String getTenKhuyenMai() {
        return tenKhuyenMai;
    }

    public void setTenKhuyenMai(String tenKhuyenMai) {
        this.tenKhuyenMai = tenKhuyenMai;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public String getLoaiGiamGia() {
        return loaiGiamGia;
    }

    public void setLoaiGiamGia(String loaiGiamGia) {
        this.loaiGiamGia = loaiGiamGia;
    }

    public Long getGiaTriGiam() {
        return giaTriGiam;
    }

    public void setGiaTriGiam(Long giaTriGiam) {
        this.giaTriGiam = giaTriGiam;
    }

    public Long getGiamToiDa() {
        return giamToiDa;
    }

    public void setGiamToiDa(Long giamToiDa) {
        this.giamToiDa = giamToiDa;
    }

    public Long getDonToiThieu() {
        return donToiThieu;
    }

    public void setDonToiThieu(Long donToiThieu) {
        this.donToiThieu = donToiThieu;
    }

    public String getPhamViApDung() {
        return phamViApDung;
    }

    public void setPhamViApDung(String phamViApDung) {
        this.phamViApDung = phamViApDung;
    }

    public String getHangToiThieu() {
        return hangToiThieu;
    }

    public void setHangToiThieu(String hangToiThieu) {
        this.hangToiThieu = hangToiThieu;
    }

    public Integer getDiemCanDoi() {
        return diemCanDoi;
    }

    public void setDiemCanDoi(Integer diemCanDoi) {
        this.diemCanDoi = diemCanDoi;
    }

    public Integer getTongLuotPhatHanh() {
        return tongLuotPhatHanh;
    }

    public void setTongLuotPhatHanh(Integer tongLuotPhatHanh) {
        this.tongLuotPhatHanh = tongLuotPhatHanh;
    }

    public Integer getSoLuotDaDung() {
        return soLuotDaDung;
    }

    public void setSoLuotDaDung(Integer soLuotDaDung) {
        this.soLuotDaDung = soLuotDaDung;
    }

    public Integer getGioiHanMoiKhach() {
        return gioiHanMoiKhach;
    }

    public void setGioiHanMoiKhach(Integer gioiHanMoiKhach) {
        this.gioiHanMoiKhach = gioiHanMoiKhach;
    }

    public Date getNgayBatDau() {
        return ngayBatDau;
    }

    public void setNgayBatDau(Date ngayBatDau) {
        this.ngayBatDau = ngayBatDau;
    }

    public Date getNgayKetThuc() {
        return ngayKetThuc;
    }

    public void setNgayKetThuc(Date ngayKetThuc) {
        this.ngayKetThuc = ngayKetThuc;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    @Override
    public String toString() {
        return "KhuyenMai{" +
                "idKhuyenMai=" + idKhuyenMai +
                ", maCodeKm='" + maCodeKm + '\'' +
                ", tenKhuyenMai='" + tenKhuyenMai + '\'' +
                ", loaiGiamGia='" + loaiGiamGia + '\'' +
                ", giaTriGiam=" + giaTriGiam +
                ", trangThai='" + trangThai + '\'' +
                '}';
    }
}
