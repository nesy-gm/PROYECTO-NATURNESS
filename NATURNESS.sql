-- phpMyAdmin SQL Dump
-- version 4.5.4.1deb2ubuntu2.1
-- http://www.phpmyadmin.net
--
-- Servidor: localhost
-- Tiempo de generación: 19-05-2019 a las 21:37:40
-- Versión del servidor: 10.0.36-MariaDB-0ubuntu0.16.04.1
-- Versión de PHP: 7.0.32-0ubuntu0.16.04.1

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `NATURNESS`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `clientes`
--

CREATE TABLE `clientes` (
  `nif` varchar(11) NOT NULL,
  `nombre` varchar(11) DEFAULT NULL,
  `apellidos` varchar(11) DEFAULT NULL,
  `cp` varchar(11) DEFAULT NULL,
  `teléfono` int(11) DEFAULT NULL,
  `fecha_nacimiento` date DEFAULT NULL,
  `fecha_registro` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `clientes`
--

INSERT INTO `clientes` (`nif`, `nombre`, `apellidos`, `cp`, `teléfono`, `fecha_nacimiento`, `fecha_registro`) VALUES
('00000000I', 'Invitado', 'Invitado', '00000', 0, NULL, NULL),
('12345678F', 'Ana', 'Fernandez', '07003', 123345667, '1990-07-14', '2019-03-07 00:00:00'),
('12345678W', 'Pepe', 'Jimenez', '07001', 123456789, '1978-02-25', '2019-01-15 00:00:00'),
('45678123X', 'Antonio', 'Lopez', '07012', 987654321, '1986-04-12', '2019-02-20 00:00:00'),
('45778123B', 'Isabel', 'García', '28080', 234156789, '1977-11-27', '2019-04-30 00:00:00');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `facturas`
--

CREATE TABLE `facturas` (
  `num_fact` int(11) NOT NULL,
  `nif_cliente` int(11) NOT NULL,
  `ean_tratamiento` int(11) NOT NULL DEFAULT '0',
  `cantidad` int(11) NOT NULL DEFAULT '1',
  `precio` float DEFAULT NULL,
  `fecha_compra` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `facturas`
--

INSERT INTO `facturas` (`num_fact`, `nif_cliente`, `ean_tratamiento`, `cantidad`, `precio`, `fecha_compra`) VALUES
(1, 0, 1101, 1, 49, '2019-01-09 23:00:00'),
(2, 0, 1402, 2, 9.9, '2019-03-01 23:00:00'),
(3, 45778123, 1302, 1, 19, '2019-04-29 22:00:00'),
(4, 12345678, 1501, 3, 17.85, '2019-01-14 23:00:00');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tratamiento`
--

CREATE TABLE `tratamiento` (
  `ean` int(14) NOT NULL,
  `nombre` varchar(11) DEFAULT NULL,
  `descripción` varchar(100) DEFAULT NULL,
  `zona_aplicación` varchar(11) DEFAULT NULL,
  `stock` int(11) DEFAULT NULL,
  `precio_ud` float DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `tratamiento`
--

INSERT INTO `tratamiento` (`ean`, `nombre`, `descripción`, `zona_aplicación`, `stock`, `precio_ud`) VALUES
(1101, 'Hya', 'Hidratante antiedad con ácido hyalurónico', 'rostro', 10, 49),
(1102, 'Hya Eyes', 'Contorno de ojos hidratante antiedad con ácido hyalurónico', 'ojos', 10, 39),
(1201, 'Rose', 'Hidratante  con rosa mosqueta', 'rostro', 10, 39),
(1202, 'Rose Eyes', 'Contorno de ojos hidratante con rosa mosqueta', 'ojos', 10, 29),
(1301, 'Coco balm', 'Leche corporal de coco', 'cuerpo', 10, 19),
(1302, 'Aloe balm', 'Leche corporal de aloe vera', 'cuerpo', 10, 19),
(1303, 'Body oil', 'Aceite corporal de almendras y manteca de karité', 'cuerpo', 10, 23.95),
(1401, 'Handy', 'Emulsión de manos de coco', 'manos', 10, 4.95),
(1402, 'Handy', 'Crema hidratante de manos de aloe vera', 'manos', 10, 4.95),
(1501, 'Refresh gel', 'Gel hidratante y regenerante con menta y aloe', 'piernas ', 10, 5.95);

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `clientes`
--
ALTER TABLE `clientes`
  ADD PRIMARY KEY (`nif`);

--
-- Indices de la tabla `facturas`
--
ALTER TABLE `facturas`
  ADD PRIMARY KEY (`num_fact`);

--
-- Indices de la tabla `tratamiento`
--
ALTER TABLE `tratamiento`
  ADD PRIMARY KEY (`ean`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
