package com.polycoffee.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Entity anh xa bang NHAN_VIEN trong CSDL polycoffee.
 *
 * @author Nam (Nhóm PolyCoffee - JAV102)
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NhanVien {
    private Integer idNhanVien;
    private String maNhanVien;
    private String tenDangNhap;
    private String email;
    private String matKhauBam;
    private String hoTen;
    private String soDienThoai;
    private String diaChi;
    private String anhDaiDien;
    private String vaiTro;
    private Integer soLanSaiMk;
    private Date khoaDenLuc;
    private Boolean dangHoatDong;
    private Date ngayTao;



    public Integer getIdNhanVien() {
        return idNhanVien;
    }

    public void setIdNhanVien(Integer idNhanVien) {
        this.idNhanVien = idNhanVien;
    }

    public String getMaNhanVien() {
        return maNhanVien;
    }

    public void setMaNhanVien(String maNhanVien) {
        this.maNhanVien = maNhanVien;
    }

    public String getTenDangNhap() {
        return tenDangNhap;
    }

    public void setTenDangNhap(String tenDangNhap) {
        this.tenDangNhap = tenDangNhap;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMatKhauBam() {
        return matKhauBam;
    }

    public void setMatKhauBam(String matKhauBam) {
        this.matKhauBam = matKhauBam;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getSoDienThoai() {
        return soDienThoai;
    }

    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getAnhDaiDien() {
        return anhDaiDien;
    }

    public void setAnhDaiDien(String anhDaiDien) {
        this.anhDaiDien = anhDaiDien;
    }

    public String getVaiTro() {
        return vaiTro;
    }

    public void setVaiTro(String vaiTro) {
        this.vaiTro = vaiTro;
    }

    public Integer getSoLanSaiMk() {
        return soLanSaiMk;
    }

    public void setSoLanSaiMk(Integer soLanSaiMk) {
        this.soLanSaiMk = soLanSaiMk;
    }

    public Date getKhoaDenLuc() {
        return khoaDenLuc;
    }

    public void setKhoaDenLuc(Date khoaDenLuc) {
        this.khoaDenLuc = khoaDenLuc;
    }

    public Boolean getDangHoatDong() {
        return dangHoatDong;
    }

    public void setDangHoatDong(Boolean dangHoatDong) {
        this.dangHoatDong = dangHoatDong;
    }

    public Date getNgayTao() {
        return ngayTao;
    }

    public void setNgayTao(Date ngayTao) {
        this.ngayTao = ngayTao;
    }

    @Override
    public String toString() {
        return "NhanVien{" +
                "idNhanVien=" + idNhanVien +
                ", maNhanVien='" + maNhanVien + '\'' +
                ", tenDangNhap='" + tenDangNhap + '\'' +
                ", hoTen='" + hoTen + '\'' +
                ", vaiTro='" + vaiTro + '\'' +
                ", dangHoatDong=" + dangHoatDong +
                '}';
    }
}
