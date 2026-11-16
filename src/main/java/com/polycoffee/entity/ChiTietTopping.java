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
}
