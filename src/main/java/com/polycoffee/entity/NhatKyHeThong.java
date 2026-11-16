package com.polycoffee.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Entity anh xa bang NHAT_KY_HE_THONG trong CSDL polycoffee.
 *
 * @author Quý (Nhóm PolyCoffee - JAV102)
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NhatKyHeThong {
    private Long idNhatKy;
    private Integer idNhanVien;
    private String loaiHanhDong;
    private String bangTacDong;
    private String maBanGhi;
    private String duLieuCu;
    private String duLieuMoi;
    private String diaChiIp;
    private Date thoiGian;

    public NhatKyHeThong() {
    }

    public NhatKyHeThong(Long idNhatKy, Integer idNhanVien, String loaiHanhDong, String bangTacDong,
                         String maBanGhi, String duLieuCu, String duLieuMoi, String diaChiIp,
                         Date thoiGian) {
        this.idNhatKy = idNhatKy;
        this.idNhanVien = idNhanVien;
        this.loaiHanhDong = loaiHanhDong;
        this.bangTacDong = bangTacDong;
        this.maBanGhi = maBanGhi;
        this.duLieuCu = duLieuCu;
        this.duLieuMoi = duLieuMoi;
        this.diaChiIp = diaChiIp;
        this.thoiGian = thoiGian;
    }

    public Long getIdNhatKy() {
        return idNhatKy;
    }

    public void setIdNhatKy(Long idNhatKy) {
        this.idNhatKy = idNhatKy;
    }

    public Integer getIdNhanVien() {
        return idNhanVien;
    }

    public void setIdNhanVien(Integer idNhanVien) {
        this.idNhanVien = idNhanVien;
    }

    public String getLoaiHanhDong() {
        return loaiHanhDong;
    }

    public void setLoaiHanhDong(String loaiHanhDong) {
        this.loaiHanhDong = loaiHanhDong;
    }

    public String getBangTacDong() {
        return bangTacDong;
    }

    public void setBangTacDong(String bangTacDong) {
        this.bangTacDong = bangTacDong;
    }

    public String getMaBanGhi() {
        return maBanGhi;
    }

    public void setMaBanGhi(String maBanGhi) {
        this.maBanGhi = maBanGhi;
    }

    public String getDuLieuCu() {
        return duLieuCu;
    }

    public void setDuLieuCu(String duLieuCu) {
        this.duLieuCu = duLieuCu;
    }

    public String getDuLieuMoi() {
        return duLieuMoi;
    }

    public void setDuLieuMoi(String duLieuMoi) {
        this.duLieuMoi = duLieuMoi;
    }

    public String getDiaChiIp() {
        return diaChiIp;
    }

    public void setDiaChiIp(String diaChiIp) {
        this.diaChiIp = diaChiIp;
    }

    public Date getThoiGian() {
        return thoiGian;
    }

    public void setThoiGian(Date thoiGian) {
        this.thoiGian = thoiGian;
    }

    @Override
    public String toString() {
        return "NhatKyHeThong{" +
                "idNhatKy=" + idNhatKy +
                ", idNhanVien=" + idNhanVien +
                ", loaiHanhDong='" + loaiHanhDong + '\'' +
                ", bangTacDong='" + bangTacDong + '\'' +
                ", maBanGhi='" + maBanGhi + '\'' +
                ", thoiGian=" + thoiGian +
                '}';
    }
}
