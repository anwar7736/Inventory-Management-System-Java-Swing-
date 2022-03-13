-- phpMyAdmin SQL Dump
-- version 5.0.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Feb 16, 2022 at 06:42 PM
-- Server version: 10.4.22-MariaDB
-- PHP Version: 7.4.1

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `inventory_management_system`
--

-- --------------------------------------------------------

--
-- Table structure for table `categories`
--

CREATE TABLE `categories` (
  `category_code` int(11) NOT NULL,
  `category_name` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `categories`
--

INSERT INTO `categories` (`category_code`, `category_name`) VALUES
(123482, 'Baby Food'),
(123483, 'Cosmetics'),
(123485, 'Motor Bike'),
(123486, 'Electronics'),
(123487, 'Accessories');

-- --------------------------------------------------------

--
-- Table structure for table `current_stock`
--

CREATE TABLE `current_stock` (
  `stock_id` int(11) NOT NULL,
  `product_code` varchar(255) NOT NULL,
  `stock_qty` int(11) NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `current_stock`
--

INSERT INTO `current_stock` (`stock_id`, `product_code`, `stock_qty`) VALUES
(44, '35494610', 10),
(45, '34165450', 5),
(46, '29711578', 11),
(47, '57507453', 2),
(48, '58777152', 30),
(49, '56554030', 1);

-- --------------------------------------------------------

--
-- Table structure for table `products`
--

CREATE TABLE `products` (
  `product_code` int(8) NOT NULL,
  `category_code` varchar(255) NOT NULL,
  `product_name` varchar(255) NOT NULL,
  `unit_price` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `products`
--

INSERT INTO `products` (`product_code`, `category_code`, `product_name`, `unit_price`) VALUES
(29711578, '123482', 'Lactogen 3 180gm', '236'),
(34165450, '123482', 'Lactogen 2 180gm', '245'),
(35494610, '123482', 'Lactogen 1 180gm', '245'),
(56554030, '123483', 'Meril Lipgel', '32'),
(57507453, '123485', 'Yamaha R15', '500000'),
(58777152, '123485', 'Bajaj', '150000');

-- --------------------------------------------------------

--
-- Table structure for table `transactions`
--

CREATE TABLE `transactions` (
  `trx_id` int(11) NOT NULL,
  `invoice_no` varchar(255) NOT NULL,
  `product_code` varchar(255) NOT NULL,
  `product_name` varchar(255) NOT NULL,
  `category` varchar(255) NOT NULL,
  `unit_price` double NOT NULL,
  `sales_qty` int(255) NOT NULL,
  `total_amount` double DEFAULT NULL,
  `invoice_date` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `transactions`
--

INSERT INTO `transactions` (`trx_id`, `invoice_no`, `product_code`, `product_name`, `category`, `unit_price`, `sales_qty`, `total_amount`, `invoice_date`) VALUES
(103, 'INV58933810', '21764403', ' Lactogen 1 180gm', 'Baby Food', 245, 2, 490, '15-02-2022'),
(104, 'INV58933810', '45433619', ' Lactogen 2 180gm', 'Baby Food', 245, 3, 735, '15-02-2022'),
(105, 'INV37486430', '21764403', ' Lactogen 1 180gm', 'Baby Food', 245, 5, 1225, '15-02-2022'),
(106, 'INV37486430', '45433619', ' Lactogen 2 180gm', 'Baby Food', 245, 2, 490, '15-02-2022'),
(107, 'INV37486430', '16534483', ' Lactogen 3 180gm', 'Baby Food', 236, 1, 236, '15-02-2022'),
(108, 'INV50837944', '21764403', ' Lactogen 1 180gm', 'Baby Food', 245, 1, 245, '15-02-2022'),
(109, 'INV50837944', '45433619', ' Lactogen 2 180gm', 'Baby Food', 245, 1, 245, '15-02-2022'),
(110, 'INV50837944', '16534483', ' Lactogen 3 180gm', 'Baby Food', 236, 1, 236, '15-02-2022');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `phone` varchar(255) NOT NULL,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `name`, `email`, `phone`, `username`, `password`) VALUES
(1, 'Md Anwar Hossain', 'anwarhossain7736@gmail.com', '01794030592', 'anwar123', '123');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `categories`
--
ALTER TABLE `categories`
  ADD PRIMARY KEY (`category_code`);

--
-- Indexes for table `current_stock`
--
ALTER TABLE `current_stock`
  ADD PRIMARY KEY (`stock_id`);

--
-- Indexes for table `products`
--
ALTER TABLE `products`
  ADD PRIMARY KEY (`product_code`);

--
-- Indexes for table `transactions`
--
ALTER TABLE `transactions`
  ADD PRIMARY KEY (`trx_id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `categories`
--
ALTER TABLE `categories`
  MODIFY `category_code` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=123488;

--
-- AUTO_INCREMENT for table `current_stock`
--
ALTER TABLE `current_stock`
  MODIFY `stock_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=50;

--
-- AUTO_INCREMENT for table `products`
--
ALTER TABLE `products`
  MODIFY `product_code` int(8) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=97629650;

--
-- AUTO_INCREMENT for table `transactions`
--
ALTER TABLE `transactions`
  MODIFY `trx_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=111;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
