-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jan 21, 2025 at 02:44 PM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `shopgiay`
--

-- --------------------------------------------------------

--
-- Table structure for table `anh_bien_the`
--

CREATE TABLE `anh_bien_the` (
  `maAnh` int(11) NOT NULL,
  `maSanPham` int(11) DEFAULT NULL,
  `duongDan` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `anh_bien_the`
--

INSERT INTO `anh_bien_the` (`maAnh`, `maSanPham`, `duongDan`) VALUES(20, 85, '/uploads/anh_bien_the/W+AIR+MAX+DN.png');
INSERT INTO `anh_bien_the` (`maAnh`, `maSanPham`, `duongDan`) VALUES(21, 85, '/uploads/anh_bien_the/W+AIR+MAX+DN (1).png');
INSERT INTO `anh_bien_the` (`maAnh`, `maSanPham`, `duongDan`) VALUES(22, 85, '/uploads/anh_bien_the/W+AIR+MAX+DN (2).png');
INSERT INTO `anh_bien_the` (`maAnh`, `maSanPham`, `duongDan`) VALUES(23, 85, '/uploads/anh_bien_the/W+AIR+MAX+DN (3).png');
INSERT INTO `anh_bien_the` (`maAnh`, `maSanPham`, `duongDan`) VALUES(24, 86, '/uploads/anh_bien_the/W+AIR+MAX+DN+ISA.png');
INSERT INTO `anh_bien_the` (`maAnh`, `maSanPham`, `duongDan`) VALUES(25, 86, '/uploads/anh_bien_the/W+AIR+MAX+DN+ISA (1).png');
INSERT INTO `anh_bien_the` (`maAnh`, `maSanPham`, `duongDan`) VALUES(26, 86, '/uploads/anh_bien_the/W+AIR+MAX+DN+ISA (2).png');
INSERT INTO `anh_bien_the` (`maAnh`, `maSanPham`, `duongDan`) VALUES(27, 86, '/uploads/anh_bien_the/W+AIR+MAX+DN+ISA (3).png');
INSERT INTO `anh_bien_the` (`maAnh`, `maSanPham`, `duongDan`) VALUES(28, 87, '/uploads/anh_bien_the/AIR+MAX+DN+(PS).png');
INSERT INTO `anh_bien_the` (`maAnh`, `maSanPham`, `duongDan`) VALUES(29, 87, '/uploads/anh_bien_the/AIR+MAX+DN+(PS) (1).png');
INSERT INTO `anh_bien_the` (`maAnh`, `maSanPham`, `duongDan`) VALUES(30, 87, '/uploads/anh_bien_the/AIR+MAX+DN+(PS) (2).png');
INSERT INTO `anh_bien_the` (`maAnh`, `maSanPham`, `duongDan`) VALUES(31, 87, '/uploads/anh_bien_the/AIR+MAX+DN+(PS) (3).png');
INSERT INTO `anh_bien_the` (`maAnh`, `maSanPham`, `duongDan`) VALUES(32, 88, '/uploads/anh_bien_the/BLAZER+MID+\'77+VNTG.png');
INSERT INTO `anh_bien_the` (`maAnh`, `maSanPham`, `duongDan`) VALUES(33, 88, '/uploads/anh_bien_the/BLAZER+MID+\'77+VNTG (1).png');
INSERT INTO `anh_bien_the` (`maAnh`, `maSanPham`, `duongDan`) VALUES(34, 88, '/uploads/anh_bien_the/BLAZER+MID+\'77+VNTG (2).png');
INSERT INTO `anh_bien_the` (`maAnh`, `maSanPham`, `duongDan`) VALUES(35, 88, '/uploads/anh_bien_the/BLAZER+MID+\'77+VNTG (3).png');
INSERT INTO `anh_bien_the` (`maAnh`, `maSanPham`, `duongDan`) VALUES(36, 89, '/uploads/anh_bien_the/W+BLAZER+MID+\'77.png');
INSERT INTO `anh_bien_the` (`maAnh`, `maSanPham`, `duongDan`) VALUES(37, 89, '/uploads/anh_bien_the/W+BLAZER+MID+\'77 (1).png');
INSERT INTO `anh_bien_the` (`maAnh`, `maSanPham`, `duongDan`) VALUES(38, 89, '/uploads/anh_bien_the/W+BLAZER+MID+\'77 (2).png');
INSERT INTO `anh_bien_the` (`maAnh`, `maSanPham`, `duongDan`) VALUES(39, 89, '/uploads/anh_bien_the/W+BLAZER+MID+\'77 (3).png');
INSERT INTO `anh_bien_the` (`maAnh`, `maSanPham`, `duongDan`) VALUES(40, 90, '/uploads/anh_bien_the/NIKE+BLAZER+MID+\'77+(GS).png');
INSERT INTO `anh_bien_the` (`maAnh`, `maSanPham`, `duongDan`) VALUES(41, 90, '/uploads/anh_bien_the/NIKE+BLAZER+MID+\'77+(GS) (1).png');
INSERT INTO `anh_bien_the` (`maAnh`, `maSanPham`, `duongDan`) VALUES(42, 90, '/uploads/anh_bien_the/NIKE+BLAZER+MID+\'77+(GS) (2).png');
INSERT INTO `anh_bien_the` (`maAnh`, `maSanPham`, `duongDan`) VALUES(43, 90, '/uploads/anh_bien_the/NIKE+BLAZER+MID+\'77+(GS) (3).png');
INSERT INTO `anh_bien_the` (`maAnh`, `maSanPham`, `duongDan`) VALUES(44, 91, '/uploads/anh_bien_the/NIKE+BLAZER+MID+\'77+(PS).png');
INSERT INTO `anh_bien_the` (`maAnh`, `maSanPham`, `duongDan`) VALUES(45, 91, '/uploads/anh_bien_the/NIKE+BLAZER+MID+\'77+(PS) (1).png');
INSERT INTO `anh_bien_the` (`maAnh`, `maSanPham`, `duongDan`) VALUES(46, 91, '/uploads/anh_bien_the/NIKE+BLAZER+MID+\'77+(PS) (2).png');
INSERT INTO `anh_bien_the` (`maAnh`, `maSanPham`, `duongDan`) VALUES(47, 91, '/uploads/anh_bien_the/NIKE+BLAZER+MID+\'77+(PS) (3).png');
INSERT INTO `anh_bien_the` (`maAnh`, `maSanPham`, `duongDan`) VALUES(48, 92, '/uploads/anh_bien_the/AIR+JORDAN+1+LOW.png');
INSERT INTO `anh_bien_the` (`maAnh`, `maSanPham`, `duongDan`) VALUES(49, 92, '/uploads/anh_bien_the/AIR+JORDAN+1+LOW (1).png');
INSERT INTO `anh_bien_the` (`maAnh`, `maSanPham`, `duongDan`) VALUES(50, 92, '/uploads/anh_bien_the/AIR+JORDAN+1+LOW (2).png');
INSERT INTO `anh_bien_the` (`maAnh`, `maSanPham`, `duongDan`) VALUES(51, 92, '/uploads/anh_bien_the/AIR+JORDAN+1+LOW (3).png');
INSERT INTO `anh_bien_the` (`maAnh`, `maSanPham`, `duongDan`) VALUES(52, 93, '/uploads/anh_bien_the/AIR+JORDAN+1+MID+SE.png');
INSERT INTO `anh_bien_the` (`maAnh`, `maSanPham`, `duongDan`) VALUES(53, 93, '/uploads/anh_bien_the/AIR+JORDAN+1+MID+SE (1).png');
INSERT INTO `anh_bien_the` (`maAnh`, `maSanPham`, `duongDan`) VALUES(54, 93, '/uploads/anh_bien_the/AIR+JORDAN+1+MID+SE (2).png');
INSERT INTO `anh_bien_the` (`maAnh`, `maSanPham`, `duongDan`) VALUES(55, 93, '/uploads/anh_bien_the/AIR+JORDAN+1+MID+SE (3).png');
INSERT INTO `anh_bien_the` (`maAnh`, `maSanPham`, `duongDan`) VALUES(56, 94, '/uploads/anh_bien_the/AIR+JORDAN+1+LOW+SE.png');
INSERT INTO `anh_bien_the` (`maAnh`, `maSanPham`, `duongDan`) VALUES(57, 94, '/uploads/anh_bien_the/AIR+JORDAN+1+LOW+SE (1).png');
INSERT INTO `anh_bien_the` (`maAnh`, `maSanPham`, `duongDan`) VALUES(58, 94, '/uploads/anh_bien_the/AIR+JORDAN+1+LOW+SE (2).png');
INSERT INTO `anh_bien_the` (`maAnh`, `maSanPham`, `duongDan`) VALUES(59, 94, '/uploads/anh_bien_the/AIR+JORDAN+1+LOW+SE (3).png');
INSERT INTO `anh_bien_the` (`maAnh`, `maSanPham`, `duongDan`) VALUES(60, 84, '/uploads/anh_bien_the/AIR+MAX+DN.png');
INSERT INTO `anh_bien_the` (`maAnh`, `maSanPham`, `duongDan`) VALUES(61, 84, '/uploads/anh_bien_the/AIR+MAX+DN (1).png');
INSERT INTO `anh_bien_the` (`maAnh`, `maSanPham`, `duongDan`) VALUES(62, 84, '/uploads/anh_bien_the/AIR+MAX+DN (2).png');
INSERT INTO `anh_bien_the` (`maAnh`, `maSanPham`, `duongDan`) VALUES(63, 95, '/uploads/anh_bien_the/NIKE+DUNK+LOW+RETRO.png');
INSERT INTO `anh_bien_the` (`maAnh`, `maSanPham`, `duongDan`) VALUES(64, 95, '/uploads/anh_bien_the/NIKE+DUNK+LOW+RETRO (1).png');
INSERT INTO `anh_bien_the` (`maAnh`, `maSanPham`, `duongDan`) VALUES(65, 95, '/uploads/anh_bien_the/NIKE+DUNK+LOW+RETRO (2).png');
INSERT INTO `anh_bien_the` (`maAnh`, `maSanPham`, `duongDan`) VALUES(66, 95, '/uploads/anh_bien_the/NIKE+DUNK+LOW+RETRO (3).png');
INSERT INTO `anh_bien_the` (`maAnh`, `maSanPham`, `duongDan`) VALUES(67, 96, '/uploads/anh_bien_the/W+NIKE+DUNK+LOW.png');
INSERT INTO `anh_bien_the` (`maAnh`, `maSanPham`, `duongDan`) VALUES(68, 96, '/uploads/anh_bien_the/W+NIKE+DUNK+LOW (1).png');
INSERT INTO `anh_bien_the` (`maAnh`, `maSanPham`, `duongDan`) VALUES(69, 96, '/uploads/anh_bien_the/W+NIKE+DUNK+LOW (2).png');
INSERT INTO `anh_bien_the` (`maAnh`, `maSanPham`, `duongDan`) VALUES(70, 96, '/uploads/anh_bien_the/W+NIKE+DUNK+LOW (3).png');
INSERT INTO `anh_bien_the` (`maAnh`, `maSanPham`, `duongDan`) VALUES(71, 97, '/uploads/anh_bien_the/NIKE+DUNK+LOW+(GS).png');
INSERT INTO `anh_bien_the` (`maAnh`, `maSanPham`, `duongDan`) VALUES(72, 97, '/uploads/anh_bien_the/NIKE+DUNK+LOW+(GS) (1).png');
INSERT INTO `anh_bien_the` (`maAnh`, `maSanPham`, `duongDan`) VALUES(73, 97, '/uploads/anh_bien_the/NIKE+DUNK+LOW+(GS) (2).png');
INSERT INTO `anh_bien_the` (`maAnh`, `maSanPham`, `duongDan`) VALUES(74, 97, '/uploads/anh_bien_the/NIKE+DUNK+LOW+(GS) (3).png');
INSERT INTO `anh_bien_the` (`maAnh`, `maSanPham`, `duongDan`) VALUES(75, 98, '/uploads/anh_bien_the/NIKE+DUNK+LOW+(PS).png');
INSERT INTO `anh_bien_the` (`maAnh`, `maSanPham`, `duongDan`) VALUES(76, 98, '/uploads/anh_bien_the/NIKE+DUNK+LOW+(PS) (1).png');
INSERT INTO `anh_bien_the` (`maAnh`, `maSanPham`, `duongDan`) VALUES(77, 98, '/uploads/anh_bien_the/NIKE+DUNK+LOW+(PS) (2).png');
INSERT INTO `anh_bien_the` (`maAnh`, `maSanPham`, `duongDan`) VALUES(78, 98, '/uploads/anh_bien_the/NIKE+DUNK+LOW+(PS) (3).png');

-- --------------------------------------------------------

--
-- Table structure for table `bien_the_san_pham`
--

CREATE TABLE `bien_the_san_pham` (
  `maBienThe` int(11) NOT NULL,
  `maSanPham` int(11) DEFAULT NULL,
  `kichThuoc` varchar(255) DEFAULT NULL,
  `soLuongTon` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `bien_the_san_pham`
--

INSERT INTO `bien_the_san_pham` (`maBienThe`, `maSanPham`, `kichThuoc`, `soLuongTon`) VALUES(56, 84, '35', 6);
INSERT INTO `bien_the_san_pham` (`maBienThe`, `maSanPham`, `kichThuoc`, `soLuongTon`) VALUES(57, 85, '35', 6);
INSERT INTO `bien_the_san_pham` (`maBienThe`, `maSanPham`, `kichThuoc`, `soLuongTon`) VALUES(58, 86, '35', 6);
INSERT INTO `bien_the_san_pham` (`maBienThe`, `maSanPham`, `kichThuoc`, `soLuongTon`) VALUES(59, 87, '35', 6);
INSERT INTO `bien_the_san_pham` (`maBienThe`, `maSanPham`, `kichThuoc`, `soLuongTon`) VALUES(60, 88, '35', 6);
INSERT INTO `bien_the_san_pham` (`maBienThe`, `maSanPham`, `kichThuoc`, `soLuongTon`) VALUES(61, 89, '35', 6);
INSERT INTO `bien_the_san_pham` (`maBienThe`, `maSanPham`, `kichThuoc`, `soLuongTon`) VALUES(62, 90, '35', 6);
INSERT INTO `bien_the_san_pham` (`maBienThe`, `maSanPham`, `kichThuoc`, `soLuongTon`) VALUES(63, 91, '35', 6);
INSERT INTO `bien_the_san_pham` (`maBienThe`, `maSanPham`, `kichThuoc`, `soLuongTon`) VALUES(64, 92, '35', 6);
INSERT INTO `bien_the_san_pham` (`maBienThe`, `maSanPham`, `kichThuoc`, `soLuongTon`) VALUES(65, 93, '35', 6);
INSERT INTO `bien_the_san_pham` (`maBienThe`, `maSanPham`, `kichThuoc`, `soLuongTon`) VALUES(66, 94, '35', 6);
INSERT INTO `bien_the_san_pham` (`maBienThe`, `maSanPham`, `kichThuoc`, `soLuongTon`) VALUES(67, 95, '35', 6);
INSERT INTO `bien_the_san_pham` (`maBienThe`, `maSanPham`, `kichThuoc`, `soLuongTon`) VALUES(68, 96, '35', 6);
INSERT INTO `bien_the_san_pham` (`maBienThe`, `maSanPham`, `kichThuoc`, `soLuongTon`) VALUES(69, 97, '35', 6);
INSERT INTO `bien_the_san_pham` (`maBienThe`, `maSanPham`, `kichThuoc`, `soLuongTon`) VALUES(70, 98, '35', 6);
INSERT INTO `bien_the_san_pham` (`maBienThe`, `maSanPham`, `kichThuoc`, `soLuongTon`) VALUES(71, 84, '36', 10);
INSERT INTO `bien_the_san_pham` (`maBienThe`, `maSanPham`, `kichThuoc`, `soLuongTon`) VALUES(72, 85, '36', 10);
INSERT INTO `bien_the_san_pham` (`maBienThe`, `maSanPham`, `kichThuoc`, `soLuongTon`) VALUES(73, 86, '36', 10);
INSERT INTO `bien_the_san_pham` (`maBienThe`, `maSanPham`, `kichThuoc`, `soLuongTon`) VALUES(74, 87, '36', 10);
INSERT INTO `bien_the_san_pham` (`maBienThe`, `maSanPham`, `kichThuoc`, `soLuongTon`) VALUES(75, 88, '36', 10);
INSERT INTO `bien_the_san_pham` (`maBienThe`, `maSanPham`, `kichThuoc`, `soLuongTon`) VALUES(76, 89, '36', 10);
INSERT INTO `bien_the_san_pham` (`maBienThe`, `maSanPham`, `kichThuoc`, `soLuongTon`) VALUES(77, 90, '36', 10);
INSERT INTO `bien_the_san_pham` (`maBienThe`, `maSanPham`, `kichThuoc`, `soLuongTon`) VALUES(78, 91, '36', 10);
INSERT INTO `bien_the_san_pham` (`maBienThe`, `maSanPham`, `kichThuoc`, `soLuongTon`) VALUES(79, 92, '36', 10);
INSERT INTO `bien_the_san_pham` (`maBienThe`, `maSanPham`, `kichThuoc`, `soLuongTon`) VALUES(80, 93, '36', 10);
INSERT INTO `bien_the_san_pham` (`maBienThe`, `maSanPham`, `kichThuoc`, `soLuongTon`) VALUES(81, 94, '36', 10);
INSERT INTO `bien_the_san_pham` (`maBienThe`, `maSanPham`, `kichThuoc`, `soLuongTon`) VALUES(82, 95, '36', 10);
INSERT INTO `bien_the_san_pham` (`maBienThe`, `maSanPham`, `kichThuoc`, `soLuongTon`) VALUES(83, 96, '36', 10);
INSERT INTO `bien_the_san_pham` (`maBienThe`, `maSanPham`, `kichThuoc`, `soLuongTon`) VALUES(84, 97, '36', 10);
INSERT INTO `bien_the_san_pham` (`maBienThe`, `maSanPham`, `kichThuoc`, `soLuongTon`) VALUES(85, 98, '36', 10);
INSERT INTO `bien_the_san_pham` (`maBienThe`, `maSanPham`, `kichThuoc`, `soLuongTon`) VALUES(86, 98, '37', 13);
INSERT INTO `bien_the_san_pham` (`maBienThe`, `maSanPham`, `kichThuoc`, `soLuongTon`) VALUES(87, 97, '37', 13);
INSERT INTO `bien_the_san_pham` (`maBienThe`, `maSanPham`, `kichThuoc`, `soLuongTon`) VALUES(88, 96, '37', 13);
INSERT INTO `bien_the_san_pham` (`maBienThe`, `maSanPham`, `kichThuoc`, `soLuongTon`) VALUES(89, 95, '37', 13);
INSERT INTO `bien_the_san_pham` (`maBienThe`, `maSanPham`, `kichThuoc`, `soLuongTon`) VALUES(90, 94, '37', 13);
INSERT INTO `bien_the_san_pham` (`maBienThe`, `maSanPham`, `kichThuoc`, `soLuongTon`) VALUES(91, 93, '37', 13);
INSERT INTO `bien_the_san_pham` (`maBienThe`, `maSanPham`, `kichThuoc`, `soLuongTon`) VALUES(92, 92, '37', 13);
INSERT INTO `bien_the_san_pham` (`maBienThe`, `maSanPham`, `kichThuoc`, `soLuongTon`) VALUES(93, 91, '37', 13);
INSERT INTO `bien_the_san_pham` (`maBienThe`, `maSanPham`, `kichThuoc`, `soLuongTon`) VALUES(94, 90, '37', 13);
INSERT INTO `bien_the_san_pham` (`maBienThe`, `maSanPham`, `kichThuoc`, `soLuongTon`) VALUES(95, 89, '37', 13);
INSERT INTO `bien_the_san_pham` (`maBienThe`, `maSanPham`, `kichThuoc`, `soLuongTon`) VALUES(96, 88, '37', 13);
INSERT INTO `bien_the_san_pham` (`maBienThe`, `maSanPham`, `kichThuoc`, `soLuongTon`) VALUES(97, 87, '37', 13);
INSERT INTO `bien_the_san_pham` (`maBienThe`, `maSanPham`, `kichThuoc`, `soLuongTon`) VALUES(98, 86, '37', 13);
INSERT INTO `bien_the_san_pham` (`maBienThe`, `maSanPham`, `kichThuoc`, `soLuongTon`) VALUES(99, 85, '37', 13);
INSERT INTO `bien_the_san_pham` (`maBienThe`, `maSanPham`, `kichThuoc`, `soLuongTon`) VALUES(100, 84, '37', 13);
INSERT INTO `bien_the_san_pham` (`maBienThe`, `maSanPham`, `kichThuoc`, `soLuongTon`) VALUES(101, 84, '38', 4);
INSERT INTO `bien_the_san_pham` (`maBienThe`, `maSanPham`, `kichThuoc`, `soLuongTon`) VALUES(102, 85, '38', 7);
INSERT INTO `bien_the_san_pham` (`maBienThe`, `maSanPham`, `kichThuoc`, `soLuongTon`) VALUES(103, 86, '38', 9);
INSERT INTO `bien_the_san_pham` (`maBienThe`, `maSanPham`, `kichThuoc`, `soLuongTon`) VALUES(104, 87, '38', 9);
INSERT INTO `bien_the_san_pham` (`maBienThe`, `maSanPham`, `kichThuoc`, `soLuongTon`) VALUES(105, 88, '38', 9);
INSERT INTO `bien_the_san_pham` (`maBienThe`, `maSanPham`, `kichThuoc`, `soLuongTon`) VALUES(106, 89, '38', 9);
INSERT INTO `bien_the_san_pham` (`maBienThe`, `maSanPham`, `kichThuoc`, `soLuongTon`) VALUES(107, 90, '38', 9);
INSERT INTO `bien_the_san_pham` (`maBienThe`, `maSanPham`, `kichThuoc`, `soLuongTon`) VALUES(108, 91, '38', 9);
INSERT INTO `bien_the_san_pham` (`maBienThe`, `maSanPham`, `kichThuoc`, `soLuongTon`) VALUES(109, 92, '38', 9);
INSERT INTO `bien_the_san_pham` (`maBienThe`, `maSanPham`, `kichThuoc`, `soLuongTon`) VALUES(110, 93, '38', 9);
INSERT INTO `bien_the_san_pham` (`maBienThe`, `maSanPham`, `kichThuoc`, `soLuongTon`) VALUES(111, 94, '38', 9);
INSERT INTO `bien_the_san_pham` (`maBienThe`, `maSanPham`, `kichThuoc`, `soLuongTon`) VALUES(112, 95, '38', 9);
INSERT INTO `bien_the_san_pham` (`maBienThe`, `maSanPham`, `kichThuoc`, `soLuongTon`) VALUES(113, 96, '38', 9);
INSERT INTO `bien_the_san_pham` (`maBienThe`, `maSanPham`, `kichThuoc`, `soLuongTon`) VALUES(114, 97, '38', 9);
INSERT INTO `bien_the_san_pham` (`maBienThe`, `maSanPham`, `kichThuoc`, `soLuongTon`) VALUES(115, 98, '38', 9);

-- --------------------------------------------------------

--
-- Table structure for table `chi_tiet_don_hang`
--

CREATE TABLE `chi_tiet_don_hang` (
  `maChiTiet` int(11) NOT NULL,
  `maDonHang` int(11) DEFAULT NULL,
  `maBienThe` int(11) DEFAULT NULL,
  `soLuong` int(11) DEFAULT NULL,
  `gia` float DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `danh_muc`
--

CREATE TABLE `danh_muc` (
  `maDanhMuc` int(11) NOT NULL,
  `tenDanhMuc` varchar(50) DEFAULT NULL,
  `ngayTao` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `danh_muc`
--

INSERT INTO `danh_muc` (`maDanhMuc`, `tenDanhMuc`, `ngayTao`) VALUES(1, 'nike lorss', '0000-00-00 00:00:00');
INSERT INTO `danh_muc` (`maDanhMuc`, `tenDanhMuc`, `ngayTao`) VALUES(10, 'Nike Air Max', '0000-00-00 00:00:00');
INSERT INTO `danh_muc` (`maDanhMuc`, `tenDanhMuc`, `ngayTao`) VALUES(11, 'Nike Air Force 1', '0000-00-00 00:00:00');
INSERT INTO `danh_muc` (`maDanhMuc`, `tenDanhMuc`, `ngayTao`) VALUES(12, 'Nike Jordan', '0000-00-00 00:00:00');
INSERT INTO `danh_muc` (`maDanhMuc`, `tenDanhMuc`, `ngayTao`) VALUES(13, 'Nike Free Run', '0000-00-00 00:00:00');

-- --------------------------------------------------------

--
-- Table structure for table `danh_sach_yeu_thich`
--

CREATE TABLE `danh_sach_yeu_thich` (
  `maYeuThich` int(11) NOT NULL,
  `maNguoiDung` int(11) DEFAULT NULL,
  `maSanPham` int(11) DEFAULT NULL,
  `ngayTao` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `don_hang`
--

CREATE TABLE `don_hang` (
  `maDonHang` int(11) NOT NULL,
  `maNguoiDung` int(11) DEFAULT NULL,
  `diaChiGiaoHang` varchar(255) DEFAULT NULL,
  `tongTien` float DEFAULT NULL,
  `trangThai` varchar(20) DEFAULT NULL,
  `ngayTao` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `gio_hang`
--

CREATE TABLE `gio_hang` (
  `maGioHang` int(11) NOT NULL,
  `maNguoiDung` int(11) DEFAULT NULL,
  `maBienThe` int(11) DEFAULT NULL,
  `soLuong` int(11) DEFAULT NULL,
  `ngayTao` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `kich_thuoc_san_pham`
--

CREATE TABLE `kich_thuoc_san_pham` (
  `maKichThuoc` char(10) NOT NULL,
  `maBienThe` int(11) DEFAULT NULL,
  `kichThuoc` varchar(10) DEFAULT NULL,
  `soLuongTon` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `nguoi_dung`
--

CREATE TABLE `nguoi_dung` (
  `maNguoiDung` int(11) NOT NULL,
  `tenNguoiDung` varchar(50) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `matKhau` varchar(255) DEFAULT NULL,
  `sdt` char(15) DEFAULT NULL,
  `diaChiMacDinh` varchar(255) DEFAULT NULL,
  `vaiTro` varchar(20) DEFAULT NULL,
  `ngayTao` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `nguoi_dung`
--

INSERT INTO `nguoi_dung` (`maNguoiDung`, `tenNguoiDung`, `email`, `matKhau`, `sdt`, `diaChiMacDinh`, `vaiTro`, `ngayTao`) VALUES(1111122253, 'Hoàng Việt', 'viet@gmail.com', '1', '0348302144', '102 Chiến Thắng, F9, Phú Nhuận, Tp Hcm', 'user', '2025-01-17 14:37:08');
INSERT INTO `nguoi_dung` (`maNguoiDung`, `tenNguoiDung`, `email`, `matKhau`, `sdt`, `diaChiMacDinh`, `vaiTro`, `ngayTao`) VALUES(1111122254, 'admin', 'admin@gmail.com', '1', 'admin', 'null', 'admin', '2025-01-17 14:37:08');
INSERT INTO `nguoi_dung` (`maNguoiDung`, `tenNguoiDung`, `email`, `matKhau`, `sdt`, `diaChiMacDinh`, `vaiTro`, `ngayTao`) VALUES(1111122255, 'Lương Ngọc Tài', 'tai@gmail.com', '1', '0348302236', 'Hẻm 48 Bùi Thị Xuân, P9, Quận 3, Tp HCM', 'user', '2025-01-17 14:37:08');
INSERT INTO `nguoi_dung` (`maNguoiDung`, `tenNguoiDung`, `email`, `matKhau`, `sdt`, `diaChiMacDinh`, `vaiTro`, `ngayTao`) VALUES(1111122256, 'Nguyễn Văn Huy', 'huy@gmail.com', '1', '0348333236', '622 Hồ Văn Huê, P4, Phú Nhuận, Tp HCM', 'user', '2025-01-17 14:37:08');
INSERT INTO `nguoi_dung` (`maNguoiDung`, `tenNguoiDung`, `email`, `matKhau`, `sdt`, `diaChiMacDinh`, `vaiTro`, `ngayTao`) VALUES(1111122257, 'Trần Chí Chung', 'chung@gmail.com', '1', '0348333444', '105 ấp Kinh 10b, Xã Thạnh Đông B, Huyện Tân Hiệp, Kiên Giang', 'user', '2025-01-17 14:37:08');
INSERT INTO `nguoi_dung` (`maNguoiDung`, `tenNguoiDung`, `email`, `matKhau`, `sdt`, `diaChiMacDinh`, `vaiTro`, `ngayTao`) VALUES(1111122258, 'Lê Văn Công', 'cong@gmail.com', '1', '0348333444', '32 Thị Trấn Tân Hiệp, Tân Hiệp, Kiên Giang', 'user', '2025-01-17 14:37:08');
INSERT INTO `nguoi_dung` (`maNguoiDung`, `tenNguoiDung`, `email`, `matKhau`, `sdt`, `diaChiMacDinh`, `vaiTro`, `ngayTao`) VALUES(1111122259, 'Nguyễn Thạch Sanh', 'sanh@gmail.com', '1', '0347333444', '32 Gò Quao, Châu Thành, Hậu Giang', 'user', '2025-01-17 14:37:08');
INSERT INTO `nguoi_dung` (`maNguoiDung`, `tenNguoiDung`, `email`, `matKhau`, `sdt`, `diaChiMacDinh`, `vaiTro`, `ngayTao`) VALUES(1111122260, 'Trần Lí Thông', 'thong@gmail.com', '1', '0347333555', '32 Gò Quao, Châu Thành, Hậu Giang', 'user', '2025-01-17 14:37:08');
INSERT INTO `nguoi_dung` (`maNguoiDung`, `tenNguoiDung`, `email`, `matKhau`, `sdt`, `diaChiMacDinh`, `vaiTro`, `ngayTao`) VALUES(1111122262, 'việt', 'v@gmail.com', '1', '0348302426', '', 'user', '2025-01-18 09:21:13');

-- --------------------------------------------------------

--
-- Table structure for table `san_pham`
--

CREATE TABLE `san_pham` (
  `maSanPham` int(11) NOT NULL,
  `tenSanPham` varchar(100) DEFAULT NULL,
  `moTa` varchar(255) DEFAULT NULL,
  `gia` float DEFAULT NULL,
  `maDanhMuc` int(11) DEFAULT NULL,
  `anhSanPham` varchar(255) DEFAULT NULL,
  `ngayTao` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `san_pham`
--

INSERT INTO `san_pham` (`maSanPham`, `tenSanPham`, `moTa`, `gia`, `maDanhMuc`, `anhSanPham`, `ngayTao`) VALUES(84, 'Nike Air Max Dn', 'The Air Max Dn features our Dynamic Air unit system of dual-pressure tubes, creating a reactive sensation with every step', 4800000, 1, '/uploads/1.png', '0000-00-00 00:00:00');
INSERT INTO `san_pham` (`maSanPham`, `tenSanPham`, `moTa`, `gia`, `maDanhMuc`, `anhSanPham`, `ngayTao`) VALUES(85, 'Nike Air Max Dn Hammer', 'The Air Max Dn features our Dynamic Air unit system of dual-pressure tubes, creating a reactive sensation with every step', 5200000, 1, '/uploads/2.jpg', '0000-00-00 00:00:00');
INSERT INTO `san_pham` (`maSanPham`, `tenSanPham`, `moTa`, `gia`, `maDanhMuc`, `anhSanPham`, `ngayTao`) VALUES(86, 'Nike Air Max Dn x Isamaya Ffrench', 'This special edition of the Air Max Dn takes inspiration from women athletes and their influence on the fashion world. Ffrench used the unique design of the upper to play with textures and materials', 4200000, 1, '/uploads/3.png', '0000-00-00 00:00:00');
INSERT INTO `san_pham` (`maSanPham`, `tenSanPham`, `moTa`, `gia`, `maDanhMuc`, `anhSanPham`, `ngayTao`) VALUES(87, 'Nike Air Max Dn Vander', 'The Air Max Dn features our Dynamic Air unit system of dual-pressure tubes, creating a bouncy sensation with every step.', 5400000, 1, '/uploads/4.png', '0000-00-00 00:00:00');
INSERT INTO `san_pham` (`maSanPham`, `tenSanPham`, `moTa`, `gia`, `maDanhMuc`, `anhSanPham`, `ngayTao`) VALUES(88, 'Nike Blazer Mid \'77 Premium', 'This premium version of the Blazer Mid delivers a timeless design that’s easy to wear. Resort-inspired details mean these shoes are a vacation for your feet.', 7200000, 1, '/uploads/5.png', '0000-00-00 00:00:00');
INSERT INTO `san_pham` (`maSanPham`, `tenSanPham`, `moTa`, `gia`, `maDanhMuc`, `anhSanPham`, `ngayTao`) VALUES(89, 'Nike Blazer Mid \'77 Victor', 'he Nike Blazer Mid ’77 delivers a timeless design that’s easy to wear. ', 7500000, 1, '/uploads/6.png', '0000-00-00 00:00:00');
INSERT INTO `san_pham` (`maSanPham`, `tenSanPham`, `moTa`, `gia`, `maDanhMuc`, `anhSanPham`, `ngayTao`) VALUES(90, 'Nike Blazer Mid \'77 Mei', 'The Nike Blazer Mid \'77 channels the old-school look of Nike basketball with a vintage midsole finish', 6800000, 1, '/uploads/7.png', '0000-00-00 00:00:00');
INSERT INTO `san_pham` (`maSanPham`, `tenSanPham`, `moTa`, `gia`, `maDanhMuc`, `anhSanPham`, `ngayTao`) VALUES(91, 'Nike Blazer Mid \'77 WarWich', 'The vintage look and comfortable feel help this court classic transcend the hardwood into a legend of street style.', 5200000, 1, '/uploads/8.png', '0000-00-00 00:00:00');
INSERT INTO `san_pham` (`maSanPham`, `tenSanPham`, `moTa`, `gia`, `maDanhMuc`, `anhSanPham`, `ngayTao`) VALUES(92, 'Air Jordan 1 LOW', 'Inspired by the original that debuted in 1985, the Air Jordan 1 Low offers a clean, classic look that\'s familiar yet always fresh. ', 6900000, 1, '/uploads/10.png', '0000-00-00 00:00:00');
INSERT INTO `san_pham` (`maSanPham`, `tenSanPham`, `moTa`, `gia`, `maDanhMuc`, `anhSanPham`, `ngayTao`) VALUES(93, 'Air Jordan 1 Mid SE', 'Take your neutral game to the next level with this special edition AJ1.', 5900000, 1, '/uploads/11.png', '0000-00-00 00:00:00');
INSERT INTO `san_pham` (`maSanPham`, `tenSanPham`, `moTa`, `gia`, `maDanhMuc`, `anhSanPham`, `ngayTao`) VALUES(94, 'Air Jordan 1 LOW SE', 'This fresh take on the AJ1 brings new energy to neutrals. Smooth, premium leather and classic Nike Air cushioning give you the quality and comfort you\'ve come to expect from Jordan.', 4900000, 1, '/uploads/12.png', '0000-00-00 00:00:00');
INSERT INTO `san_pham` (`maSanPham`, `tenSanPham`, `moTa`, `gia`, `maDanhMuc`, `anhSanPham`, `ngayTao`) VALUES(95, 'Nike Dunk Low Retro', 'The Nike Dunk Low Retro returns with crisp overlays and original team colors. This basketball icon channels \'80s vibes with premium leather in the upper that looks good and breaks in even better.', 1900000, 1, '/uploads/13.png', '0000-00-00 00:00:00');
INSERT INTO `san_pham` (`maSanPham`, `tenSanPham`, `moTa`, `gia`, `maDanhMuc`, `anhSanPham`, `ngayTao`) VALUES(96, 'Nike Dunk Low ', 'The Nike Dunk Low  returns with crisp overlays and original team colors. This basketball icon channels \'80s vibes with premium leather in the upper that looks good and breaks in even better.', 3900000, 1, '/uploads/14.png', '0000-00-00 00:00:00');
INSERT INTO `san_pham` (`maSanPham`, `tenSanPham`, `moTa`, `gia`, `maDanhMuc`, `anhSanPham`, `ngayTao`) VALUES(97, 'Nike Dunk Low Corki', 'The Nike Dunk Low  Corki eturns with crisp overlays and original team colors. This basketball icon channels \'80s vibes with premium leather in the upper that looks good and breaks in even better.', 6700000, 1, '/uploads/15.png', '0000-00-00 00:00:00');
INSERT INTO `san_pham` (`maSanPham`, `tenSanPham`, `moTa`, `gia`, `maDanhMuc`, `anhSanPham`, `ngayTao`) VALUES(98, 'Nike Dunk HIGH Gangplank', 'The Nike Dunk Low  Gangplank returns with crisp overlays and original team colors. This basketball icon channels \'80s vibes with premium leather in the upper that looks good and breaks in even better.', 5700000, 1, '/uploads/16.png', '0000-00-00 00:00:00');

-- --------------------------------------------------------

--
-- Table structure for table `thanh_toan`
--

CREATE TABLE `thanh_toan` (
  `maThanhToan` int(11) NOT NULL,
  `maDonHang` int(11) DEFAULT NULL,
  `phuongThuc` varchar(20) DEFAULT NULL,
  `trangThai` varchar(20) DEFAULT NULL,
  `ngayThanhToan` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `anh_bien_the`
--
ALTER TABLE `anh_bien_the`
  ADD PRIMARY KEY (`maAnh`),
  ADD KEY `fk_ANH_BIEN_THE` (`maSanPham`);

--
-- Indexes for table `bien_the_san_pham`
--
ALTER TABLE `bien_the_san_pham`
  ADD PRIMARY KEY (`maBienThe`),
  ADD KEY `fk_BIEN_THE_SAN_PHAM` (`maSanPham`);

--
-- Indexes for table `chi_tiet_don_hang`
--
ALTER TABLE `chi_tiet_don_hang`
  ADD PRIMARY KEY (`maChiTiet`),
  ADD KEY `fk_CTDH_DON_HANG` (`maDonHang`),
  ADD KEY `fk_CTDH_BIEN_THE` (`maBienThe`);

--
-- Indexes for table `danh_muc`
--
ALTER TABLE `danh_muc`
  ADD PRIMARY KEY (`maDanhMuc`);

--
-- Indexes for table `danh_sach_yeu_thich`
--
ALTER TABLE `danh_sach_yeu_thich`
  ADD PRIMARY KEY (`maYeuThich`),
  ADD KEY `fk_DSYT_NGUOI_DUNG` (`maNguoiDung`),
  ADD KEY `fk_DSYT_SAN_PHAM` (`maSanPham`);

--
-- Indexes for table `don_hang`
--
ALTER TABLE `don_hang`
  ADD PRIMARY KEY (`maDonHang`),
  ADD KEY `fk_DON_HANG_NGUOI_DUNG` (`maNguoiDung`);

--
-- Indexes for table `gio_hang`
--
ALTER TABLE `gio_hang`
  ADD PRIMARY KEY (`maGioHang`),
  ADD KEY `fk_GIO_HANG_NGUOI_DUNG` (`maNguoiDung`),
  ADD KEY `fk_GIO_HANG_BIEN_THE` (`maBienThe`);

--
-- Indexes for table `kich_thuoc_san_pham`
--
ALTER TABLE `kich_thuoc_san_pham`
  ADD PRIMARY KEY (`maKichThuoc`),
  ADD KEY `fk_KICH_THUOC_SAN_PHAM` (`maBienThe`);

--
-- Indexes for table `nguoi_dung`
--
ALTER TABLE `nguoi_dung`
  ADD PRIMARY KEY (`maNguoiDung`);

--
-- Indexes for table `san_pham`
--
ALTER TABLE `san_pham`
  ADD PRIMARY KEY (`maSanPham`),
  ADD KEY `fk_SAN_PHAM_DANH_MUC` (`maDanhMuc`);

--
-- Indexes for table `thanh_toan`
--
ALTER TABLE `thanh_toan`
  ADD PRIMARY KEY (`maThanhToan`),
  ADD KEY `fk_THANH_TOAN_DON_HANG` (`maDonHang`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `anh_bien_the`
--
ALTER TABLE `anh_bien_the`
  MODIFY `maAnh` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=79;

--
-- AUTO_INCREMENT for table `bien_the_san_pham`
--
ALTER TABLE `bien_the_san_pham`
  MODIFY `maBienThe` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=116;

--
-- AUTO_INCREMENT for table `don_hang`
--
ALTER TABLE `don_hang`
  MODIFY `maDonHang` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `nguoi_dung`
--
ALTER TABLE `nguoi_dung`
  MODIFY `maNguoiDung` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=1111122263;

--
-- AUTO_INCREMENT for table `san_pham`
--
ALTER TABLE `san_pham`
  MODIFY `maSanPham` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=99;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `anh_bien_the`
--
ALTER TABLE `anh_bien_the`
  ADD CONSTRAINT `fk_ANH_BIEN_THE` FOREIGN KEY (`maSanPham`) REFERENCES `san_pham` (`maSanPham`);

--
-- Constraints for table `bien_the_san_pham`
--
ALTER TABLE `bien_the_san_pham`
  ADD CONSTRAINT `fk_BIEN_THE_SAN_PHAM` FOREIGN KEY (`maSanPham`) REFERENCES `san_pham` (`maSanPham`);

--
-- Constraints for table `chi_tiet_don_hang`
--
ALTER TABLE `chi_tiet_don_hang`
  ADD CONSTRAINT `fk_CTDH_BIEN_THE` FOREIGN KEY (`maBienThe`) REFERENCES `bien_the_san_pham` (`maBienThe`),
  ADD CONSTRAINT `fk_CTDH_DON_HANG` FOREIGN KEY (`maDonHang`) REFERENCES `don_hang` (`maDonHang`);

--
-- Constraints for table `danh_sach_yeu_thich`
--
ALTER TABLE `danh_sach_yeu_thich`
  ADD CONSTRAINT `fk_DSYT_NGUOI_DUNG` FOREIGN KEY (`maNguoiDung`) REFERENCES `nguoi_dung` (`maNguoiDung`),
  ADD CONSTRAINT `fk_DSYT_SAN_PHAM` FOREIGN KEY (`maSanPham`) REFERENCES `san_pham` (`maSanPham`);

--
-- Constraints for table `don_hang`
--
ALTER TABLE `don_hang`
  ADD CONSTRAINT `fk_DON_HANG_NGUOI_DUNG` FOREIGN KEY (`maNguoiDung`) REFERENCES `nguoi_dung` (`maNguoiDung`);

--
-- Constraints for table `gio_hang`
--
ALTER TABLE `gio_hang`
  ADD CONSTRAINT `fk_GIO_HANG_BIEN_THE` FOREIGN KEY (`maBienThe`) REFERENCES `bien_the_san_pham` (`maBienThe`),
  ADD CONSTRAINT `fk_GIO_HANG_NGUOI_DUNG` FOREIGN KEY (`maNguoiDung`) REFERENCES `nguoi_dung` (`maNguoiDung`);

--
-- Constraints for table `kich_thuoc_san_pham`
--
ALTER TABLE `kich_thuoc_san_pham`
  ADD CONSTRAINT `fk_KICH_THUOC_SAN_PHAM` FOREIGN KEY (`maBienThe`) REFERENCES `bien_the_san_pham` (`maBienThe`);

--
-- Constraints for table `san_pham`
--
ALTER TABLE `san_pham`
  ADD CONSTRAINT `fk_SAN_PHAM_DANH_MUC` FOREIGN KEY (`maDanhMuc`) REFERENCES `danh_muc` (`maDanhMuc`);

--
-- Constraints for table `thanh_toan`
--
ALTER TABLE `thanh_toan`
  ADD CONSTRAINT `fk_THANH_TOAN_DON_HANG` FOREIGN KEY (`maDonHang`) REFERENCES `don_hang` (`maDonHang`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
