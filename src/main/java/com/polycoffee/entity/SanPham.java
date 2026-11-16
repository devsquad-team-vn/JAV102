package com.polycoffee.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Entity anh xa bang SAN_PHAM trong CSDL polycoffee.
 *
 * @author Quang (Nhóm PolyCoffee - JAV102)
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SanPham {
    private Integer idSanPham;
    private String maSanPham;
    private Integer idDanhMuc;
    private String tenSanPham;
    private String hinhAnh;
    private String moTa;
    private Boolean choPhepDuongDa;
    private Boolean choPhepTopping;
    private Boolean laMonBanChay;
    private Boolean laMonMoi;
    private String trangThai;
    private Date ngayTao;
}
