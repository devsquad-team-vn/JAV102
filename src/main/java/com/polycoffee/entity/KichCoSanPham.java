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
}
