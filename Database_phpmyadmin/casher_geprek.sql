-- phpMyAdmin SQL Dump
-- version 4.8.3
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Waktu pembuatan: 07 Jul 2020 pada 20.27
-- Versi server: 10.1.37-MariaDB
-- Versi PHP: 7.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `casher_geprek`
--

-- --------------------------------------------------------

--
-- Struktur dari tabel `daftar_menu`
--

CREATE TABLE `daftar_menu` (
  `id_menu` varchar(15) NOT NULL,
  `nama` varchar(100) NOT NULL,
  `kategori` enum('Makanan','Minuman','Paket') NOT NULL,
  `harga` int(11) NOT NULL,
  `status` enum('Tersedia','Tidak tersedia') NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `daftar_menu`
--

INSERT INTO `daftar_menu` (`id_menu`, `nama`, `kategori`, `harga`, `status`) VALUES
('MK_1', 'Ayam Goreng Geprek ', 'Makanan', 10000, 'Tersedia'),
('MK_2', 'Ayam Bakar Geprek', 'Makanan', 11000, 'Tersedia'),
('MK_3', 'Telur Dadar', 'Makanan', 4500, 'Tersedia'),
('MK_4', 'Jamur Crispy', 'Makanan', 6000, 'Tersedia'),
('MK_5', 'Tahu Geprek\r\n\r\nJamur Crispy\r\n', 'Makanan', 2000, 'Tersedia'),
('MK_6', 'Tempe Geprek', 'Makanan', 2000, 'Tersedia'),
('MN_1', 'Teh (Dingin / Anget)\r\n', 'Minuman', 3000, 'Tersedia'),
('MN_2', 'Susu (Dingin / Anget)\r\n', 'Minuman', 3000, 'Tersedia'),
('MN_3', 'Jeruk (Dingin / Anget)', 'Minuman', 3000, 'Tersedia'),
('PK_1', 'Paket Ayam Goreng Geprek + Teh', 'Paket', 17000, 'Tersedia'),
('PK_2', 'Paket Ayam Goreng Geprek  + Teh', 'Paket', 17000, 'Tersedia'),
('PK_3', 'Paket Ayam Bakar Geprek + Susu', 'Paket', 18000, 'Tersedia'),
('PK_4', 'Paket Ayam Bakar Geprek  + Susu', 'Paket', 18000, 'Tersedia');

-- --------------------------------------------------------

--
-- Struktur dari tabel `data_pesanan`
--

CREATE TABLE `data_pesanan` (
  `id` int(11) NOT NULL,
  `id_pesanan` int(11) NOT NULL,
  `no_meja` int(11) NOT NULL,
  `total_harga` int(11) NOT NULL,
  `tanggal_order` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `data_pesanan`
--

INSERT INTO `data_pesanan` (`id`, `id_pesanan`, `no_meja`, `total_harga`, `tanggal_order`) VALUES
(1, 1, 1, 26000, '2020-06-10'),
(2, 2, 2, 68000, '2020-06-10'),
(3, 3, 3, 21000, '2020-07-01');

-- --------------------------------------------------------

--
-- Struktur dari tabel `pesanan`
--

CREATE TABLE `pesanan` (
  `id_pesanan` int(11) NOT NULL,
  `id_menu` varchar(15) NOT NULL,
  `jumlah` int(11) NOT NULL,
  `jumlah_harga` int(11) NOT NULL,
  `catatan` text,
  `no_meja` int(11) NOT NULL,
  `tanggal_order` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `pesanan`
--

INSERT INTO `pesanan` (`id_pesanan`, `id_menu`, `jumlah`, `jumlah_harga`, `catatan`, `no_meja`, `tanggal_order`) VALUES
(1, 'MK_1', 2, 20000, 'Pedas', 1, '2020-06-10'),
(1, 'MN_3', 2, 6000, 'Dingin', 1, '2020-06-10'),
(2, 'PK_1', 4, 68000, NULL, 2, '2020-06-10'),
(3, 'PK_3', 1, 18000, NULL, 3, '2020-07-01'),
(3, 'MN_3', 1, 3000, 'Dingin', 3, '2020-07-01');

--
-- Trigger `pesanan`
--
DELIMITER $$
CREATE TRIGGER `trigger_add_pesanan` AFTER INSERT ON `pesanan` FOR EACH ROW BEGIN

    declare var_id_pesanan int;
    declare var_no_meja int;
    declare var_jumlah_harga int;
    declare var_tanggal_order date;

    declare var_id_data int;
    declare var_id_pesanan_data int;

    SELECT id_pesanan, no_meja, jumlah_harga, tanggal_order 
        INTO var_id_pesanan, var_no_meja, var_jumlah_harga, var_tanggal_order
        FROM pesanan WHERE LAST_VALUE(id_pesanan) ORDER BY id_pesanan DESC LIMIT 1 ;

    SELECT id_pesanan 
    	INTO var_id_pesanan_data
        FROM data_pesanan WHERE id_pesanan = var_id_pesanan;

    IF	var_id_pesanan_data = var_id_pesanan THEN
        UPDATE data_pesanan SET total_harga = (total_harga + var_jumlah_harga) WHERE id_pesanan = var_id_pesanan;
    ELSE
        INSERT INTO data_pesanan(`id_pesanan`, `no_meja`, `total_harga`, `tanggal_order`) 
            VALUES(var_id_pesanan, var_no_meja, var_jumlah_harga, var_tanggal_order);
    END IF;

END
$$
DELIMITER ;

--
-- Indexes for dumped tables
--

--
-- Indeks untuk tabel `daftar_menu`
--
ALTER TABLE `daftar_menu`
  ADD PRIMARY KEY (`id_menu`);

--
-- Indeks untuk tabel `data_pesanan`
--
ALTER TABLE `data_pesanan`
  ADD PRIMARY KEY (`id`),
  ADD KEY `id_pesanan` (`id_pesanan`);

--
-- Indeks untuk tabel `pesanan`
--
ALTER TABLE `pesanan`
  ADD KEY `id_pesanan` (`id_pesanan`);

--
-- AUTO_INCREMENT untuk tabel yang dibuang
--

--
-- AUTO_INCREMENT untuk tabel `data_pesanan`
--
ALTER TABLE `data_pesanan`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- Ketidakleluasaan untuk tabel pelimpahan (Dumped Tables)
--

--
-- Ketidakleluasaan untuk tabel `data_pesanan`
--
ALTER TABLE `data_pesanan`
  ADD CONSTRAINT `data_pesanan_ibfk_1` FOREIGN KEY (`id_pesanan`) REFERENCES `pesanan` (`id_pesanan`),
  ADD CONSTRAINT `data_pesanan_ibfk_2` FOREIGN KEY (`id_pesanan`) REFERENCES `pesanan` (`id_pesanan`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
