SET GLOBAL innodb_file_per_table = 1; ## erzwingt multiple tablespaces, kann erst ab MySQL 5.0 gesetzt werden
/******************************************************************************/
/***                                 Tables                                 ***/
/******************************************************************************/
DROP DATABASE IF EXISTS sqlteacher;
CREATE DATABASE IF NOT EXISTS sqlteacher /*!40100 DEFAULT CHARACTER SET latin1 */;
USE sqlteacher;


CREATE TABLE ABTEILUNG (
    ABTEILUNGSNR  INTEGER NOT NULL,
    BEZEICHNUNG   VARCHAR(50)
)ENGINE = InnoDB;

CREATE TABLE ARTIKEL (
    ARTIKELNR         INTEGER NOT NULL,
    BEZEICHNUNG       VARCHAR(50),
    HERSTELLER        INTEGER,
    NETTOPREIS        DECIMAL(10,2),
    MWST              INTEGER,
    BESTAND           INTEGER,
    MINDESTBESTAND    INTEGER,
    KATEGORIE         INTEGER,
    BESTELLVORSCHLAG  CHAR(1) DEFAULT '0'
)ENGINE = InnoDB;

CREATE TABLE BESTELLUNG (
    BESTELLNR        INTEGER NOT NULL,
    KUNDENNR         INTEGER,
    BESTELLDATUM     DATE,
    LIEFERDATUM      DATE,
    RECHNUNGSBETRAG  DECIMAL(10,2)
)ENGINE = InnoDB;

CREATE TABLE HERSTELLER (
    HERSTELLERNR  INTEGER NOT NULL,
    NAME          VARCHAR(50)
)ENGINE = InnoDB;

CREATE TABLE JOBTICKET (
    ID             INTEGER NOT NULL,
    MITARBEITERNR  INTEGER,
    GUELTIG_BIS    DATE
)ENGINE = InnoDB;

CREATE TABLE KATEGORIE (
    KATEGORIENR  INTEGER NOT NULL,
    BEZEICHNUNG  VARCHAR(50)
)ENGINE = InnoDB;

CREATE TABLE KUNDE (
    KUNDENNR        INTEGER NOT NULL,
    NAME            VARCHAR(50),
    VORNAME         VARCHAR(50),
    STRASSE         VARCHAR(50),
    PLZ             CHAR(14),
    ORT             VARCHAR(50),
    TELEFON_GESCH   VARCHAR(25),
    TELEFON_PRIVAT  VARCHAR(25),
    EMAIL           VARCHAR(50),
    ZAHLUNGSART     CHAR(1)
)ENGINE = InnoDB;

CREATE TABLE MITARBEITER (
    MITARBEITERNR   INTEGER NOT NULL,
    NAME            VARCHAR(50),
    VORNAME         VARCHAR(50),
    STRASSE         VARCHAR(50),
    PLZ             CHAR(14),
    ORT             VARCHAR(50),
    GEHALT          DECIMAL(10,2),
    ABTEILUNG       INTEGER,
    TELEFONNUMMER   VARCHAR(25),
    EMAIL           VARCHAR(50),
    EINTRITTSDATUM  DATE
)ENGINE = InnoDB;

CREATE TABLE MWSTSATZ (
    MWSTNR   INTEGER NOT NULL,
    PROZENT  DECIMAL(4,2)
)ENGINE = InnoDB;

CREATE TABLE POSTEN (
    BESTELLNR     INTEGER NOT NULL,
    ARTIKELNR     INTEGER,
    BESTELLMENGE  INTEGER,
    LIEFERMENGE   INTEGER
)ENGINE = InnoDB;


/******************************************************************************/
/***                              Primary Keys                              ***/
/******************************************************************************/

ALTER TABLE ABTEILUNG ADD PRIMARY KEY (ABTEILUNGSNR);
ALTER TABLE ARTIKEL ADD PRIMARY KEY (ARTIKELNR);
ALTER TABLE BESTELLUNG ADD PRIMARY KEY (BESTELLNR);
ALTER TABLE HERSTELLER ADD PRIMARY KEY (HERSTELLERNR);
ALTER TABLE KATEGORIE ADD PRIMARY KEY (KATEGORIENR);
ALTER TABLE KUNDE ADD PRIMARY KEY (KUNDENNR);
ALTER TABLE MITARBEITER ADD PRIMARY KEY (MITARBEITERNR);
ALTER TABLE MWSTSATZ ADD PRIMARY KEY (MWSTNR);


/******************************************************************************/
/***                              Foreign Keys                              ***/
/******************************************************************************/

ALTER TABLE ARTIKEL ADD FOREIGN KEY (MWST) REFERENCES MWSTSATZ (MWSTNR);
ALTER TABLE ARTIKEL ADD FOREIGN KEY (HERSTELLER) REFERENCES HERSTELLER (HERSTELLERNR);
ALTER TABLE ARTIKEL ADD FOREIGN KEY (KATEGORIE) REFERENCES KATEGORIE (KATEGORIENR);
ALTER TABLE BESTELLUNG ADD FOREIGN KEY (KUNDENNR) REFERENCES KUNDE (KUNDENNR);
ALTER TABLE JOBTICKET ADD FOREIGN KEY (MITARBEITERNR) REFERENCES MITARBEITER (MITARBEITERNR);
ALTER TABLE MITARBEITER ADD FOREIGN KEY (ABTEILUNG) REFERENCES ABTEILUNG (ABTEILUNGSNR);
ALTER TABLE POSTEN ADD FOREIGN KEY (BESTELLNR) REFERENCES BESTELLUNG (BESTELLNR);
ALTER TABLE POSTEN ADD FOREIGN KEY (ARTIKELNR) REFERENCES ARTIKEL (ARTIKELNR);


/******************************************************************************/
/***                                Indices                                 ***/
/******************************************************************************/

CREATE INDEX IDX_NAME ON KUNDE (NAME);