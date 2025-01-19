
create table NGUOI_DUNG (
    maNguoiDung int,
    tenNguoiDung varchar(50),
    email varchar(100),
    matKhau varchar(255),
    sdt char(15),
    diaChiMacDinh varchar(255),
    vaiTro varchar(20),
    ngayTao datetime,
    constraint pk_NGUOI_DUNG primary key(maNguoiDung)
);

create table THUONG_HIEU (
    maThuongHieu int,
    tenThuongHieu varchar(50),
    moTa varchar(255),
    ngayTao datetime,
    constraint pk_THUONG_HIEU primary key(maThuongHieu)
);

create table DANH_MUC (
    maDanhMuc int,
    tenDanhMuc varchar(50),
    ngayTao datetime,
    constraint pk_DANH_MUC primary key(maDanhMuc)
);

create table SAN_PHAM (
    maSanPham int,
    tenSanPham varchar(100),
    moTa varchar(255),
    gia float,
    maDanhMuc int,
    maThuongHieu int,
    anhSanPham varchar(255),
    ngayTao datetime,
    ngayCapNhat datetime,
    constraint pk_SAN_PHAM primary key(maSanPham),
    constraint fk_SAN_PHAM_DANH_MUC foreign key(maDanhMuc) references DANH_MUC(maDanhMuc),
    constraint fk_SAN_PHAM_THUONG_HIEU foreign key(maThuongHieu) references THUONG_HIEU(maThuongHieu)
);


create table BIEN_THE_SAN_PHAM (
    maBienThe int,
    maSanPham int,
    kichThuoc varchar(255),
    mauSac varchar(50),
    soLuongTon int,
    constraint pk_BIEN_THE_SAN_PHAM primary key(maBienThe),
    constraint fk_BIEN_THE_SAN_PHAM foreign key(maSanPham) references SAN_PHAM(maSanPham)
);

create table DON_HANG (
    maDonHang int,
    maNguoiDung int,
    diaChiGiaoHang varchar(255),
    tongTien float,
    trangThai varchar(20),
    ngayTao datetime,
    ngayCapNhat datetime,
    constraint pk_DON_HANG primary key(maDonHang),
    constraint fk_DON_HANG_NGUOI_DUNG foreign key(maNguoiDung) references NGUOI_DUNG(maNguoiDung)
);

create table CHI_TIET_DON_HANG (
    maChiTiet int,
    maDonHang int,
    maBienThe int,
    soLuong int,
    gia float,
    constraint pk_CHI_TIET_DON_HANG primary key(maChiTiet),
    constraint fk_CTDH_DON_HANG foreign key(maDonHang) references DON_HANG(maDonHang),
    constraint fk_CTDH_BIEN_THE foreign key(maBienThe) references BIEN_THE_SAN_PHAM(maBienThe)
);

create table GIO_HANG (
    maGioHang int,
    maNguoiDung int,
    maBienThe int,
    soLuong int,
    ngayTao datetime,
    constraint pk_GIO_HANG primary key(maGioHang),
    constraint fk_GIO_HANG_NGUOI_DUNG foreign key(maNguoiDung) references NGUOI_DUNG(maNguoiDung),
    constraint fk_GIO_HANG_BIEN_THE foreign key(maBienThe) references BIEN_THE_SAN_PHAM(maBienThe)
);

create table DANH_SACH_YEU_THICH (
    maYeuThich int,
    maNguoiDung int,
    maSanPham int,
    ngayTao datetime,
    constraint pk_DANH_SACH_YEU_THICH primary key(maYeuThich),
    constraint fk_DSYT_NGUOI_DUNG foreign key(maNguoiDung) references NGUOI_DUNG(maNguoiDung),
    constraint fk_DSYT_SAN_PHAM foreign key(maSanPham) references SAN_PHAM(maSanPham)
);

create table THANH_TOAN (
    maThanhToan int,
    maDonHang int,
    phuongThuc varchar(20),
    trangThai varchar(20),
    ngayThanhToan datetime,
    constraint pk_THANH_TOAN primary key(maThanhToan),
    constraint fk_THANH_TOAN_DON_HANG foreign key(maDonHang) references DON_HANG(maDonHang)
);

create table KICH_THUOC_SAN_PHAM (
    maKichThuoc char(10) primary key,
    maBienThe int,
    kichThuoc nvarchar(10),
    soLuongTon int, 
    constraint fk_KICH_THUOC_SAN_PHAM foreign key(maBienThe) references BIEN_THE_SAN_PHAM(maBienThe)
);

create table ANH_BIEN_THE (
    maAnh char(10) primary key,
    maBienThe int,
    duongDan nvarchar(255),
    constraint fk_ANH_BIEN_THE foreign key(maBienThe) references BIEN_THE_SAN_PHAM(maBienThe)
);