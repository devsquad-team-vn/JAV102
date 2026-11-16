package com.polycoffee.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entity anh xa bang CHI_TIET_DON_HANG trong CSDL polycoffee.
 *
 * @author Kiệt (Nhóm PolyCoffee - JAV102)
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChiTietDonHang {
    private Long idCtDonHang;
    private Long idDonHang;
    private Integer idKichCoSp;
    private Integer soLuong;
    private Long donGiaChot;
    private Long tongTienTopping;
    private String mucDuong;
    private String mucDa;
    private String ghiChuPhaChe;


    public Long getIdCtDonHang() {
        return idCtDonHang;
    }

    public void setIdCtDonHang(Long idCtDonHang) {
        this.idCtDonHang = idCtDonHang;
    }

    public Long getIdDonHang() {
        return idDonHang;
    }

    public void setIdDonHang(Long idDonHang) {
        this.idDonHang = idDonHang;
    }

    public Integer getIdKichCoSp() {
        return idKichCoSp;
    }

    public void setIdKichCoSp(Integer idKichCoSp) {
        this.idKichCoSp = idKichCoSp;
    }

    public Integer getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(Integer soLuong) {
        this.soLuong = soLuong;
    }

    public Long getDonGiaChot() {
        return donGiaChot;
    }

    public void setDonGiaChot(Long donGiaChot) {
        this.donGiaChot = donGiaChot;
    }

    public Long getTongTienTopping() {
        return tongTienTopping;
    }

    public void setTongTienTopping(Long tongTienTopping) {
        this.tongTienTopping = tongTienTopping;
    }

    public String getMucDuong() {
        return mucDuong;
    }

    public void setMucDuong(String mucDuong) {
        this.mucDuong = mucDuong;
    }

    public String getMucDa() {
        return mucDa;
    }

    public void setMucDa(String mucDa) {
        this.mucDa = mucDa;
    }

    public String getGhiChuPhaChe() {
        return ghiChuPhaChe;
    }

    public void setGhiChuPhaChe(String ghiChuPhaChe) {
        this.ghiChuPhaChe = ghiChuPhaChe;
    }

    @Override
    public String toString() {
        return "ChiTietDonHang{" +
                "idCtDonHang=" + idCtDonHang +
                ", idDonHang=" + idDonHang +
                ", idKichCoSp=" + idKichCoSp +
                ", soLuong=" + soLuong +
                ", donGiaChot=" + donGiaChot +
                ", tongTienTopping=" + tongTienTopping +
                '}';
    }
}
