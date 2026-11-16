package com.polycoffee.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entity anh xa bang CHI_TIET_TOPPING trong CSDL polycoffee.
 *
 * @author Quý (Nhóm PolyCoffee - JAV102)
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChiTietTopping {
    private Long idCtTopping;
    private Long idCtDonHang;
    private Integer idTopping;
    private Integer soLuong;
    private Long donGiaChot;



    public Long getIdCtTopping() {
        return idCtTopping;
    }

    public void setIdCtTopping(Long idCtTopping) {
        this.idCtTopping = idCtTopping;
    }

    public Long getIdCtDonHang() {
        return idCtDonHang;
    }

    public void setIdCtDonHang(Long idCtDonHang) {
        this.idCtDonHang = idCtDonHang;
    }

    public Integer getIdTopping() {
        return idTopping;
    }

    public void setIdTopping(Integer idTopping) {
        this.idTopping = idTopping;
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

    @Override
    public String toString() {
        return "ChiTietTopping{" +
                "idCtTopping=" + idCtTopping +
                ", idCtDonHang=" + idCtDonHang +
                ", idTopping=" + idTopping +
                ", soLuong=" + soLuong +
                ", donGiaChot=" + donGiaChot +
                '}';
    }
}
