package com.polycoffee.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entity anh xa bang TOPPING trong CSDL polycoffee.
 *
 * @author Nam (Nhóm PolyCoffee - JAV102)
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Topping {
    private Integer idTopping;
    private String maTopping;
    private String tenTopping;
    private Long giaTopping;
    private String hinhAnh;
    private String trangThai;



    public Integer getIdTopping() {
        return idTopping;
    }

    public void setIdTopping(Integer idTopping) {
        this.idTopping = idTopping;
    }

    public String getMaTopping() {
        return maTopping;
    }

    public void setMaTopping(String maTopping) {
        this.maTopping = maTopping;
    }

    public String getTenTopping() {
        return tenTopping;
    }

    public void setTenTopping(String tenTopping) {
        this.tenTopping = tenTopping;
    }

    public Long getGiaTopping() {
        return giaTopping;
    }

    public void setGiaTopping(Long giaTopping) {
        this.giaTopping = giaTopping;
    }

    public String getHinhAnh() {
        return hinhAnh;
    }

    public void setHinhAnh(String hinhAnh) {
        this.hinhAnh = hinhAnh;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    @Override
    public String toString() {
        return "Topping{" +
                "idTopping=" + idTopping +
                ", maTopping='" + maTopping + '\'' +
                ", tenTopping='" + tenTopping + '\'' +
                ", giaTopping=" + giaTopping +
                ", trangThai='" + trangThai + '\'' +
                '}';
    }
}
