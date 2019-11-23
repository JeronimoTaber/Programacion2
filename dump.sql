-- MySQL dump 10.13  Distrib 5.7.26, for Linux (x86_64)
--
-- Host: localhost    Database: javatest
-- ------------------------------------------------------
-- Server version	5.7.26-0ubuntu0.18.04.1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `Persons`
--

DROP TABLE IF EXISTS `Persons`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Persons` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(255) DEFAULT NULL,
  `apellido` varchar(255) DEFAULT NULL,
  `documento` int(11) NOT NULL,
  `activo` binary(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `documento_UNIQUE` (`documento`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Persons`
--

LOCK TABLES `Persons` WRITE;
/*!40000 ALTER TABLE `Persons` DISABLE KEYS */;
INSERT INTO `Persons` VALUES (1,'jerrr','taberr',37380966,_binary '0'),(3,'jeronimo','taber',9340365,_binary '1');
/*!40000 ALTER TABLE `Persons` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Productos`
--

DROP TABLE IF EXISTS `Productos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Productos` (
  `idProductos` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) NOT NULL,
  `descripcion` varchar(45) DEFAULT NULL,
  `precio` int(11) NOT NULL,
  PRIMARY KEY (`idProductos`),
  UNIQUE KEY `nombre_UNIQUE` (`nombre`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Productos`
--

LOCK TABLES `Productos` WRITE;
/*!40000 ALTER TABLE `Productos` DISABLE KEYS */;
INSERT INTO `Productos` VALUES (2,'pepsi','pasteleria',25),(3,'coca','bebida',30),(4,'lais','chatarra',60),(5,'nsa','chatarra',60),(6,'ger','chatarra',60),(7,'cocaina','estupefaciente',400);
/*!40000 ALTER TABLE `Productos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `VP`
--

DROP TABLE IF EXISTS `VP`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `VP` (
  `idVP` int(11) NOT NULL AUTO_INCREMENT,
  `idVenta` int(11) DEFAULT NULL,
  `idProducto` int(11) DEFAULT NULL,
  PRIMARY KEY (`idVP`),
  KEY `fk_VP_1_idx` (`idVenta`),
  KEY `fk_VP_2_idx` (`idProducto`),
  CONSTRAINT `fk_VP_1` FOREIGN KEY (`idVenta`) REFERENCES `Ventas` (`idVentas`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_VP_2` FOREIGN KEY (`idProducto`) REFERENCES `Productos` (`idProductos`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `VP`
--

LOCK TABLES `VP` WRITE;
/*!40000 ALTER TABLE `VP` DISABLE KEYS */;
INSERT INTO `VP` VALUES (6,5,2),(7,5,3),(8,5,4),(9,5,5),(10,5,6),(11,6,2),(12,6,3),(13,6,4),(14,6,5),(15,6,6),(16,7,2),(17,7,3),(18,7,4),(19,7,5),(20,7,6);
/*!40000 ALTER TABLE `VP` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Ventas`
--

DROP TABLE IF EXISTS `Ventas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Ventas` (
  `idVentas` int(11) NOT NULL AUTO_INCREMENT,
  `fechaVenta` date DEFAULT NULL,
  `valorVenta` int(11) DEFAULT NULL,
  `idPersona` int(11) DEFAULT NULL,
  `descripcion` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`idVentas`),
  KEY `fk_Ventas_1_idx` (`idPersona`),
  CONSTRAINT `fk_Ventas_1` FOREIGN KEY (`idPersona`) REFERENCES `Persons` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Ventas`
--

LOCK TABLES `Ventas` WRITE;
/*!40000 ALTER TABLE `Ventas` DISABLE KEYS */;
INSERT INTO `Ventas` VALUES (5,'2019-06-11',115,1,'venta'),(6,'2019-06-11',115,1,'venta'),(7,'2019-06-11',115,3,'venta');
/*!40000 ALTER TABLE `Ventas` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-06-11 19:29:52
