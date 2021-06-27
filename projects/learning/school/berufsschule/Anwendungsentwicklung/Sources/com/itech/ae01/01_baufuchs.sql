# --------------------------------------------------------
# Host:                         127.0.0.1
# Server version:               5.6.21
# Server OS:                    Win32
# HeidiSQL version:             6.0.0.3603
# Date/time:                    2016-06-02 15:31:09
# --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

# Dumping database structure for baufuchs
DROP DATABASE IF EXISTS `baufuchs`;
CREATE DATABASE IF NOT EXISTS `baufuchs` /*!40100 DEFAULT CHARACTER SET latin1 COLLATE latin1_german1_ci */;
USE `baufuchs`;


# Dumping structure for table baufuchs.angebot
DROP TABLE IF EXISTS `angebot`;
CREATE TABLE IF NOT EXISTS `angebot` (
  `angebotid` int(10) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`angebotid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_german1_ci;

# Dumping data for table baufuchs.angebot: ~0 rows (approximately)
/*!40000 ALTER TABLE `angebot` DISABLE KEYS */;
/*!40000 ALTER TABLE `angebot` ENABLE KEYS */;


# Dumping structure for table baufuchs.einkauf
DROP TABLE IF EXISTS `einkauf`;
CREATE TABLE IF NOT EXISTS `einkauf` (
  `einkaufsid` int(10) NOT NULL DEFAULT '0',
  `verkaufspreis` int(10) DEFAULT NULL,
  `menge` int(10) DEFAULT NULL,
  `kundenid` int(10) DEFAULT NULL,
  PRIMARY KEY (`einkaufsid`),
  KEY `kundenid` (`kundenid`),
  CONSTRAINT `kundenid` FOREIGN KEY (`kundenid`) REFERENCES `kunde` (`kundenid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_german1_ci;

# Dumping data for table baufuchs.einkauf: ~0 rows (approximately)
/*!40000 ALTER TABLE `einkauf` DISABLE KEYS */;
/*!40000 ALTER TABLE `einkauf` ENABLE KEYS */;


# Dumping structure for table baufuchs.filiale
DROP TABLE IF EXISTS `filiale`;
CREATE TABLE IF NOT EXISTS `filiale` (
  `filialid` int(10) NOT NULL AUTO_INCREMENT,
  `regionalgesellschaft` text,
  PRIMARY KEY (`filialid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

# Dumping data for table baufuchs.filiale: ~0 rows (approximately)
/*!40000 ALTER TABLE `filiale` DISABLE KEYS */;
/*!40000 ALTER TABLE `filiale` ENABLE KEYS */;


# Dumping structure for table baufuchs.kunde
DROP TABLE IF EXISTS `kunde`;
CREATE TABLE IF NOT EXISTS `kunde` (
  `kundenid` int(10) NOT NULL AUTO_INCREMENT,
  `plz` int(10) NOT NULL DEFAULT '0',
  `umsatz` int(10) NOT NULL DEFAULT '0',
  PRIMARY KEY (`kundenid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

# Dumping data for table baufuchs.kunde: ~0 rows (approximately)
/*!40000 ALTER TABLE `kunde` DISABLE KEYS */;
/*!40000 ALTER TABLE `kunde` ENABLE KEYS */;


# Dumping structure for table baufuchs.lieferant
DROP TABLE IF EXISTS `lieferant`;
CREATE TABLE IF NOT EXISTS `lieferant` (
  `lieferantenid` int(10) NOT NULL AUTO_INCREMENT,
  `name` text,
  PRIMARY KEY (`lieferantenid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

# Dumping data for table baufuchs.lieferant: ~0 rows (approximately)
/*!40000 ALTER TABLE `lieferant` DISABLE KEYS */;
/*!40000 ALTER TABLE `lieferant` ENABLE KEYS */;


# Dumping structure for table baufuchs.lieferung
DROP TABLE IF EXISTS `lieferung`;
CREATE TABLE IF NOT EXISTS `lieferung` (
  `lieferid` int(10) NOT NULL AUTO_INCREMENT,
  `lieferantenid` int(10) DEFAULT NULL,
  `liefermenge` int(10) DEFAULT NULL,
  `kosten` int(10) DEFAULT '0',
  PRIMARY KEY (`lieferid`),
  KEY `lieferantenid` (`lieferantenid`),
  CONSTRAINT `lieferantenid` FOREIGN KEY (`lieferantenid`) REFERENCES `lieferant` (`lieferantenid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_german1_ci;

# Dumping data for table baufuchs.lieferung: ~0 rows (approximately)
/*!40000 ALTER TABLE `lieferung` DISABLE KEYS */;
/*!40000 ALTER TABLE `lieferung` ENABLE KEYS */;


# Dumping structure for table baufuchs.produkt
DROP TABLE IF EXISTS `produkt`;
CREATE TABLE IF NOT EXISTS `produkt` (
  `produktid` int(10) NOT NULL AUTO_INCREMENT,
  `bereich` text,
  `preis` int(10) DEFAULT NULL,
  PRIMARY KEY (`produktid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

# Dumping data for table baufuchs.produkt: ~0 rows (approximately)
/*!40000 ALTER TABLE `produkt` DISABLE KEYS */;
/*!40000 ALTER TABLE `produkt` ENABLE KEYS */;


# Dumping structure for table baufuchs.rahmenvertrag
DROP TABLE IF EXISTS `rahmenvertrag`;
CREATE TABLE IF NOT EXISTS `rahmenvertrag` (
  `vertragid` int(10) NOT NULL DEFAULT '0',
  PRIMARY KEY (`vertragid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_german1_ci;

# Dumping data for table baufuchs.rahmenvertrag: ~0 rows (approximately)
/*!40000 ALTER TABLE `rahmenvertrag` DISABLE KEYS */;
/*!40000 ALTER TABLE `rahmenvertrag` ENABLE KEYS */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
