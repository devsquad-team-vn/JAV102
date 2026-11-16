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
}
