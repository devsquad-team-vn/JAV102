-- =============================================================================
-- DỰ ÁN: POLYCOFFEE - HỆ THỐNG QUẢN LÝ QUÁN CÀ PHÊ (POS & ĐẶT MÓN TRỰC TUYẾN)
-- HỌC PHẦN: JAV102 - DỰ ÁN JAVA 1 (LAB 3 - BÀI 1)
-- CSDL: SQL Server (T-SQL) - Database: polycoffee
-- Quy ước: 13 Bảng (UPPER_SNAKE_CASE) & 145 Thuộc tính tiếng Việt không dấu (snake_case)
-- =============================================================================

USE master;
GO

IF DB_ID(N'polycoffee') IS NULL
BEGIN
    CREATE DATABASE polycoffee;
END
GO

USE polycoffee;
GO

-- =============================================================================
-- DỌN DẸP BẢNG / THỦ TỤC CŨ (NẾU TỒN TẠI) ĐỂ ĐẢM BẢO CHẠY LẠI AN TOÀN
-- =============================================================================
IF OBJECT_ID(N'dbo.sp_revenue_by_day', N'P') IS NOT NULL DROP PROCEDURE dbo.sp_revenue_by_day;
IF OBJECT_ID(N'dbo.sp_top_5_best_selling_drinks', N'P') IS NOT NULL DROP PROCEDURE dbo.sp_top_5_best_selling_drinks;
GO

DROP TABLE IF EXISTS dbo.NHAT_KY_HE_THONG;
DROP TABLE IF EXISTS dbo.CHI_TIET_TOPPING;
DROP TABLE IF EXISTS dbo.CHI_TIET_DON_HANG;
DROP TABLE IF EXISTS dbo.DON_HANG;
DROP TABLE IF EXISTS dbo.VI_VOUCHER_KHACH_HANG;
DROP TABLE IF EXISTS dbo.KHUYEN_MAI;
DROP TABLE IF EXISTS dbo.KHACH_HANG;
DROP TABLE IF EXISTS dbo.PHIEN_CA_LAM_VIEC;
DROP TABLE IF EXISTS dbo.NHAN_VIEN;
DROP TABLE IF EXISTS dbo.TOPPING;
DROP TABLE IF EXISTS dbo.KICH_CO_SAN_PHAM;
DROP TABLE IF EXISTS dbo.SAN_PHAM;
DROP TABLE IF EXISTS dbo.DANH_MUC;
GO

-- =============================================================================
-- 1. BẢNG DANH_MUC (6 cột)
-- =============================================================================
CREATE TABLE dbo.DANH_MUC (
    id_danh_muc      INT IDENTITY(1,1) NOT NULL,
    ma_danh_muc      VARCHAR(15)       NOT NULL,
    ten_danh_muc     NVARCHAR(100)     NOT NULL,
    mo_ta            NVARCHAR(300)     NULL,
    thu_tu_hien_thi  INT               NOT NULL CONSTRAINT DF_DM_ThuTu DEFAULT (0),
    trang_thai       VARCHAR(20)       NOT NULL CONSTRAINT DF_DM_TrangThai DEFAULT ('ACTIVE'),
    CONSTRAINT PK_DANH_MUC PRIMARY KEY (id_danh_muc),
    CONSTRAINT UK_DANH_MUC_Ma UNIQUE (ma_danh_muc),
    CONSTRAINT UK_DANH_MUC_Ten UNIQUE (ten_danh_muc),
    CONSTRAINT CK_DANH_MUC_ThuTu CHECK (thu_tu_hien_thi >= 0),
    CONSTRAINT CK_DANH_MUC_TrangThai CHECK (trang_thai IN ('ACTIVE', 'INACTIVE'))
);
GO

-- =============================================================================
-- 2. BẢNG SAN_PHAM (12 cột)
-- =============================================================================
CREATE TABLE dbo.SAN_PHAM (
    id_san_pham       INT IDENTITY(1,1) NOT NULL,
    ma_san_pham       VARCHAR(15)       NOT NULL,
    id_danh_muc       INT               NOT NULL,
    ten_san_pham      NVARCHAR(150)     NOT NULL,
    hinh_anh          VARCHAR(255)      NOT NULL,
    mo_ta             NVARCHAR(500)     NULL,
    cho_phep_duong_da BIT               NOT NULL CONSTRAINT DF_SP_DuongDa DEFAULT (1),
    cho_phep_topping  BIT               NOT NULL CONSTRAINT DF_SP_Topping DEFAULT (1),
    la_mon_ban_chay   BIT               NOT NULL CONSTRAINT DF_SP_BanChay DEFAULT (0),
    la_mon_moi        BIT               NOT NULL CONSTRAINT DF_SP_MonMoi DEFAULT (0),
    trang_thai        VARCHAR(20)       NOT NULL CONSTRAINT DF_SP_TrangThai DEFAULT ('AVAILABLE'),
    ngay_tao          DATETIME2         NOT NULL CONSTRAINT DF_SP_NgayTao DEFAULT (SYSDATETIME()),
    CONSTRAINT PK_SAN_PHAM PRIMARY KEY (id_san_pham),
    CONSTRAINT UK_SAN_PHAM_Ma UNIQUE (ma_san_pham),
    CONSTRAINT UK_SAN_PHAM_Ten UNIQUE (ten_san_pham),
    CONSTRAINT FK_SAN_PHAM_DANH_MUC FOREIGN KEY (id_danh_muc) REFERENCES dbo.DANH_MUC(id_danh_muc),
    CONSTRAINT CK_SAN_PHAM_TrangThai CHECK (trang_thai IN ('AVAILABLE', 'OUT_OF_STOCK', 'DISCONTINUED'))
);
GO

-- =============================================================================
-- 3. BẢNG KICH_CO_SAN_PHAM (5 cột)
-- =============================================================================
CREATE TABLE dbo.KICH_CO_SAN_PHAM (
    id_kich_co_sp INT IDENTITY(1,1) NOT NULL,
    id_san_pham   INT               NOT NULL,
    ten_kich_co   NVARCHAR(20)      NOT NULL,
    gia_ban       BIGINT            NOT NULL,
    trang_thai    VARCHAR(20)       NOT NULL CONSTRAINT DF_KC_TrangThai DEFAULT ('AVAILABLE'),
    CONSTRAINT PK_KICH_CO_SAN_PHAM PRIMARY KEY (id_kich_co_sp),
    CONSTRAINT FK_KICH_CO_SAN_PHAM_SP FOREIGN KEY (id_san_pham) REFERENCES dbo.SAN_PHAM(id_san_pham),
    CONSTRAINT UK_KICH_CO_SP_TenSize UNIQUE (id_san_pham, ten_kich_co),
    CONSTRAINT CK_KICH_CO_SP_GiaBan CHECK (gia_ban >= 0),
    CONSTRAINT CK_KICH_CO_SP_TrangThai CHECK (trang_thai IN ('AVAILABLE', 'OUT_OF_STOCK', 'INACTIVE'))
);
GO

-- =============================================================================
-- 4. BẢNG TOPPING (6 cột)
-- =============================================================================
CREATE TABLE dbo.TOPPING (
    id_topping   INT IDENTITY(1,1) NOT NULL,
    ma_topping   VARCHAR(15)       NOT NULL,
    ten_topping  NVARCHAR(100)     NOT NULL,
    gia_topping  BIGINT            NOT NULL,
    hinh_anh     VARCHAR(255)      NULL,
    trang_thai   VARCHAR(20)       NOT NULL CONSTRAINT DF_TP_TrangThai DEFAULT ('AVAILABLE'),
    CONSTRAINT PK_TOPPING PRIMARY KEY (id_topping),
    CONSTRAINT UK_TOPPING_Ma UNIQUE (ma_topping),
    CONSTRAINT UK_TOPPING_Ten UNIQUE (ten_topping),
    CONSTRAINT CK_TOPPING_Gia CHECK (gia_topping >= 0),
    CONSTRAINT CK_TOPPING_TrangThai CHECK (trang_thai IN ('AVAILABLE', 'OUT_OF_STOCK', 'INACTIVE'))
);
GO

-- =============================================================================
-- 5. BẢNG NHAN_VIEN (14 cột)
-- =============================================================================
CREATE TABLE dbo.NHAN_VIEN (
    id_nhan_vien   INT IDENTITY(1,1) NOT NULL,
    ma_nhan_vien   VARCHAR(15)       NOT NULL,
    ten_dang_nhap  VARCHAR(50)       NOT NULL,
    email          VARCHAR(120)      NOT NULL,
    mat_khau_bam   VARCHAR(255)      NOT NULL,
    ho_ten         NVARCHAR(100)     NOT NULL,
    so_dien_thoai  VARCHAR(15)       NULL,
    dia_chi        NVARCHAR(255)     NULL,
    anh_dai_dien   VARCHAR(255)      NULL,
    vai_tro        VARCHAR(20)       NOT NULL,
    so_lan_sai_mk  INT               NOT NULL CONSTRAINT DF_NV_SaiMK DEFAULT (0),
    khoa_den_luc   DATETIME2         NULL,
    dang_hoat_dong BIT               NOT NULL CONSTRAINT DF_NV_HoatDong DEFAULT (1),
    ngay_tao       DATETIME2         NOT NULL CONSTRAINT DF_NV_NgayTao DEFAULT (SYSDATETIME()),
    CONSTRAINT PK_NHAN_VIEN PRIMARY KEY (id_nhan_vien),
    CONSTRAINT UK_NHAN_VIEN_Ma UNIQUE (ma_nhan_vien),
    CONSTRAINT UK_NHAN_VIEN_TenDN UNIQUE (ten_dang_nhap),
    CONSTRAINT UK_NHAN_VIEN_Email UNIQUE (email),
    CONSTRAINT CK_NHAN_VIEN_VaiTro CHECK (vai_tro IN ('ADMIN', 'CASHIER', 'BARISTA')),
    CONSTRAINT CK_NHAN_VIEN_SaiMK CHECK (so_lan_sai_mk >= 0)
);
GO

-- =============================================================================
-- 6. BẢNG PHIEN_CA_LAM_VIEC (11 cột)
-- =============================================================================
CREATE TABLE dbo.PHIEN_CA_LAM_VIEC (
    id_ca_lam          INT IDENTITY(1,1) NOT NULL,
    ma_ca_lam          VARCHAR(20)       NOT NULL,
    id_nhan_vien       INT               NOT NULL,
    thoi_gian_mo_ca    DATETIME2         NOT NULL CONSTRAINT DF_CLV_MoCa DEFAULT (SYSDATETIME()),
    thoi_gian_ket_ca   DATETIME2         NULL,
    tien_dau_ca        BIGINT            NOT NULL,
    tong_tien_mat_ht   BIGINT            NOT NULL CONSTRAINT DF_CLV_TienMatHT DEFAULT (0),
    tong_tien_qr_ht    BIGINT            NOT NULL CONSTRAINT DF_CLV_TienQrHT DEFAULT (0),
    tien_mat_thuc_te   BIGINT            NULL,
    ghi_chu_chenh_lech NVARCHAR(255)     NULL,
    trang_thai         VARCHAR(20)       NOT NULL CONSTRAINT DF_CLV_TrangThai DEFAULT ('OPEN'),
    CONSTRAINT PK_PHIEN_CA_LAM_VIEC PRIMARY KEY (id_ca_lam),
    CONSTRAINT UK_PHIEN_CA_LAM_VIEC_Ma UNIQUE (ma_ca_lam),
    CONSTRAINT FK_PHIEN_CA_LAM_VIEC_NV FOREIGN KEY (id_nhan_vien) REFERENCES dbo.NHAN_VIEN(id_nhan_vien),
    CONSTRAINT CK_PHIEN_CA_LAM_VIEC_TienDauCa CHECK (tien_dau_ca >= 0),
    CONSTRAINT CK_PHIEN_CA_LAM_VIEC_TrangThai CHECK (trang_thai IN ('OPEN', 'CLOSED'))
);
GO

-- =============================================================================
-- 7. BẢNG KHACH_HANG (17 cột)
-- =============================================================================
CREATE TABLE dbo.KHACH_HANG (
    id_khach_hang      INT IDENTITY(1,1) NOT NULL,
    ma_khach_hang      VARCHAR(15)       NOT NULL,
    so_dien_thoai      VARCHAR(15)       NOT NULL,
    ho_ten             NVARCHAR(100)     NOT NULL,
    email              VARCHAR(120)      NULL,
    mat_khau_bam       VARCHAR(255)      NULL,
    ngay_sinh          DATE              NULL,
    anh_dai_dien       VARCHAR(255)      NULL,
    diem_kha_dung      INT               NOT NULL CONSTRAINT DF_KH_DiemKhaDung DEFAULT (0),
    tong_diem_tich_luy INT               NOT NULL CONSTRAINT DF_KH_TongDiem DEFAULT (0),
    hang_thanh_vien    NVARCHAR(20)      NOT NULL CONSTRAINT DF_KH_HangTV DEFAULT (N'Đồng'),
    ma_pin_kich_hoat   VARCHAR(10)       NULL,
    da_kich_hoat       BIT               NOT NULL CONSTRAINT DF_KH_DaKichHoat DEFAULT (0),
    so_lan_sai_mk      INT               NOT NULL CONSTRAINT DF_KH_SaiMK DEFAULT (0),
    khoa_den_luc       DATETIME2         NULL,
    dang_hoat_dong     BIT               NOT NULL CONSTRAINT DF_KH_HoatDong DEFAULT (1),
    ngay_tao           DATETIME2         NOT NULL CONSTRAINT DF_KH_NgayTao DEFAULT (SYSDATETIME()),
    CONSTRAINT PK_KHACH_HANG PRIMARY KEY (id_khach_hang),
    CONSTRAINT UK_KHACH_HANG_Ma UNIQUE (ma_khach_hang),
    CONSTRAINT UK_KHACH_HANG_SDT UNIQUE (so_dien_thoai),
    CONSTRAINT CK_KHACH_HANG_Diem CHECK (diem_kha_dung >= 0 AND tong_diem_tich_luy >= 0),
    CONSTRAINT CK_KHACH_HANG_Hang CHECK (hang_thanh_vien IN (N'Đồng', N'Bạc', N'Vàng', N'Kim Cương')),
    CONSTRAINT CK_KHACH_HANG_SaiMK CHECK (so_lan_sai_mk >= 0)
);
GO

-- =============================================================================
-- 8. BẢNG KHUYEN_MAI (17 cột)
-- =============================================================================
CREATE TABLE dbo.KHUYEN_MAI (
    id_khuyen_mai      INT IDENTITY(1,1) NOT NULL,
    ma_code_km         VARCHAR(20)       NOT NULL,
    ten_khuyen_mai     NVARCHAR(150)     NOT NULL,
    mo_ta              NVARCHAR(500)     NULL,
    loai_giam_gia      VARCHAR(20)       NOT NULL,
    gia_tri_giam       BIGINT            NOT NULL,
    giam_toi_da        BIGINT            NULL,
    don_toi_thieu      BIGINT            NOT NULL CONSTRAINT DF_KM_DonToiThieu DEFAULT (0),
    pham_vi_ap_dung    VARCHAR(20)       NOT NULL,
    hang_toi_thieu     NVARCHAR(20)      NULL,
    diem_can_doi       INT               NOT NULL CONSTRAINT DF_KM_DiemDoi DEFAULT (0),
    tong_luot_phat_hanh INT              NOT NULL,
    so_luot_da_dung    INT               NOT NULL CONSTRAINT DF_KM_DaDung DEFAULT (0),
    gioi_han_moi_khach INT               NOT NULL CONSTRAINT DF_KM_GioiHan DEFAULT (1),
    ngay_bat_dau       DATETIME2         NOT NULL,
    ngay_ket_thuc      DATETIME2         NOT NULL,
    trang_thai         VARCHAR(20)       NOT NULL CONSTRAINT DF_KM_TrangThai DEFAULT ('ACTIVE'),
    CONSTRAINT PK_KHUYEN_MAI PRIMARY KEY (id_khuyen_mai),
    CONSTRAINT UK_KHUYEN_MAI_Code UNIQUE (ma_code_km),
    CONSTRAINT CK_KHUYEN_MAI_LoaiGiam CHECK (loai_giam_gia IN ('PERCENT', 'FIXED_AMOUNT')),
    CONSTRAINT CK_KHUYEN_MAI_PhamVi CHECK (pham_vi_ap_dung IN ('ALL', 'POS', 'WEB', 'PUBLIC', 'PRIVATE', 'REDEEM_POINTS', 'TIER_REWARD')),
    CONSTRAINT CK_KHUYEN_MAI_Hang CHECK (hang_toi_thieu IS NULL OR hang_toi_thieu IN (N'Đồng', N'Bạc', N'Vàng', N'Kim Cương')),
    CONSTRAINT CK_KHUYEN_MAI_GiaTri CHECK (gia_tri_giam >= 0 AND don_toi_thieu >= 0 AND diem_can_doi >= 0),
    CONSTRAINT CK_KHUYEN_MAI_Luot CHECK (tong_luot_phat_hanh >= 0 AND so_luot_da_dung >= 0 AND gioi_han_moi_khach >= 1),
    CONSTRAINT CK_KHUYEN_MAI_Ngay CHECK (ngay_ket_thuc >= ngay_bat_dau),
    CONSTRAINT CK_KHUYEN_MAI_TrangThai CHECK (trang_thai IN ('ACTIVE', 'INACTIVE', 'EXPIRED'))
);
GO

-- =============================================================================
-- 9. BẢNG VI_VOUCHER_KHACH_HANG (7 cột - Ví Voucher chuẩn Shopee)
-- =============================================================================
CREATE TABLE dbo.VI_VOUCHER_KHACH_HANG (
    id_vi_voucher  BIGINT IDENTITY(1,1) NOT NULL,
    id_khach_hang  INT                  NOT NULL,
    id_khuyen_mai  INT                  NOT NULL,
    nguon_nhan     VARCHAR(20)          NOT NULL,
    ngay_nhan      DATETIME2            NOT NULL CONSTRAINT DF_VV_NgayNhan DEFAULT (SYSDATETIME()),
    ngay_het_han   DATETIME2            NOT NULL,
    trang_thai     VARCHAR(20)          NOT NULL CONSTRAINT DF_VV_TrangThai DEFAULT ('UNUSED'),
    CONSTRAINT PK_VI_VOUCHER_KHACH_HANG PRIMARY KEY (id_vi_voucher),
    CONSTRAINT FK_VV_KHACH_HANG FOREIGN KEY (id_khach_hang) REFERENCES dbo.KHACH_HANG(id_khach_hang),
    CONSTRAINT FK_VV_KHUYEN_MAI FOREIGN KEY (id_khuyen_mai) REFERENCES dbo.KHUYEN_MAI(id_khuyen_mai),
    CONSTRAINT CK_VV_NguonNhan CHECK (nguon_nhan IN ('COLLECT', 'REDEEM_POINTS', 'TIER_REWARD')),
    CONSTRAINT CK_VV_TrangThai CHECK (trang_thai IN ('UNUSED', 'USED', 'EXPIRED')),
    CONSTRAINT CK_VV_Ngay CHECK (ngay_het_han >= ngay_nhan)
);
GO

-- =============================================================================
-- 10. BẢNG DON_HANG (27 cột)
-- =============================================================================
CREATE TABLE dbo.DON_HANG (
    id_don_hang        BIGINT IDENTITY(1,1) NOT NULL,
    ma_don_hang        VARCHAR(25)          NOT NULL,
    id_khach_hang      INT                  NULL,
    id_nhan_vien       INT                  NULL,
    id_ca_lam          INT                  NULL,
    id_khuyen_mai      INT                  NULL,
    kenh_ban           VARCHAR(15)          NOT NULL,
    hinh_thuc_phuc_vu  VARCHAR(20)          NOT NULL,
    so_ban_the_rung    NVARCHAR(30)         NULL,
    gio_hen_lay        DATETIME2            NULL,
    ma_nhan_do         VARCHAR(12)          NULL,
    tong_tien_mon      BIGINT               NOT NULL,
    tien_giam_voucher  BIGINT               NOT NULL CONSTRAINT DF_DH_GiamVoucher DEFAULT (0),
    diem_su_dung       INT                  NOT NULL CONSTRAINT DF_DH_DiemSD DEFAULT (0),
    tien_giam_diem     BIGINT               NOT NULL CONSTRAINT DF_DH_GiamDiem DEFAULT (0),
    tong_thanh_toan    BIGINT               NOT NULL,
    diem_cong_them     INT                  NOT NULL CONSTRAINT DF_DH_DiemCong DEFAULT (0),
    phuong_thuc_tt     VARCHAR(20)          NOT NULL,
    tien_khach_dua     BIGINT               NULL,
    tien_thoi_lai      BIGINT               NULL,
    trang_thai_tt      VARCHAR(20)          NOT NULL,
    trang_thai_don     VARCHAR(25)          NOT NULL,
    ly_do_huy          NVARCHAR(300)        NULL,
    ghi_chu_hoan_tien  NVARCHAR(300)        NULL,
    so_sao_danh_gia    INT                  NULL,
    noi_dung_danh_gia  NVARCHAR(500)        NULL,
    ngay_tao           DATETIME2            NOT NULL CONSTRAINT DF_DH_NgayTao DEFAULT (SYSDATETIME()),
    CONSTRAINT PK_DON_HANG PRIMARY KEY (id_don_hang),
    CONSTRAINT UK_DON_HANG_Ma UNIQUE (ma_don_hang),
    CONSTRAINT FK_DON_HANG_KHACH_HANG FOREIGN KEY (id_khach_hang) REFERENCES dbo.KHACH_HANG(id_khach_hang),
    CONSTRAINT FK_DON_HANG_NHAN_VIEN FOREIGN KEY (id_nhan_vien) REFERENCES dbo.NHAN_VIEN(id_nhan_vien),
    CONSTRAINT FK_DON_HANG_PHIEN_CA_LAM_VIEC FOREIGN KEY (id_ca_lam) REFERENCES dbo.PHIEN_CA_LAM_VIEC(id_ca_lam),
    CONSTRAINT FK_DON_HANG_KHUYEN_MAI FOREIGN KEY (id_khuyen_mai) REFERENCES dbo.KHUYEN_MAI(id_khuyen_mai),
    CONSTRAINT CK_DON_HANG_KenhBan CHECK (kenh_ban IN ('POS', 'WEB')),
    CONSTRAINT CK_DON_HANG_PhucVu CHECK (hinh_thuc_phuc_vu IN ('DINE_IN', 'TAKE_AWAY', 'PICKUP_AT_COUNTER')),
    CONSTRAINT CK_DON_HANG_PhuongThucTT CHECK (phuong_thuc_tt IN ('CASH', 'QR_TRANSFER', 'E_WALLET')),
    CONSTRAINT CK_DON_HANG_TrangThaiTT CHECK (trang_thai_tt IN ('UNPAID', 'PAID', 'REFUNDED')),
    CONSTRAINT CK_DON_HANG_TrangThaiDon CHECK (trang_thai_don IN ('PENDING_CONFIRM', 'PREPARING', 'READY', 'COMPLETED', 'CANCELLED')),
    CONSTRAINT CK_DON_HANG_Tien CHECK (tong_tien_mon >= 0 AND tien_giam_voucher >= 0 AND tien_giam_diem >= 0 AND tong_thanh_toan >= 0),
    CONSTRAINT CK_DON_HANG_DanhGia CHECK (so_sao_danh_gia IS NULL OR (so_sao_danh_gia BETWEEN 1 AND 5))
);
GO

-- =============================================================================
-- 11. BẢNG CHI_TIET_DON_HANG (9 cột)
-- =============================================================================
CREATE TABLE dbo.CHI_TIET_DON_HANG (
    id_ct_don_hang     BIGINT IDENTITY(1,1) NOT NULL,
    id_don_hang        BIGINT               NOT NULL,
    id_kich_co_sp      INT                  NOT NULL,
    so_luong           INT                  NOT NULL,
    don_gia_chot       BIGINT               NOT NULL,
    tong_tien_topping  BIGINT               NOT NULL CONSTRAINT DF_CTDH_TienTopping DEFAULT (0),
    muc_duong          NVARCHAR(20)         NULL,
    muc_da             NVARCHAR(20)         NULL,
    ghi_chu_pha_che    NVARCHAR(200)        NULL,
    CONSTRAINT PK_CHI_TIET_DON_HANG PRIMARY KEY (id_ct_don_hang),
    CONSTRAINT FK_CTDH_DON_HANG FOREIGN KEY (id_don_hang) REFERENCES dbo.DON_HANG(id_don_hang) ON DELETE CASCADE,
    CONSTRAINT FK_CTDH_KICH_CO_SP FOREIGN KEY (id_kich_co_sp) REFERENCES dbo.KICH_CO_SAN_PHAM(id_kich_co_sp),
    CONSTRAINT CK_CTDH_SoLuong CHECK (so_luong >= 1),
    CONSTRAINT CK_CTDH_DonGia CHECK (don_gia_chot >= 0 AND tong_tien_topping >= 0)
);
GO

-- =============================================================================
-- 12. BẢNG CHI_TIET_TOPPING (5 cột)
-- =============================================================================
CREATE TABLE dbo.CHI_TIET_TOPPING (
    id_ct_topping   BIGINT IDENTITY(1,1) NOT NULL,
    id_ct_don_hang  BIGINT               NOT NULL,
    id_topping      INT                  NOT NULL,
    so_luong        INT                  NOT NULL,
    don_gia_chot    BIGINT               NOT NULL,
    CONSTRAINT PK_CHI_TIET_TOPPING PRIMARY KEY (id_ct_topping),
    CONSTRAINT FK_CTTP_CT_DON_HANG FOREIGN KEY (id_ct_don_hang) REFERENCES dbo.CHI_TIET_DON_HANG(id_ct_don_hang) ON DELETE CASCADE,
    CONSTRAINT FK_CTTP_TOPPING FOREIGN KEY (id_topping) REFERENCES dbo.TOPPING(id_topping),
    CONSTRAINT CK_CTTP_SoLuong CHECK (so_luong >= 1),
    CONSTRAINT CK_CTTP_DonGia CHECK (don_gia_chot >= 0)
);
GO

-- =============================================================================
-- 13. BẢNG NHAT_KY_HE_THONG (9 cột)
-- =============================================================================
CREATE TABLE dbo.NHAT_KY_HE_THONG (
    id_nhat_ky     BIGINT IDENTITY(1,1) NOT NULL,
    id_nhan_vien   INT                  NOT NULL,
    loai_hanh_dong VARCHAR(40)          NOT NULL,
    bang_tac_dong  VARCHAR(50)          NULL,
    ma_ban_ghi     VARCHAR(50)          NULL,
    du_lieu_cu     NVARCHAR(MAX)        NULL,
    du_lieu_moi    NVARCHAR(MAX)        NULL,
    dia_chi_ip     VARCHAR(45)          NULL,
    thoi_gian      DATETIME2            NOT NULL CONSTRAINT DF_NK_ThoiGian DEFAULT (SYSDATETIME()),
    CONSTRAINT PK_NHAT_KY_HE_THONG PRIMARY KEY (id_nhat_ky),
    CONSTRAINT FK_NHAT_KY_NHAN_VIEN FOREIGN KEY (id_nhan_vien) REFERENCES dbo.NHAN_VIEN(id_nhan_vien)
);
GO

-- =============================================================================
-- INDEX HỖ TRỢ TRUY VẤN TỐC ĐỘ CAO & FILTERED UNIQUE INDEX
-- =============================================================================
CREATE UNIQUE NONCLUSTERED INDEX UX_KHACH_HANG_Email ON dbo.KHACH_HANG(email) WHERE email IS NOT NULL;
CREATE INDEX IX_SAN_PHAM_DanhMuc ON dbo.SAN_PHAM(id_danh_muc);
CREATE INDEX IX_VI_VOUCHER_KhachHang ON dbo.VI_VOUCHER_KHACH_HANG(id_khach_hang, trang_thai);
CREATE INDEX IX_DON_HANG_NgayTao ON dbo.DON_HANG(ngay_tao);
CREATE INDEX IX_DON_HANG_TrangThaiDon ON dbo.DON_HANG(trang_thai_don);
CREATE INDEX IX_DON_HANG_KhachHang ON dbo.DON_HANG(id_khach_hang);
CREATE INDEX IX_CTDH_DonHang ON dbo.CHI_TIET_DON_HANG(id_don_hang);
GO

-- =============================================================================
-- STORED PROCEDURES (HỖ TRỢ GỌI BẰNG JdbcUtil.createPreStmt("{CALL ...}"))
-- =============================================================================
CREATE PROCEDURE dbo.sp_revenue_by_day
    @tu_ngay  DATE,
    @den_ngay DATE
AS
BEGIN
    SET NOCOUNT ON;
    SELECT
        CAST(ngay_tao AS DATE) AS ngay,
        COUNT(*) AS so_don_hoan_tat,
        SUM(tong_thanh_toan) AS doanh_thu
    FROM dbo.DON_HANG
    WHERE trang_thai_don = 'COMPLETED'
      AND trang_thai_tt = 'PAID'
      AND CAST(ngay_tao AS DATE) BETWEEN @tu_ngay AND @den_ngay
    GROUP BY CAST(ngay_tao AS DATE)
    ORDER BY ngay DESC;
END
GO

CREATE PROCEDURE dbo.sp_top_5_best_selling_drinks
AS
BEGIN
    SET NOCOUNT ON;
    SELECT TOP 5
        sp.id_san_pham,
        sp.ma_san_pham,
        sp.ten_san_pham,
        SUM(ct.so_luong) AS tong_ly_ban_ra,
        SUM(ct.so_luong * ct.don_gia_chot + ct.tong_tien_topping) AS doanh_so_mon
    FROM dbo.CHI_TIET_DON_HANG ct
    INNER JOIN dbo.DON_HANG dh ON dh.id_don_hang = ct.id_don_hang
    INNER JOIN dbo.KICH_CO_SAN_PHAM kc ON kc.id_kich_co_sp = ct.id_kich_co_sp
    INNER JOIN dbo.SAN_PHAM sp ON sp.id_san_pham = kc.id_san_pham
    WHERE dh.trang_thai_don = 'COMPLETED'
    GROUP BY sp.id_san_pham, sp.ma_san_pham, sp.ten_san_pham
    ORDER BY tong_ly_ban_ra DESC;
END
GO

-- =============================================================================
-- DỮ LIỆU MẪU (SEED DATA CHO TẤT CẢ 13 BẢNG)
-- =============================================================================

-- 1. DANH_MUC
INSERT INTO dbo.DANH_MUC (ma_danh_muc, ten_danh_muc, mo_ta, thu_tu_hien_thi, trang_thai)
VALUES
('DM01', N'Cà phê Việt Nam', N'Cà phê phin truyền thống đậm đà hương vị Việt', 1, 'ACTIVE'),
('DM02', N'Trà trái cây & Trà sữa', N'Các dòng trà tươi thanh mát và trà sữa trân châu', 2, 'ACTIVE'),
('DM03', N'Đá xay & Matcha', N'Thức uống đá xay mát lạnh và Matcha Nhật Bản', 3, 'ACTIVE'),
('DM04', N'Bánh ngọt ăn kèm', N'Bánh mì que, Tiramisu, Croissant nướng mới mỗi ngày', 4, 'ACTIVE');
GO

-- 2. SAN_PHAM
INSERT INTO dbo.SAN_PHAM (ma_san_pham, id_danh_muc, ten_san_pham, hinh_anh, mo_ta, cho_phep_duong_da, cho_phep_topping, la_mon_ban_chay, la_mon_moi, trang_thai)
VALUES
('SP001', 1, N'Cà phê sữa đá', '/assets/img/ca-phe-sua-da.jpg', N'Cà phê Robusta rang mộc kết hợp sữa đặc truyền thống', 1, 1, 1, 0, 'AVAILABLE'),
('SP002', 1, N'Bạc xỉu đá', '/assets/img/bac-xiu.jpg', N'Sữa tươi béo ngậy hòa quyện cùng cà phê phin dịu nhẹ', 1, 1, 1, 0, 'AVAILABLE'),
('SP003', 2, N'Trà đào cam sả', '/assets/img/tra-dao-cam-sa.jpg', N'Trà đen ủ lạnh cùng đào miếng giòn và hương sả cam tươi', 1, 1, 1, 0, 'AVAILABLE'),
('SP004', 2, N'Trà sữa Oolong nướng', '/assets/img/tra-sua-oolong.jpg', N'Trà Oolong rang thơm đậm kết hợp kem sữa mịn màng', 1, 1, 0, 1, 'AVAILABLE'),
('SP005', 3, N'Matcha Latte đá xay', '/assets/img/matcha-da-xay.jpg', N'Bột Matcha Uji Nhật Bản xay nhuyễn cùng sữa tươi và kem', 0, 1, 0, 1, 'AVAILABLE'),
('SP006', 4, N'Bánh Croissant bơ tỏi', '/assets/img/croissant.jpg', N'Bánh sừng bò ngàn lớp giòn rụm thơm hương bơ Pháp', 0, 0, 0, 0, 'AVAILABLE');
GO

-- 3. KICH_CO_SAN_PHAM
INSERT INTO dbo.KICH_CO_SAN_PHAM (id_san_pham, ten_kich_co, gia_ban, trang_thai)
VALUES
(1, N'Size S', 29000, 'AVAILABLE'),
(1, N'Size M', 35000, 'AVAILABLE'),
(1, N'Size L', 42000, 'AVAILABLE'),
(2, N'Size S', 29000, 'AVAILABLE'),
(2, N'Size M', 35000, 'AVAILABLE'),
(3, N'Size M', 45000, 'AVAILABLE'),
(3, N'Size L', 55000, 'AVAILABLE'),
(4, N'Size M', 49000, 'AVAILABLE'),
(4, N'Size L', 59000, 'AVAILABLE'),
(5, N'Size M', 55000, 'AVAILABLE'),
(6, N'Tiêu chuẩn', 32000, 'AVAILABLE');
GO

-- 4. TOPPING
INSERT INTO dbo.TOPPING (ma_topping, ten_topping, gia_topping, hinh_anh, trang_thai)
VALUES
('TP01', N'Trân châu trắng', 10000, '/assets/img/tp-tran-chau-trang.jpg', 'AVAILABLE'),
('TP02', N'Kem phô mai (Macchiato)', 12000, '/assets/img/tp-kem-pho-mai.jpg', 'AVAILABLE'),
('TP03', N'Thạch đào giòn', 10000, '/assets/img/tp-thach-dao.jpg', 'AVAILABLE'),
('TP04', N'Shot Espresso thêm', 15000, '/assets/img/tp-espresso.jpg', 'AVAILABLE');
GO

-- 5. NHAN_VIEN
INSERT INTO dbo.NHAN_VIEN (ma_nhan_vien, ten_dang_nhap, email, mat_khau_bam, ho_ten, so_dien_thoai, dia_chi, anh_dai_dien, vai_tro, so_lan_sai_mk, dang_hoat_dong)
VALUES
('NV001', 'admin_an', 'an.nguyen@polycoffee.vn', '$2a$10$PolyCoffeeHashAdmin001', N'Nguyễn Văn An', '0901112233', N'120 Nguyễn Văn Linh, Q.7, TP.HCM', '/assets/avatar/nv001.jpg', 'ADMIN', 0, 1),
('NV002', 'cashier_binh', 'binh.tran@polycoffee.vn', '$2a$10$PolyCoffeeHashCashier002', N'Trần Thị Bình', '0902223344', N'45 Lê Lợi, Q.1, TP.HCM', '/assets/avatar/nv002.jpg', 'CASHIER', 0, 1),
('NV003', 'barista_cuong', 'cuong.le@polycoffee.vn', '$2a$10$PolyCoffeeHashBarista003', N'Lê Minh Cường', '0903334455', N'88 Tô Ký, Q.12, TP.HCM', '/assets/avatar/nv003.jpg', 'BARISTA', 0, 1);
GO

-- 6. PHIEN_CA_LAM_VIEC
INSERT INTO dbo.PHIEN_CA_LAM_VIEC (ma_ca_lam, id_nhan_vien, thoi_gian_mo_ca, thoi_gian_ket_ca, tien_dau_ca, tong_tien_mat_ht, tong_tien_qr_ht, tien_mat_thuc_te, ghi_chu_chenh_lech, trang_thai)
VALUES
('CA20261112_S', 2, '2026-11-12 07:00:00', '2026-11-12 15:00:00', 1000000, 1450000, 820000, 1450000, N'Khớp sổ quỹ ca sáng', 'CLOSED'),
('CA20261113_S', 2, SYSDATETIME(), NULL, 1000000, 109800, 90000, NULL, NULL, 'OPEN');
GO

-- 7. KHACH_HANG
INSERT INTO dbo.KHACH_HANG (ma_khach_hang, so_dien_thoai, ho_ten, email, mat_khau_bam, ngay_sinh, anh_dai_dien, diem_kha_dung, tong_diem_tich_luy, hang_thanh_vien, ma_pin_kich_hoat, da_kich_hoat, dang_hoat_dong)
VALUES
('KH001', '0912345678', N'Phạm Hoàng Minh', 'minh.pham@gmail.com', '$2a$10$PolyCoffeeCustomer001', '2001-05-14', '/assets/avatar/kh001.jpg', 120, 450, N'Vàng', NULL, 1, 1),
('KH002', '0987654321', N'Lê Ngọc Thảo', 'thao.le@gmail.com', '$2a$10$PolyCoffeeCustomer002', '2003-09-20', '/assets/avatar/kh002.jpg', 45, 110, N'Bạc', NULL, 1, 1),
('KH003', '0933445566', N'Võ Quốc Huy', NULL, NULL, NULL, NULL, 15, 15, N'Đồng', '482910', 0, 1);
GO

-- 8. KHUYEN_MAI
INSERT INTO dbo.KHUYEN_MAI (ma_code_km, ten_khuyen_mai, mo_ta, loai_giam_gia, gia_tri_giam, giam_toi_da, don_toi_thieu, pham_vi_ap_dung, hang_toi_thieu, diem_can_doi, tong_luot_phat_hanh, so_luot_da_dung, gioi_han_moi_khach, ngay_bat_dau, ngay_ket_thuc, trang_thai)
VALUES
('POLY10', N'Giảm 10% đơn PolyCoffee', N'Áp dụng cho toàn bộ đơn từ 60.000đ tại quầy và Web', 'PERCENT', 10, 30000, 60000, 'ALL', NULL, 0, 500, 12, 3, '2026-11-01 00:00:00', '2026-12-31 23:59:59', 'ACTIVE'),
('CHAO20K', N'Giảm 20.000đ cho đơn Web', N'Đổi điểm tích lũy cất vào Ví Voucher, dùng cho đơn Online trên 100.000đ', 'FIXED_AMOUNT', 20000, 20000, 100000, 'REDEEM_POINTS', NULL, 20, 200, 5, 1, '2026-11-01 00:00:00', '2026-12-15 23:59:59', 'ACTIVE'),
('VANG30K', N'Ưu đãi hạng Vàng giảm 30.000đ', N'Tự động phát vào Ví Voucher cho thành viên từ hạng Vàng trở lên', 'FIXED_AMOUNT', 30000, 30000, 120000, 'TIER_REWARD', N'Vàng', 0, 300, 1, 1, '2026-11-01 00:00:00', '2026-12-31 23:59:59', 'ACTIVE');
GO

-- 9. VI_VOUCHER_KHACH_HANG
INSERT INTO dbo.VI_VOUCHER_KHACH_HANG (id_khach_hang, id_khuyen_mai, nguon_nhan, ngay_nhan, ngay_het_han, trang_thai)
VALUES
(1, 3, 'TIER_REWARD', '2026-11-05 08:00:00', '2026-12-31 23:59:59', 'UNUSED'),
(2, 2, 'REDEEM_POINTS', '2026-11-10 14:30:00', '2026-12-15 23:59:59', 'UNUSED'),
(1, 1, 'COLLECT', '2026-11-01 09:00:00', '2026-11-12 23:59:59', 'USED');
GO

-- 10. DON_HANG
INSERT INTO dbo.DON_HANG (ma_don_hang, id_khach_hang, id_nhan_vien, id_ca_lam, id_khuyen_mai, kenh_ban, hinh_thuc_phuc_vu, so_ban_the_rung, gio_hen_lay, ma_nhan_do, tong_tien_mon, tien_giam_voucher, diem_su_dung, tien_giam_diem, tong_thanh_toan, diem_cong_them, phuong_thuc_tt, tien_khach_dua, tien_thoi_lai, trang_thai_tt, trang_thai_don, so_sao_danh_gia, noi_dung_danh_gia)
VALUES
('ORD-20261113-0001', 1, 2, 2, 1, 'POS', 'DINE_IN', N'Bàn 05', NULL, 'PC-001', 122000, 12200, 0, 0, 109800, 10, 'CASH', 110000, 200, 'PAID', 'COMPLETED', 5, N'Cà phê đậm đà, phục vụ nhanh'),
('ORD-20261113-0002', 2, 2, 2, NULL, 'WEB', 'PICKUP_AT_COUNTER', NULL, DATEADD(MINUTE, 20, SYSDATETIME()), 'PC-002', 90000, 0, 0, 0, 90000, 9, 'QR_TRANSFER', NULL, NULL, 'PAID', 'PREPARING', NULL, NULL);
GO

-- 11. CHI_TIET_DON_HANG
INSERT INTO dbo.CHI_TIET_DON_HANG (id_don_hang, id_kich_co_sp, so_luong, don_gia_chot, tong_tien_topping, muc_duong, muc_da, ghi_chu_pha_che)
VALUES
(1, 2, 2, 35000, 20000, N'70%', N'100%', N'Ít sữa đặc hơn bình thường'),
(1, 11, 1, 32000, 0, NULL, NULL, N'Hâm nóng bánh trước khi giao'),
(2, 6, 2, 45000, 0, N'50%', N'70%', N'Thêm lát cam tươi');
GO

-- 12. CHI_TIET_TOPPING
INSERT INTO dbo.CHI_TIET_TOPPING (id_ct_don_hang, id_topping, so_luong, don_gia_chot)
VALUES
(1, 1, 2, 10000);
GO

-- 13. NHAT_KY_HE_THONG
INSERT INTO dbo.NHAT_KY_HE_THONG (id_nhan_vien, loai_hanh_dong, bang_tac_dong, ma_ban_ghi, du_lieu_cu, du_lieu_moi, dia_chi_ip)
VALUES
(1, 'CREATE_PRODUCT', 'SAN_PHAM', 'SP001', NULL, N'{"ten_san_pham":"Cà phê sữa đá","trang_thai":"AVAILABLE"}', '127.0.0.1'),
(2, 'OPEN_SHIFT', 'PHIEN_CA_LAM_VIEC', 'CA20261113_S', NULL, N'{"tien_dau_ca":1000000,"trang_thai":"OPEN"}', '192.168.1.15');
GO

-- Kiểm tra nhanh số lượng bản ghi đã khởi tạo
SELECT 'DANH_MUC' AS TenBang, COUNT(*) AS SoBanGhi FROM dbo.DANH_MUC
UNION ALL SELECT 'SAN_PHAM', COUNT(*) FROM dbo.SAN_PHAM
UNION ALL SELECT 'KICH_CO_SAN_PHAM', COUNT(*) FROM dbo.KICH_CO_SAN_PHAM
UNION ALL SELECT 'TOPPING', COUNT(*) FROM dbo.TOPPING
UNION ALL SELECT 'NHAN_VIEN', COUNT(*) FROM dbo.NHAN_VIEN
UNION ALL SELECT 'PHIEN_CA_LAM_VIEC', COUNT(*) FROM dbo.PHIEN_CA_LAM_VIEC
UNION ALL SELECT 'KHACH_HANG', COUNT(*) FROM dbo.KHACH_HANG
UNION ALL SELECT 'KHUYEN_MAI', COUNT(*) FROM dbo.KHUYEN_MAI
UNION ALL SELECT 'VI_VOUCHER_KHACH_HANG', COUNT(*) FROM dbo.VI_VOUCHER_KHACH_HANG
UNION ALL SELECT 'DON_HANG', COUNT(*) FROM dbo.DON_HANG
UNION ALL SELECT 'CHI_TIET_DON_HANG', COUNT(*) FROM dbo.CHI_TIET_DON_HANG
UNION ALL SELECT 'CHI_TIET_TOPPING', COUNT(*) FROM dbo.CHI_TIET_TOPPING
UNION ALL SELECT 'NHAT_KY_HE_THONG', COUNT(*) FROM dbo.NHAT_KY_HE_THONG;
GO
