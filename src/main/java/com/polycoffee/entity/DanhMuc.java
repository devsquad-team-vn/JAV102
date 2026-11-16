package com.polycoffee.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entity anh xa bang DANH_MUC trong CSDL polycoffee.
 *
 * @author Quang (Nhóm PolyCoffee - JAV102)
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DanhMuc {
    private Integer idDanhMuc;
    private String maDanhMuc;
    private String tenDanhMuc;
    private String moTa;
    private Integer thuTuHienThi;
    private String trangThai;
}
