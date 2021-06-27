DROP DATABASE IF EXISTS kurs_IT4_2014;
CREATE DATABASE kurs_IT4_2014;
USE kurs_IT4_2014;

# --------------------------------------------------------

#
# Tabellenstruktur für Tabelle dozent
#
#

CREATE TABLE dozent (
  PersNr int(10) NOT NULL auto_increment,
  Nachname varchar(30) default NULL,
  Vorname varchar(30) default NULL,
  Gehalt double default NULL,
  Ort varchar(30),
  PRIMARY KEY  (PersNr)
) ENGINE=INNODB ;

#
# Daten für Tabelle dozent
#

INSERT INTO dozent VALUES (1, 'Luebbe', 'Klaus', 640.0,'Hamburg');
INSERT INTO dozent VALUES (2, 'Nagel', 'Andreas',690,'Lüneburg');
INSERT INTO dozent VALUES (3, 'Giera', 'Heike', 700.0,'Hamburg');
INSERT INTO dozent VALUES (4, 'Stausberg', 'Monika', 840.0,'Hamburg');
INSERT INTO dozent VALUES (5, 'Wehmeyer', 'Markus', 860,'Buxtehude');
INSERT INTO dozent VALUES (6, 'Plewe', 'Dietmar', 950.0,'Buxtehude');

# --------------------------------------------------------

#
# Tabellenstruktur für Tabelle kurs
#
#

CREATE TABLE kurs (
  KursNr int(10) NOT NULL auto_increment,
  PersNr int(10) default NULL,
  Thema varchar(50) default NULL,
  Gebuehr double default NULL,
  PRIMARY KEY  (KursNr),
  FOREIGN KEY (PersNr) REFERENCES dozent(PersNr)
) ENGINE=INNODB ;

#
# Daten für Tabelle kurs
#

INSERT INTO kurs VALUES (1, 1, 'Datenbanken',200.0);
INSERT INTO kurs VALUES (2, 4, 'Netzwerke',220.0);
INSERT INTO kurs VALUES (3, 3, 'Organisation',160.0);
INSERT INTO kurs VALUES (4, 2, 'Englisch',140.0);
INSERT INTO kurs VALUES (5, 1, 'Mathe',160.0);
# --------------------------------------------------------
#
# Tabellenstruktur für Tabelle kursteilnehmer
#
#

CREATE TABLE kursteilnehmer (
  TeilnNr int(10) NOT NULL auto_increment,
  Nachname varchar(30) default NULL,
  Vorname varchar(30) default NULL,
  PRIMARY KEY  (TeilnNr)
) ENGINE=INNODB;

#
# Daten für Tabelle kursteilnehmer
#

INSERT INTO kursteilnehmer VALUES (1, 'Neumann', 'Marcus');
INSERT INTO kursteilnehmer VALUES (2, 'Bartels', 'Henrik');
INSERT INTO kursteilnehmer VALUES (3, 'Blank', 'Manuel');
INSERT INTO kursteilnehmer VALUES (4, 'Menniken', 'Florian');
INSERT INTO kursteilnehmer VALUES (5, 'Witkowski', 'Britta');
INSERT INTO kursteilnehmer VALUES (6, 'Neef', 'Svenja');
INSERT INTO kursteilnehmer VALUES (7, 'Glass', 'D�rte');
INSERT INTO kursteilnehmer VALUES (8, 'Guenter', 'Grit');
INSERT INTO kursteilnehmer VALUES (9, 'Roehling', 'Sven');
INSERT INTO kursteilnehmer VALUES (10, 'Bratschke', 'Tobias');
INSERT INTO kursteilnehmer VALUES (11, 'Kaatz', 'Daniel');
INSERT INTO kursteilnehmer VALUES (12, 'Komen', 'Dennis');
INSERT INTO kursteilnehmer VALUES (13, 'Auga', 'Benjamin');
INSERT INTO kursteilnehmer VALUES (14, 'Anders', 'Christian');
INSERT INTO kursteilnehmer VALUES (15, 'Lamey', 'Philipp');
INSERT INTO kursteilnehmer VALUES (16, 'Schnoor', 'Timo');
INSERT INTO kursteilnehmer VALUES (17, 'Utes', 'Stephanie');
INSERT INTO kursteilnehmer VALUES (18, 'Stein', 'Yani');
INSERT INTO kursteilnehmer VALUES (19, 'Wahrmann', 'Christian');
INSERT INTO kursteilnehmer VALUES (20, 'Goertz', 'Florian');
INSERT INTO kursteilnehmer VALUES (21, 'Schuessler', 'Christoph');
INSERT INTO kursteilnehmer VALUES (22, 'Jensen', 'Mirre');


# --------------------------------------------------------

#
# Tabellenstruktur für Tabelle kursbelegung
#
#

CREATE TABLE kursbelegung (
  KursNr int(10) NOT NULL default '0',
  TeilnNr int(10) NOT NULL default '0',
  PRIMARY KEY  (KursNr,TeilnNr),
  FOREIGN KEY (KursNr) REFERENCES kurs(KursNr),
  FOREIGN KEY (TeilnNr) REFERENCES kursteilnehmer(TeilnNr)
) ENGINE=INNODB;

#
# Daten für Tabelle kursbelegung
#

INSERT INTO kursbelegung VALUES (1, 1);
INSERT INTO kursbelegung VALUES (1, 7);
INSERT INTO kursbelegung VALUES (1, 8);
INSERT INTO kursbelegung VALUES (1, 11);
INSERT INTO kursbelegung VALUES (1, 17);
INSERT INTO kursbelegung VALUES (1, 18);
INSERT INTO kursbelegung VALUES (2, 2);
INSERT INTO kursbelegung VALUES (2, 5);
INSERT INTO kursbelegung VALUES (2, 9);
INSERT INTO kursbelegung VALUES (2, 10);
INSERT INTO kursbelegung VALUES (2, 12);
INSERT INTO kursbelegung VALUES (2, 15);
INSERT INTO kursbelegung VALUES (2, 19);
INSERT INTO kursbelegung VALUES (2, 20);
INSERT INTO kursbelegung VALUES (3, 4);
INSERT INTO kursbelegung VALUES (3, 14);
INSERT INTO kursbelegung VALUES (4, 3);
INSERT INTO kursbelegung VALUES (4, 11);
INSERT INTO kursbelegung VALUES (4, 13);
INSERT INTO kursbelegung VALUES (5, 1);
INSERT INTO kursbelegung VALUES (5, 6);
INSERT INTO kursbelegung VALUES (5, 11);
INSERT INTO kursbelegung VALUES (5, 16);


