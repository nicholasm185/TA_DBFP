-- phpMyAdmin SQL Dump
-- version 4.8.5
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jan 21, 2020 at 08:33 AM
-- Server version: 10.1.39-MariaDB
-- PHP Version: 7.1.29

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `foodhalldb`
--

-- --------------------------------------------------------

--
-- Table structure for table `bill`
--

CREATE TABLE `bill` (
  `billID` int(20) UNSIGNED NOT NULL,
  `transactionTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `cashierID` int(3) UNSIGNED DEFAULT NULL,
  `storeID` int(3) UNSIGNED DEFAULT NULL,
  `paymentTypeID` int(2) UNSIGNED DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `bill`
--

INSERT INTO `bill` (`billID`, `transactionTime`, `cashierID`, `storeID`, `paymentTypeID`) VALUES
(6, '2020-01-18 21:18:43', NULL, NULL, NULL),
(7, '2020-01-18 21:20:16', NULL, NULL, NULL),
(8, '2020-01-18 21:21:51', NULL, NULL, NULL),
(9, '2020-01-18 21:25:13', NULL, NULL, NULL),
(10, '2020-01-18 21:27:54', NULL, NULL, NULL),
(11, '2020-01-19 12:02:21', NULL, NULL, NULL),
(12, '2020-01-19 14:13:54', NULL, NULL, NULL),
(13, '2020-01-19 14:16:15', NULL, NULL, NULL),
(14, '2020-01-19 14:16:30', NULL, NULL, NULL),
(15, '2020-01-19 14:18:08', NULL, NULL, NULL),
(16, '2020-01-19 14:19:21', NULL, NULL, NULL),
(17, '2020-01-19 14:57:17', NULL, NULL, NULL),
(18, '2020-01-19 14:58:11', NULL, NULL, NULL),
(19, '2020-01-19 14:59:23', NULL, NULL, NULL),
(20, '2020-01-19 15:16:59', NULL, NULL, NULL),
(21, '2020-01-19 15:17:59', NULL, NULL, NULL),
(22, '2020-01-19 15:19:08', NULL, NULL, NULL),
(23, '2020-01-19 15:19:23', NULL, NULL, NULL),
(24, '2020-01-19 15:19:35', NULL, NULL, NULL),
(25, '2020-01-19 15:23:02', NULL, NULL, NULL),
(26, '2020-01-19 15:23:22', NULL, NULL, NULL),
(27, '2020-01-19 15:26:15', NULL, NULL, NULL),
(28, '2020-01-19 15:34:31', NULL, NULL, NULL),
(29, '2020-01-19 15:56:14', NULL, NULL, NULL),
(30, '2020-01-19 15:58:33', NULL, NULL, NULL),
(31, '2020-01-19 16:07:55', NULL, NULL, NULL),
(32, '2020-01-21 11:09:51', NULL, NULL, NULL),
(33, '2020-01-21 11:22:05', NULL, NULL, NULL),
(34, '2020-01-21 11:22:36', NULL, NULL, NULL),
(35, '2020-01-21 11:29:14', NULL, NULL, NULL),
(36, '2020-01-21 11:30:19', NULL, NULL, NULL),
(37, '2020-01-21 12:16:08', NULL, NULL, NULL),
(38, '2020-01-21 12:20:03', NULL, NULL, NULL),
(39, '2020-01-21 12:21:45', NULL, NULL, NULL),
(40, '2020-01-21 12:24:37', NULL, NULL, NULL),
(41, '2020-01-21 12:25:39', NULL, NULL, NULL),
(42, '2020-01-21 12:27:31', NULL, NULL, NULL),
(43, '2020-01-21 12:28:58', NULL, NULL, NULL),
(44, '2020-01-21 12:29:34', 6, 3, 3),
(45, '2020-01-21 13:46:33', NULL, NULL, NULL),
(46, '2020-01-21 13:47:21', NULL, NULL, NULL),
(47, '2020-01-21 13:49:54', 6, 1, 2),
(48, '2020-01-21 13:58:50', NULL, NULL, NULL),
(49, '2020-01-21 13:59:03', 6, 2, 3),
(50, '2020-01-21 14:00:48', 6, 2, 3),
(51, '2020-01-21 14:01:10', 6, 2, 2),
(52, '2020-01-21 14:25:22', 6, 1, 2),
(53, '2020-01-21 14:27:18', 6, 1, 2),
(54, '2020-01-21 14:27:47', NULL, NULL, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `cashier`
--

CREATE TABLE `cashier` (
  `cashierID` int(3) UNSIGNED NOT NULL,
  `cashierName` varchar(20) NOT NULL,
  `password` varchar(40) DEFAULT NULL,
  `admin` tinyint(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `cashier`
--

INSERT INTO `cashier` (`cashierID`, `cashierName`, `password`, `admin`) VALUES
(6, 'aa', 'a7431f0258c9b308a73d337c20ca9ee6', 1);

-- --------------------------------------------------------

--
-- Table structure for table `itemtransaction`
--

CREATE TABLE `itemtransaction` (
  `itemID` int(20) NOT NULL,
  `billID` int(20) UNSIGNED NOT NULL,
  `productID` int(10) UNSIGNED NOT NULL,
  `qty` int(3) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `itemtransaction`
--

INSERT INTO `itemtransaction` (`itemID`, `billID`, `productID`, `qty`) VALUES
(83, 44, 1, 3),
(84, 44, 3, 1),
(85, 45, 3, 1),
(86, 47, 1, 1),
(87, 48, 1, 1),
(88, 48, 2, 1),
(89, 49, 2, 1),
(91, 50, 4, 5),
(92, 51, 2, 1),
(93, 52, 1, 1),
(94, 52, 3, 3),
(95, 53, 4, 3),
(96, 54, 2, 1);

-- --------------------------------------------------------

--
-- Table structure for table `paymenttype`
--

CREATE TABLE `paymenttype` (
  `paymentTypeID` int(2) UNSIGNED NOT NULL,
  `paymentName` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `paymenttype`
--

INSERT INTO `paymenttype` (`paymentTypeID`, `paymentName`) VALUES
(1, 'Cash'),
(2, 'Debit'),
(3, 'Credit'),
(4, 'Go-pay'),
(5, 'Ovo');

-- --------------------------------------------------------

--
-- Table structure for table `products`
--

CREATE TABLE `products` (
  `productID` int(10) UNSIGNED NOT NULL,
  `productName` varchar(30) NOT NULL,
  `productPrice` int(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `products`
--

INSERT INTO `products` (`productID`, `productName`, `productPrice`) VALUES
(1, 'Paket 1', 26000),
(2, 'Paket 2', 30000),
(3, 'Paket 3', 34000),
(4, 'Fried Fishcake 3x', 15000),
(5, 'Fried Fishcake 1x', 5000),
(6, 'Shrimp Nugget', 15000),
(7, 'GanjaMurah', 25000),
(8, 'abc', 50000),
(9, 'aaa', 123),
(10, 'a', 500000);

-- --------------------------------------------------------

--
-- Table structure for table `store`
--

CREATE TABLE `store` (
  `StoreID` int(3) UNSIGNED NOT NULL,
  `StoreName` varchar(255) NOT NULL,
  `StoreLocation` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `store`
--

INSERT INTO `store` (`StoreID`, `StoreName`, `StoreLocation`) VALUES
(1, 'Food Hall Senayan City', 'Jl. Asia Afrika No.19, RT.1/RW.3, Gelora, Kecamatan Tanah Abang, Kota Jakarta Pusat, Daerah Khusus Ibukota Jakarta 10270'),
(2, 'Food Hall Grand Indonesia', 'Jl. M.H. Thamrin No.1, Kb. Melati, Tanahabang, Kota Jakarta Pusat, Daerah Khusus Ibukota Jakarta 10250'),
(3, 'Food Hall Plaza Indonesia', 'Jl. M.H. Thamrin No.28-30, RT.9/RW.5, Gondangdia, Kec. Menteng, Kota Jakarta Pusat, Daerah Khusus Ibukota Jakarta 10350'),
(4, 'Food Hall FX', 'Jalan Jenderal Sudirman, Gelora, RT.1/RW.3, Gelora, Kota Jakarta Pusat, Daerah Khusus Ibukota Jakarta 10270'),
(5, 'Food Hall PIM 2', 'Jalan Metro Pondok Indah, RT.1/RW.16, Pd. Pinang, Kec. Kby. Lama, Kota Jakarta Selatan, Daerah Khusus Ibukota Jakarta 12310');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `bill`
--
ALTER TABLE `bill`
  ADD PRIMARY KEY (`billID`),
  ADD KEY `bill_ibfk_1` (`cashierID`),
  ADD KEY `bill_ibfk_2` (`storeID`),
  ADD KEY `paymentTypeID` (`paymentTypeID`);

--
-- Indexes for table `cashier`
--
ALTER TABLE `cashier`
  ADD PRIMARY KEY (`cashierID`);

--
-- Indexes for table `itemtransaction`
--
ALTER TABLE `itemtransaction`
  ADD PRIMARY KEY (`itemID`),
  ADD KEY `billID` (`billID`),
  ADD KEY `productID` (`productID`);

--
-- Indexes for table `paymenttype`
--
ALTER TABLE `paymenttype`
  ADD PRIMARY KEY (`paymentTypeID`);

--
-- Indexes for table `products`
--
ALTER TABLE `products`
  ADD PRIMARY KEY (`productID`);

--
-- Indexes for table `store`
--
ALTER TABLE `store`
  ADD PRIMARY KEY (`StoreID`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `bill`
--
ALTER TABLE `bill`
  MODIFY `billID` int(20) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=55;

--
-- AUTO_INCREMENT for table `cashier`
--
ALTER TABLE `cashier`
  MODIFY `cashierID` int(3) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `itemtransaction`
--
ALTER TABLE `itemtransaction`
  MODIFY `itemID` int(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=97;

--
-- AUTO_INCREMENT for table `paymenttype`
--
ALTER TABLE `paymenttype`
  MODIFY `paymentTypeID` int(2) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `products`
--
ALTER TABLE `products`
  MODIFY `productID` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT for table `store`
--
ALTER TABLE `store`
  MODIFY `StoreID` int(3) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `bill`
--
ALTER TABLE `bill`
  ADD CONSTRAINT `bill_ibfk_1` FOREIGN KEY (`cashierID`) REFERENCES `cashier` (`cashierID`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `bill_ibfk_2` FOREIGN KEY (`storeID`) REFERENCES `store` (`StoreID`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `bill_ibfk_3` FOREIGN KEY (`paymentTypeID`) REFERENCES `paymenttype` (`paymentTypeID`) ON DELETE SET NULL ON UPDATE SET NULL;

--
-- Constraints for table `itemtransaction`
--
ALTER TABLE `itemtransaction`
  ADD CONSTRAINT `itemtransaction_ibfk_1` FOREIGN KEY (`billID`) REFERENCES `bill` (`billID`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `itemtransaction_ibfk_2` FOREIGN KEY (`productID`) REFERENCES `products` (`productID`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
