package com.polycoffee.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Entity anh xa bang PHIEN_CA_LAM_VIEC trong CSDL polycoffee.
 *
 * @author Nam (Nhóm PolyCoffee - JAV102)
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PhienCaLamViec {
    private Integer idCaLam;
    private String maCaLam;
    private Integer idNhanVien;
    private Date thoiGianMoCa;
    private Date thoiGianKetCa;
    private Long tienDauCa;
    private Long tongTienMatHt;
    private Long tongTienQrHt;
    private Long tienMatThucTe;
    private String ghiChuChenhLech;
    private String trangThai;



    public Integer getIdCaLam() {
        return idCaLam;
    }

    public void setIdCaLam(Integer idCaLam) {
        this.idCaLam = idCaLam;
    }

    public String getMaCaLam() {
        return maCaLam;
    }

    public void setMaCaLam(String maCaLam) {
        this.maCaLam = maCaLam;
    }

    public Integer getIdNhanVien() {
        return idNhanVien;
    }

    public void setIdNhanVien(Integer idNhanVien) {
        this.idNhanVien = idNhanVien;
    }

    public Date getThoiGianMoCa() {
        return thoiGianMoCa;
    }

    public void setThoiGianMoCa(Date thoiGianMoCa) {
        this.thoiGianMoCa = thoiGianMoCa;
    }

    public Date getThoiGianKetCa() {
        return thoiGianKetCa;
    }

    public void setThoiGianKetCa(Date thoiGianKetCa) {
        this.thoiGianKetCa = thoiGianKetCa;
    }

    public Long getTienDauCa() {
        return tienDauCa;
    }

    public void setTienDauCa(Long tienDauCa) {
        this.tienDauCa = tienDauCa;
    }

    public Long getTongTienMatHt() {
        return tongTienMatHt;
    }

    public void setTongTienMatHt(Long tongTienMatHt) {
        this.tongTienMatHt = tongTienMatHt;
    }

    public Long getTongTienQrHt() {
        return tongTienQrHt;
    }

    public void setTongTienQrHt(Long tongTienQrHt) {
        this.tongTienQrHt = tongTienQrHt;
    }

    public Long getTienMatThucTe() {
        return tienMatThucTe;
    }

    public void setTienMatThucTe(Long tienMatThucTe) {
        this.tienMatThucTe = tienMatThucTe;
    }

    public String getGhiChuChenhLech() {
        return ghiChuChenhLech;
    }

    public void setGhiChuChenhLech(String ghiChuChenhLech) {
        this.ghiChuChenhLech = ghiChuChenhLech;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    @Override
    public String toString() {
        return "PhienCaLamViec{" +
                "idCaLam=" + idCaLam +
                ", maCaLam='" + maCaLam + '\'' +
                ", idNhanVien=" + idNhanVien +
                ", tienDauCa=" + tienDauCa +
                ", trangThai='" + trangThai + '\'' +
                '}';
    }
}
