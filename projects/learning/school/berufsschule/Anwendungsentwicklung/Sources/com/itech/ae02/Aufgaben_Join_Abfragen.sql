# Aufgabe 1
# Listen Sie die Mitarbeiter mit Mitarbeiternr, Name, Vorname und die Bezeichnung
# der Abteilung in der sie arbeiten.
SELECT MITARBEITERNR, NAME, VORNAME, ABTEILUNG.BEZEICHNUNG
FROM MITARBEITER
INNER JOIN ABTEILUNG
ON MITARBEITER.ABTEILUNG = ABTEILUNGSNR;

# Aufgabe 2
# Listen Sie alle Mitarbeiter auf, die in der Abteilung „Support“ beschäftigt sind. Es
# soll der Name, Vorname, das Gehalt, das Eintrittsdatum und die Bezeichnung der
# Abteilung ausgegeben werden. Die Ausgabe soll nach dem Eintrittsdatum
# absteigend sortiert werden. Verwenden Sie für die Tabellen Aliasnamen.
SELECT NAME, VORNAME, GEHALT, EINTRITTSDATUM, ABTEILUNG.BEZEICHNUNG
FROM MITARBEITER
INNER JOIN ABTEILUNG ON ABTEILUNG = ABTEILUNGSNR
WHERE BEZEICHNUNG = 'Support'
ORDER BY EINTRITTSDATUM DESC;

# Aufgabe 3
# Geben Sie zu den Kunden die zugehörigen Bestellungen aus. Es soll der Name
# und Vorname der Kunden sowie der Rechnungsbetrag sortiert nach dem
# Rechnungsbetrag ausgegeben werden.
SELECT NAME, VORNAME, BESTELLUNG.RECHNUNGSBETRAG, PLZ
FROM KUNDE
INNER JOIN BESTELLUNG

# a. Berücksichtigen Sie nur Kunden, deren Postleitzahl mit 5 beginnt und einer
# Bestellsumme über 100€.
#WHERE PLZ LIKE '5%' AND RECHNUNGSBETRAG > 100 

# b. Berücksichtigen Sie nur Kunden aus dem Postleitzahlenbereich 3 und 5
# und einer Bestellsumme zwischen 100€ und 200€
WHERE RECHNUNGSBETRAG BETWEEN 100 AND 200 AND (PLZ LIKE '3%' OR PLZ LIKE '5%')

ORDER BY BESTELLUNG.RECHNUNGSBETRAG DESC;

# Aufgabe 4
# Geben Sie eine Tabelle aus, die folgende Spalten enthält: Artikelbezeichnung,
# Herstellername, Kategoriebezeichnung, Artikelnettopreis. Sortieren Sie die Liste
# nach Herstellername, Kategoriebezeichnung und dann nach Artikelname.
SELECT ARTIKEL.BEZEICHNUNG, HERSTELLER.NAME, KATEGORIE.BEZEICHNUNG, NETTOPREIS
FROM ARTIKEL
INNER JOIN HERSTELLER ON ARTIKEL.HERSTELLER = HERSTELLER.HERSTELLERNR
INNER JOIN KATEGORIE ON ARTIKEL.KATEGORIE = KATEGORIE.KATEGORIENR
ORDER BY ARTIKEL.BEZEICHNUNG;

# Aufgabe 5 
# Geben Sie eine Tabelle aus, die folgende Spalten enthält: Kategoriebezeichnung,
# Herstellername, Artikelbezeichnung, Artikelnettopreis, Mehrwertsteuersatz in %
# und Bruttopreis Es sollen nur Artikel ausgegeben werden, die teurer als 50 €
# brutto sind. Sortieren Sie die Ausgabe nach Kategoriebezeichnung, Herstellername
# und Artikelnamen. Verwenden Sie für die Tabellen Aliasnamen. Hinweis
# der Bruttopreis muss berechnet werden.
SELECT KATEGORIE.BEZEICHNUNG AS Kategoriebezeichnung, HERSTELLER.NAME, ARTIKEL.BEZEICHNUNG, ARTIKEL.NETTOPREIS, MWSTSATZ.PROZENT, (NETTOPREIS / 100 * (100+MWSTSATZ.PROZENT)) AS BRUTTOPREIS
FROM ARTIKEL
INNER JOIN HERSTELLER ON ARTIKEL.HERSTELLER = HERSTELLER.HERSTELLERNR
INNER JOIN KATEGORIE ON ARTIKEL.KATEGORIE = KATEGORIE.KATEGORIENR
INNER JOIN MWSTSATZ ON ARTIKEL.MWST = MWSTSATZ.MWSTNR
WHERE ARTIKEL.NETTOPREIS / 100 * (100+MWSTSATZ.PROZENT) > 50
ORDER BY KATEGORIE.BEZEICHNUNG ASC, HERSTELLER.NAME ASC, ARTIKEL.BEZEICHNUNG;

# Aufgabe 6
# Es soll eine Liste aller Mitarbeiter ausgegeben werden, die ein Jobticket haben.
SELECT MITARBEITER.VORNAME, MITARBEITER.NAME, JOBTICKET.GUELTIG_BIS
FROM MITARBEITER
NATURAL JOIN JOBTICKET
ORDER BY MITARBEITER.NAME;

# Geben Sie alle Mitarbeiter aus, auch wenn sie kein Jobticket besitzen.
SELECT MITARBEITER.VORNAME, MITARBEITER.NAME, JOBTICKET.GUELTIG_BIS
FROM MITARBEITER
LEFT JOIN JOBTICKET ON MITARBEITER.MITARBEITERNR = JOBTICKET.MITARBEITERNR
ORDER BY MITARBEITER.NAME;

# Geben Sie alle Mitarbeiter aus, die kein Jobticket besitzen.
SELECT MITARBEITER.VORNAME, MITARBEITER.NAME, JOBTICKET.GUELTIG_BIS
FROM MITARBEITER
LEFT JOIN JOBTICKET ON MITARBEITER.MITARBEITERNR = JOBTICKET.MITARBEITERNR
WHERE JOBTICKET.GUELTIG_BIS IS NULL
ORDER BY MITARBEITER.NAME;

# Aufgabe 7
# Alle Artikel vom Hersteller Microsoft, die zur Kategorie Software gehören und alle
# Drucker der Firma Epson. Es sollen nur Artikel angezeigt werden, die weniger als
# 120€ kosten.
SELECT HERSTELLER.NAME, KATEGORIE.BEZEICHNUNG, ARTIKEL.NETTOPREIS
FROM ARTIKEL
INNER JOIN HERSTELLER ON ARTIKEL.HERSTELLER = HERSTELLER.HERSTELLERNR
INNER JOIN KATEGORIE ON ARTIKEL.KATEGORIE = KATEGORIE.KATEGORIENR
WHERE 	NETTOPREIS < 120 AND ((HERSTELLER.NAME = 'Microsoft' AND KATEGORIE.BEZEICHNUNG = 'Software') 
		OR (HERSTELLER.NAME = 'EPSON' AND KATEGORIE.BEZEICHNUNG = 'Drucker'));