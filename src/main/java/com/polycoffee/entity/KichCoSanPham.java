package com.polycoffee.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entity anh xa bang KICH_CO_SAN_PHAM trong CSDL polycoffee.
 *
 * @author Quang (Nhóm PolyCoffee - JAV102)
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class KichCoSanPham {
    private Integer idKichCoSp;
    private Integer idSanPham;
    private String tenKichCo;
    private Long giaBan;
    private String trangThai;

    public KichCoSanPham() {
    }

    public KichCoSanPham(Integer idKichCoSp, Integer idSanPham, String tenKichCo,
                         Long giaBan, String trangThai) {
        this.idKichCoSp = idKichCoSp;
        this.idSanPham = idSanPham;
        this.tenKichCo = tenKichCo;
        this.giaBan = giaBan;
        this.trangThai = trangThai;
    }

    public Integer getIdKichCoSp() {
        return idKichCoSp;
    }

    public void setIdKichCoSp(Integer idKichCoSp) {
        this.idKichCoSp = idKichCoSp;
    }

    public Integer getIdSanPham() {
        return idSanPham;
    }

    public void setIdSanPham(Integer idSanPham) {
        this.idSanPham = idSanPham;
    }

    public String getTenKichCo() {
        return tenKichCo;
    }

    public void setTenKichCo(String tenKichCo) {
        this.tenKichCo = tenKichCo;
    }

    public Long getGiaBan() {
        return giaBan;
    }

    public void setGiaBan(Long giaBan) {
        this.giaBan = giaBan;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    @Override
    public String toString() {
        return "KichCoSanPham{" +
                "idKichCoSp=" + idKichCoSp +
                ", idSanPham=" + idSanPham +
                ", tenKichCo='" + tenKichCo + '\'' +
                ", giaBan=" + giaBan +
                ", trangThai='" + trangThai + '\'' +
                '}';
    }
}
