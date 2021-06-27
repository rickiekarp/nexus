## Aufgabe 1
## Erzeugen Sie die Datenbank Verband. Verhindern Sie eine Fehlermeldung 
## beim Ausführen als Skript, falls die Datenbank schon existiert.
DROP DATABASE IF EXISTS verband;
CREATE DATABASE IF NOT EXISTS verband;

USE verband;

## Aufgabe 2
## Erzeugen Sie für die Datenbank Verband die Tabellen Mitglied, Platzierung und Wettkampf
## Legen Sie für die Attribute sinnvolle Eigenschaften fest. Berücksichtigen Sie referentielle Integrität.
## Fremdschlüssel können nicht leer sein.
CREATE TABLE  IF NOT EXISTS Mitglied (
MitgliedNr INT(5) PRIMARY KEY NOT NULL AUTO_INCREMENT,
Vorname VARCHAR(15) NOT NULL,
Nachname VARCHAR(15) NOT NULL,
VereinNr VARCHAR(15) NOT NULL,
GebDatum DATE
);

CREATE TABLE  IF NOT EXISTS Wettkampf (
WKampfNr INT(5) PRIMARY KEY NOT NULL AUTO_INCREMENT,
Start Date,
Ende Date
);

CREATE TABLE  IF NOT EXISTS Platzierung (
MitgliedNr INT(5) NOT NULL,
WKampfNr INT(5) NOT NULL,
Platzierung VARCHAR(15),
PRIMARY KEY (MitgliedNr, WKampfNr),
FOREIGN KEY (MitgliedNr) REFERENCES Mitglied (MitgliedNr),
FOREIGN KEY (WKampfNr) REFERENCES Wettkampf (WKampfNr)
);

## Aufgabe 3
## Tragen Sie sich selbst als Mitglied ein
INSERT INTO Mitglied VALUES (1, 'vorname', 'nachname', 1, 111);

## Aufgabe 4
## In der Tabelle Wettkampf wurde versehentlich der Ort vergessen.
## Fügen sie diesen in einem weiteren Schritt hinzu.
ALTER TABLE Wettkampf ADD Ort VARCHAR(15);