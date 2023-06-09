-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jun 09, 2023 at 03:27 PM
-- Server version: 10.4.28-MariaDB
-- PHP Version: 8.2.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `meatu`
--

-- --------------------------------------------------------

--
-- Table structure for table `history_user`
--

CREATE TABLE `history_user` (
  `id` int(11) NOT NULL,
  `user_id` int(11) DEFAULT NULL,
  `link_gambar` varchar(255) DEFAULT NULL,
  `tanggal` date DEFAULT NULL,
  `hasil_prediksi` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id` int(20) NOT NULL,
  `nama` varchar(255) NOT NULL,
  `alamat` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `nama`, `alamat`, `email`, `password`) VALUES
(1, 'Thoriq', 'Kendal Jawa Tengah', 'thoriqds.a@gmail.com', 'thoriq1234'),
(2, 'thoriq1234', '', 'thord@gmail.com', 'thor1234'),
(3, 'thoriq1234', '', 'thord@gmail.com', 'thor1234'),
(4, 'thoriq1234', '', 'thord@gmail.com', 'thor1234'),
(5, 'thoriq1234a', '', 'thord@gmail.com', 'thor1234'),
(6, 'thoriq1234a', '', 'thord@gmail.com', 'thor1234aaaa'),
(7, 'thoriq1234a', '', 'thord@gmail.com', 'thor1234aaaa'),
(8, 'thoriq1234a', '', 'thord@gmail.com', 'thor1234aaaa'),
(9, 'amdo', 'Jekardah', 'amdo@gmail.com', '$2b$12$OErwxif0r1aQiO4wkAdxY.N2t71DViNHuhsKjllg9ypTMDvUydugS');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `history_user`
--
ALTER TABLE `history_user`
  ADD PRIMARY KEY (`id`),
  ADD KEY `user_id` (`user_id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `history_user`
--
ALTER TABLE `history_user`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` int(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `history_user`
--
ALTER TABLE `history_user`
  ADD CONSTRAINT `history_user_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
