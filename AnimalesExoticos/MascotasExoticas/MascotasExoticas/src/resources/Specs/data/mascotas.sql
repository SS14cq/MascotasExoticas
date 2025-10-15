-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 15-10-2025 a las 14:21:35
-- Versión del servidor: 10.4.32-MariaDB
-- Versión de PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `animales`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `mascotas`
--

CREATE TABLE `mascotas` (
  `nombre` varchar(50) NOT NULL,
  `apodo` varchar(50) NOT NULL,
  `clasificacion` varchar(100) NOT NULL,
  `familia` varchar(50) NOT NULL,
  `genero` varchar(50) NOT NULL,
  `especie` varchar(100) NOT NULL,
  `alimento` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `mascotas`
--

INSERT INTO `mascotas` (`nombre`, `apodo`, `clasificacion`, `familia`, `genero`, `especie`, `alimento`) VALUES
('Loro Yaco', 'Pepe', 'Ave', 'Psittacidae', 'Psittacus', 'Psittacus erithacus', 'Frutas'),
('Tarántula Chilena Rosa', 'Rosita', 'Arachnida', 'Theraphosidae', 'Grammostola', '', 'Carnes'),
('6', '', '', '', '', '', ''),
('Ajolote', 'Morice', 'Amphibia', 'Ambystomatidae', 'Ambystoma', 'Ambystoma mexicanum', 'Carnes'),
('Pez Payaso', 'Nemo', 'Actinopterygii', 'Pomacentridae', 'Amphiprion', 'Amphiprion ocellaris', 'Omnívoros'),
('Hurón', 'Furby', 'Mammalia', 'Mustelidae', 'Mustela', 'Mustela putorius furo', 'Carnes'),
('Dragón Barbudo', 'Spicke', 'Reptil', 'Agamidae', 'cesa', 'Pogona vitticeps', 'Omnívoros');
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
