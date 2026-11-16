package com.polycoffee.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Entity anh xa bang KHACH_HANG trong CSDL polycoffee.
 *
 * @author Khoa (Nhóm PolyCoffee - JAV102)
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class KhachHang {
    private Integer idKhachHang;
    private String maKhachHang;
    private String soDienThoai;
    private String hoTen;
    private String email;
    private String matKhauBam;
    private Date ngaySinh;
    private String anhDaiDien;
    private Integer diemKhaDung;
    private Integer tongDiemTichLuy;
    private String hangThanhVien;
    private String maPinKichHoat;
    private Boolean daKichHoat;
    private Integer soLanSaiMk;
    private Date khoaDenLuc;
    private Boolean dangHoatDong;
    private Date ngayTao;

    public Integer getIdKhachHang() {
        return idKhachHang;
    }

    public void setIdKhachHang(Integer idKhachHang) {
        this.idKhachHang = idKhachHang;
    }

    public String getMaKhachHang() {
        return maKhachHang;
    }

    public void setMaKhachHang(String maKhachHang) {
        this.maKhachHang = maKhachHang;
    }

    public String getSoDienThoai() {
        return soDienThoai;
    }

    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
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

    public Date getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(Date ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public String getAnhDaiDien() {
        return anhDaiDien;
    }

    public void setAnhDaiDien(String anhDaiDien) {
        this.anhDaiDien = anhDaiDien;
    }

    public Integer getDiemKhaDung() {
        return diemKhaDung;
    }

    public void setDiemKhaDung(Integer diemKhaDung) {
        this.diemKhaDung = diemKhaDung;
    }

    public Integer getTongDiemTichLuy() {
        return tongDiemTichLuy;
    }

    public void setTongDiemTichLuy(Integer tongDiemTichLuy) {
        this.tongDiemTichLuy = tongDiemTichLuy;
    }

    public String getHangThanhVien() {
        return hangThanhVien;
    }

    public void setHangThanhVien(String hangThanhVien) {
        this.hangThanhVien = hangThanhVien;
    }

    public String getMaPinKichHoat() {
        return maPinKichHoat;
    }

    public void setMaPinKichHoat(String maPinKichHoat) {
        this.maPinKichHoat = maPinKichHoat;
    }

    public Boolean getDaKichHoat() {
        return daKichHoat;
    }

    public void setDaKichHoat(Boolean daKichHoat) {
        this.daKichHoat = daKichHoat;
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
        return "KhachHang{" +
                "idKhachHang=" + idKhachHang +
                ", maKhachHang='" + maKhachHang + '\'' +
                ", soDienThoai='" + soDienThoai + '\'' +
                ", hoTen='" + hoTen + '\'' +
                ", diemKhaDung=" + diemKhaDung +
                ", hangThanhVien='" + hangThanhVien + '\'' +
                '}';
    }
}
