-- Adminer 4.7.5 MySQL dump

SET NAMES utf8;
SET time_zone = '+00:00';
SET foreign_key_checks = 0;
SET sql_mode = 'NO_AUTO_VALUE_ON_ZERO';

USE `meteorolog`;

DROP TABLE IF EXISTS `meteo`;
CREATE TABLE `meteo` (
  `rowid` int(11) NOT NULL AUTO_INCREMENT,
  `fk_station` int(11) NOT NULL,
  `fk_meteo_type` int(11) NOT NULL,
  `fk_tranche_journee` int(11) NOT NULL,
  `date` date NOT NULL,
  `temperature` int(11) NOT NULL,
  `humidite` int(11) NOT NULL,
  PRIMARY KEY (`rowid`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

INSERT INTO `meteo` (`fk_station`, `fk_meteo_type`, `fk_tranche_journee`, `date`, `temperature`, `humidite`) VALUES
(1,	0,	0,	'2020-03-25',	14,	59),
(2,	1,	2,	'2020-03-25',	8,	98),
(3,	2,	3,	'2020-03-25',	18,	45),
(4,	0,	2,	'2020-03-25',	21,	48),
(5,	5,	1,	'2020-03-25',	4,	78),
(6,	9,	0,	'2020-03-25',	12,	63),
(7,	1,	2,	'2020-03-25',	11,	85),
(8,	6,	2,	'2020-03-25',	2,	41),
(10,	4,	1,	'2020-03-25',	23,	54),
(11,	7,	2,	'2020-03-25',	24,	31),
(12,	10,	3,	'2020-03-25',	7,	41),
(13,	11,	2,	'2020-03-25',	9,	13),
(14,	2,	0,	'2020-03-25',	5,	10),
(9,	1,	1,	'2020-03-24',	4,	100);

DROP TABLE IF EXISTS `meteo_type`;
CREATE TABLE `meteo_type` (
  `rowid` int(11) NOT NULL AUTO_INCREMENT,
  `description` varchar(100) NOT NULL,
  `type` int(11) NOT NULL COMMENT '0: calme / 1: bof / 2: tempêtueux',
  PRIMARY KEY (`rowid`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

INSERT INTO `meteo_type` (`rowid`, `description`, `type`) VALUES
(1,	'Dégagé',	0),
(2,	'Partiellement nuageux',	0),
(3,	'Couvert',	0),
(4,	'Brumeux',	0),
(5,	'Bruine',	1),
(6,	'Averses de pluie',	1),
(7,	'Pluie',	1),
(8,	'Pluie forte',	2),
(9,	'Orage',	2),
(10,	'Orage violent',	2),
(11,	'Tempête orageuse',	2);






DROP TABLE IF EXISTS `pays`;
CREATE TABLE `pays` (
  `rowid` int(11) NOT NULL AUTO_INCREMENT,
  `nom` varchar(30) NOT NULL,
  PRIMARY KEY (`rowid`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

INSERT INTO `pays` (`rowid`, `nom`) VALUES
(1,	'France'),
(2,	'Allemagne'),
(3,	'Danemark'),
(4,	'Italie'),
(5,	'Espagne'),
(6,	'Suisse');

DROP TABLE IF EXISTS `station`;
CREATE TABLE `station` (
  `rowid` int(11) NOT NULL AUTO_INCREMENT,
  `nom` varchar(30) NOT NULL,
  `fk_ville` int(11) NOT NULL,
  PRIMARY KEY (`rowid`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

INSERT INTO `station` (`rowid`, `nom`, `fk_ville`) VALUES
(1,	'FR-VAL01',	1),
(2,	'DE-MUN04',	2),
(3,	'DN-COP02',	3),
(4,	'IT-TUR01',	4),
(5,	'IT-MIL12',	5),
(6,	'IT-ROM03',	6),
(7,	'ES-BAR02',	7),
(8,	'SW-GEN07',	8),
(9,	'SW-LAU02',	9),
(10,	'FR-LYO02',	10),
(11,	'FR-BRE05',	11),
(12,	'FR-PAR01',	12),
(13,	'FR-DUN03',	13),
(14,	'FR-NIM03',	14);


DROP TABLE IF EXISTS `tranche_journee`;
CREATE TABLE `tranche_journee` (
  `rowid` int(11) NOT NULL AUTO_INCREMENT,
  `description` varchar(10) NOT NULL,
  PRIMARY KEY (`rowid`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

INSERT INTO `tranche_journee` (`rowid`, `description`) VALUES
(1,	'nuit'),
(2,	'matin'),
(3,	'après-midi'),
(4,	'soirée');

DROP TABLE IF EXISTS `ville`;
CREATE TABLE `ville` (
  `rowid` int(11) NOT NULL AUTO_INCREMENT,
  `nom` varchar(30) NOT NULL,
  `fk_pays` int(11) NOT NULL,
  PRIMARY KEY (`rowid`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

INSERT INTO `ville` (`rowid`, `nom`, `fk_pays`) VALUES
(1,	'Valence',	1),
(2,	'Munich',	2),
(3,	'Copenhague',	3),
(4,	'Turin',	4),
(5,	'Milan',	4),
(6,	'Rome',	4),
(7,	'Barcelone',	5),
(8,	'Genève',	6),
(9,	'Lausanne',	6),
(10,	'Lyon',	1),
(11,	'Brest',	1),
(12,	'Paris',	1),
(13,	'Dunkerque',	1),
(14,	'Nimes',	1);

-- 2020-03-26 19:53:03
