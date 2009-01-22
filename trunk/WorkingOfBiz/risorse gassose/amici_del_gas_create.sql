SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL';

CREATE SCHEMA IF NOT EXISTS `mydb` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci ;
USE `mydb`;

-- -----------------------------------------------------
-- Table `mydb`.`Comune`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`Comune` ;

CREATE  TABLE IF NOT EXISTS `mydb`.`Comune` (
  `IDComune` INT UNSIGNED NOT NULL AUTO_INCREMENT ,
  `NomeComune` VARCHAR(50) NOT NULL ,
  `Provincia` VARCHAR(50) NOT NULL ,
  `CAP` INT UNSIGNED NOT NULL ,
  PRIMARY KEY (`IDComune`) )
PACK_KEYS = 0
ROW_FORMAT = DEFAULT;


-- -----------------------------------------------------
-- Table `mydb`.`Account`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`Account` ;

CREATE  TABLE IF NOT EXISTS `mydb`.`Account` (
  `Username` VARCHAR(20) NOT NULL ,
  `Pass` VARCHAR(10) NOT NULL ,
  `Bloccato` BOOLEAN NOT NULL ,
  `Elimato` BOOLEAN NOT NULL ,
  `Cancellato` BOOLEAN NOT NULL ,
  `Attivato` BOOLEAN NOT NULL ,
  `DataRichiesta` DATE NOT NULL ,
  `DataAccettazione` DATE NULL ,
  `FeedbackPositivo` INT UNSIGNED NOT NULL DEFAULT 0 ,
  PRIMARY KEY (`Username`) )
PACK_KEYS = 0
ROW_FORMAT = DEFAULT;


-- -----------------------------------------------------
-- Table `mydb`.`Utente`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`Utente` ;

CREATE  TABLE IF NOT EXISTS `mydb`.`Utente` (
  `CodiceFiscale` VARCHAR(16) NOT NULL ,
  `IDComune` INT UNSIGNED NOT NULL ,
  `Username` VARCHAR(20) NOT NULL ,
  `Nome` VARCHAR(50) NOT NULL ,
  `Cognome` VARCHAR(50) NOT NULL ,
  `DataNascita` DATE NOT NULL ,
  `LuogoNascita` VARCHAR(50) NOT NULL ,
  `Sesso` TINYINT UNSIGNED NOT NULL COMMENT '1 Maschio, 0 zero' ,
  `PathQuestionario` VARCHAR(500) NOT NULL ,
  `Indirizzo` VARCHAR(100) NOT NULL COMMENT 'Indirizzo e NumeroCivico' ,
  PRIMARY KEY (`CodiceFiscale`) ,
  INDEX `Utente_FKIndex1` (`IDComune` ASC) ,
  INDEX `Utente_FKIndex3` (`Username` ASC) ,
  CONSTRAINT `fk_{9704DEAA-BB32-47EF-AD4F-9347E0C8A85D}`
    FOREIGN KEY (`IDComune` )
    REFERENCES `mydb`.`Comune` (`IDComune` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_{9A51EF98-DD0A-44F1-B02A-83F5A7B44287}`
    FOREIGN KEY (`Username` )
    REFERENCES `mydb`.`Account` (`Username` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
PACK_KEYS = 0
ROW_FORMAT = DEFAULT;


-- -----------------------------------------------------
-- Table `mydb`.`Patente`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`Patente` ;

CREATE  TABLE IF NOT EXISTS `mydb`.`Patente` (
  `IDPatente` INT UNSIGNED NOT NULL AUTO_INCREMENT ,
  `CodiceFiscale` VARCHAR(16) NOT NULL ,
  `Tipo` VARCHAR(2) NOT NULL ,
  PRIMARY KEY (`IDPatente`) ,
  INDEX `Patente_FKIndex1` (`CodiceFiscale` ASC) ,
  CONSTRAINT `fk_{B12D8C79-3FAF-4495-843B-8861D9236FA5}`
    FOREIGN KEY (`CodiceFiscale` )
    REFERENCES `mydb`.`Utente` (`CodiceFiscale` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
PACK_KEYS = 0
ROW_FORMAT = DEFAULT;


-- -----------------------------------------------------
-- Table `mydb`.`Ruolo`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`Ruolo` ;

CREATE  TABLE IF NOT EXISTS `mydb`.`Ruolo` (
  `IDRuolo` INT UNSIGNED NOT NULL AUTO_INCREMENT ,
  `Username` VARCHAR(20) NOT NULL ,
  `Tipo` VARCHAR(100) NOT NULL ,
  PRIMARY KEY (`IDRuolo`) ,
  INDEX `Ruolo_FKIndex1` (`Username` ASC) ,
  CONSTRAINT `fk_{98558AB5-AC31-4A9A-91A0-46CB0A502194}`
    FOREIGN KEY (`Username` )
    REFERENCES `mydb`.`Account` (`Username` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
PACK_KEYS = 0
ROW_FORMAT = DEFAULT;


-- -----------------------------------------------------
-- Table `mydb`.`GAS`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`GAS` ;

CREATE  TABLE IF NOT EXISTS `mydb`.`GAS` (
  `idGAS` INT UNSIGNED NOT NULL AUTO_INCREMENT ,
  `Nome` VARCHAR(50) NULL ,
  `Descrizione` TEXT NULL ,
  PRIMARY KEY (`idGAS`) )
PACK_KEYS = 0
ROW_FORMAT = DEFAULT;


-- -----------------------------------------------------
-- Table `mydb`.`Punti_di_Consegna`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`Punti_di_Consegna` ;

CREATE  TABLE IF NOT EXISTS `mydb`.`Punti_di_Consegna` (
  `IDPuntiConsegna` INT UNSIGNED NOT NULL AUTO_INCREMENT ,
  `IDComune` INT UNSIGNED NOT NULL ,
  `Indirizzo` VARCHAR(500) NOT NULL COMMENT 'Indirizzo NumeroCivico' ,
  `Coordinate` INT UNSIGNED NULL COMMENT 'Coordinate google Maps' ,
  PRIMARY KEY (`IDPuntiConsegna`) ,
  INDEX `Punti di Consegna_FKIndex1` (`IDComune` ASC) ,
  CONSTRAINT `fk_{18C19BAE-C78A-40DD-B5C3-D8E0CD0B9FE3}`
    FOREIGN KEY (`IDComune` )
    REFERENCES `mydb`.`Comune` (`IDComune` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
PACK_KEYS = 0
ROW_FORMAT = DEFAULT;


-- -----------------------------------------------------
-- Table `mydb`.`Articolo`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`Articolo` ;

CREATE  TABLE IF NOT EXISTS `mydb`.`Articolo` (
  `IDArticolo` INT UNSIGNED NOT NULL AUTO_INCREMENT ,
  `CodiceEsterno` VARCHAR(45) NOT NULL ,
  `Descrizione` TEXT NULL ,
  PRIMARY KEY (`IDArticolo`) )
PACK_KEYS = 0
ROW_FORMAT = DEFAULT;


-- -----------------------------------------------------
-- Table `mydb`.`Ordine`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`Ordine` ;

CREATE  TABLE IF NOT EXISTS `mydb`.`Ordine` (
  `IDOrdine` INT UNSIGNED NOT NULL AUTO_INCREMENT ,
  `Username` VARCHAR(20) NOT NULL ,
  `IDArticolo` INT UNSIGNED NOT NULL ,
  `DataRichiesta` DATETIME NOT NULL ,
  `Pendente` BOOLEAN NOT NULL ,
  `Quantita` INT UNSIGNED NOT NULL ,
  `QuantitaMinUtente` INT UNSIGNED NOT NULL ,
  `DataMassimaConsegna` DATETIME NULL ,
  PRIMARY KEY (`IDOrdine`) ,
  INDEX `Ordini_FKIndex1` (`Username` ASC) ,
  INDEX `Ordini_FKIndex2` (`IDArticolo` ASC) ,
  CONSTRAINT `fk_{8346770B-3F75-4F4A-BF67-818F03BD08FF}`
    FOREIGN KEY (`Username` )
    REFERENCES `mydb`.`Account` (`Username` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_{C5C88862-D5CC-4C3D-A0FE-AF311DE1E387}`
    FOREIGN KEY (`IDArticolo` )
    REFERENCES `mydb`.`Articolo` (`IDArticolo` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
PACK_KEYS = 0
ROW_FORMAT = DEFAULT;


-- -----------------------------------------------------
-- Table `mydb`.`CyberContadino`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`CyberContadino` ;

CREATE  TABLE IF NOT EXISTS `mydb`.`CyberContadino` (
  `PartitaIVA` INT UNSIGNED NOT NULL ,
  `IDArticolo` INT UNSIGNED NOT NULL ,
  `Username` VARCHAR(20) NOT NULL ,
  `Comune_IDComune` INT UNSIGNED NOT NULL ,
  `NomePresidente` VARCHAR(50) NULL ,
  `Indirizzo` VARCHAR(100) NOT NULL ,
  `CognomePresidente` VARCHAR(50) NULL ,
  `NomeAzienda` VARCHAR(50) NOT NULL ,
  `PathQuestionario` VARCHAR(500) NULL ,
  `UrlWISDL` VARCHAR(500) NOT NULL ,
  `DescrizioneAzienda` TEXT NULL ,
  PRIMARY KEY (`PartitaIVA`) ,
  INDEX `CyberContadino_FKIndex2` (`Username` ASC) ,
  INDEX `CyberContadino_FKIndex3` (`IDArticolo` ASC) ,
  INDEX `CyberContadino_FKIndex4` (`Comune_IDComune` ASC) ,
  CONSTRAINT `fk_{AAF543F6-8237-4FC9-8FE4-6A6761472FCF}`
    FOREIGN KEY (`Username` )
    REFERENCES `mydb`.`Account` (`Username` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_{9B67207F-4F1A-49F4-B022-AC7989142AC7}`
    FOREIGN KEY (`IDArticolo` )
    REFERENCES `mydb`.`Articolo` (`IDArticolo` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_{A0F5C480-1BDF-4767-81EE-A0B5E3F23DCB}`
    FOREIGN KEY (`Comune_IDComune` )
    REFERENCES `mydb`.`Comune` (`IDComune` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
PACK_KEYS = 0
ROW_FORMAT = DEFAULT;


-- -----------------------------------------------------
-- Table `mydb`.`Itinerario`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`Itinerario` ;

CREATE  TABLE IF NOT EXISTS `mydb`.`Itinerario` (
  `IDItinerario` INT UNSIGNED NOT NULL AUTO_INCREMENT ,
  `Username` VARCHAR(20) NOT NULL ,
  `DataPartenza` DATETIME NOT NULL ,
  `DataConsegna` DATETIME NOT NULL ,
  `Documentazione` TEXT NULL ,
  `DataCreazione` DATETIME NOT NULL ,
  PRIMARY KEY (`IDItinerario`) ,
  INDEX `Itinerario_FKIndex1` (`Username` ASC) ,
  CONSTRAINT `fk_{03D2A586-96CD-46BB-A066-DA7F6096FFB9}`
    FOREIGN KEY (`Username` )
    REFERENCES `mydb`.`Account` (`Username` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
PACK_KEYS = 0
ROW_FORMAT = DEFAULT;


-- -----------------------------------------------------
-- Table `mydb`.`FeedbackNegativo`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`FeedbackNegativo` ;

CREATE  TABLE IF NOT EXISTS `mydb`.`FeedbackNegativo` (
  `IDFeedback` INT UNSIGNED NOT NULL AUTO_INCREMENT ,
  `IDOrdine` INT UNSIGNED NOT NULL ,
  `Validatore` VARCHAR(20) NOT NULL ,
  `Segnalatore` VARCHAR(20) NOT NULL ,
  `Destinatario` VARCHAR(20) NOT NULL ,
  `Analizzato` BOOLEAN NOT NULL ,
  `Descrizione` TEXT NULL ,
  `DataValidazione` DATE NOT NULL ,
  `DataSegnalazione` DATE NOT NULL ,
  PRIMARY KEY (`IDFeedback`) ,
  INDEX `FeedbackNegativo_FKIndex1` (`Segnalatore` ASC) ,
  INDEX `FeedbackNegativo_FKIndex2` (`Destinatario` ASC) ,
  INDEX `FeedbackNegativo_FKIndex3` (`IDOrdine` ASC) ,
  INDEX `FeedbackNegativo_FKIndex4` (`Validatore` ASC) ,
  CONSTRAINT `fk_{12F9DF03-9296-46A4-9595-5D9D64C20A9A}`
    FOREIGN KEY (`Segnalatore` )
    REFERENCES `mydb`.`Account` (`Username` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_{DDE823F1-BC03-4FF2-8964-4401B266B004}`
    FOREIGN KEY (`Destinatario` )
    REFERENCES `mydb`.`Account` (`Username` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_{4A00E5E0-736D-4064-8BD2-64B1502FD36D}`
    FOREIGN KEY (`IDOrdine` )
    REFERENCES `mydb`.`Ordine` (`IDOrdine` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_{8E4B9CBF-CDE5-4F9E-B216-88C7825FDE65}`
    FOREIGN KEY (`Validatore` )
    REFERENCES `mydb`.`Account` (`Username` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
PACK_KEYS = 0
ROW_FORMAT = DEFAULT;


-- -----------------------------------------------------
-- Table `mydb`.`Itinerario_has_Ordine`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`Itinerario_has_Ordine` ;

CREATE  TABLE IF NOT EXISTS `mydb`.`Itinerario_has_Ordine` (
  `IDOrdine` INT UNSIGNED NOT NULL ,
  `IDItinerario` INT UNSIGNED NOT NULL ,
  PRIMARY KEY (`IDOrdine`, `IDItinerario`) ,
  INDEX `Itinerario_has_Ordine_FKIndex1` (`IDItinerario` ASC) ,
  INDEX `Itinerario_has_Ordine_FKIndex2` (`IDOrdine` ASC) ,
  CONSTRAINT `fk_{D4E7A693-7A59-43C6-B615-2F66E1F97AEC}`
    FOREIGN KEY (`IDItinerario` )
    REFERENCES `mydb`.`Itinerario` (`IDItinerario` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_{3F7881B4-92E8-4D66-B4B0-A5812C8F06EE}`
    FOREIGN KEY (`IDOrdine` )
    REFERENCES `mydb`.`Ordine` (`IDOrdine` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);


-- -----------------------------------------------------
-- Table `mydb`.`Itinerario_has_CyberContadino`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`Itinerario_has_CyberContadino` ;

CREATE  TABLE IF NOT EXISTS `mydb`.`Itinerario_has_CyberContadino` (
  `IDItinerario` INT UNSIGNED NOT NULL ,
  `PartitaIVA` INT UNSIGNED NOT NULL ,
  PRIMARY KEY (`IDItinerario`, `PartitaIVA`) ,
  INDEX `Itinerario_has_CyberContadino_FKIndex1` (`IDItinerario` ASC) ,
  INDEX `Itinerario_has_CyberContadino_FKIndex2` (`PartitaIVA` ASC) ,
  CONSTRAINT `fk_{DC418E24-964D-42A0-9FAA-BF1C535E4D44}`
    FOREIGN KEY (`IDItinerario` )
    REFERENCES `mydb`.`Itinerario` (`IDItinerario` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_{0269B7AE-7AA8-4277-B629-D2A0DD86750A}`
    FOREIGN KEY (`PartitaIVA` )
    REFERENCES `mydb`.`CyberContadino` (`PartitaIVA` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);


-- -----------------------------------------------------
-- Table `mydb`.`Itinerario_has_Punti di Consegna`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`Itinerario_has_Punti di Consegna` ;

CREATE  TABLE IF NOT EXISTS `mydb`.`Itinerario_has_Punti di Consegna` (
  `IDPuntiConsegna` INT UNSIGNED NOT NULL ,
  `IDItinerario` INT UNSIGNED NOT NULL ,
  PRIMARY KEY (`IDPuntiConsegna`, `IDItinerario`) ,
  INDEX `Itinerario_has_Punti di Consegna_FKIndex1` (`IDItinerario` ASC) ,
  INDEX `Itinerario_has_Punti di Consegna_FKIndex2` (`IDPuntiConsegna` ASC) ,
  CONSTRAINT `fk_{D41EF012-DF11-4187-B08A-D62DBC721A0D}`
    FOREIGN KEY (`IDItinerario` )
    REFERENCES `mydb`.`Itinerario` (`IDItinerario` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_{E0E4EF2B-C655-48D3-BDD1-1276ECCE10FC}`
    FOREIGN KEY (`IDPuntiConsegna` )
    REFERENCES `mydb`.`Punti_di_Consegna` (`IDPuntiConsegna` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);



SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
