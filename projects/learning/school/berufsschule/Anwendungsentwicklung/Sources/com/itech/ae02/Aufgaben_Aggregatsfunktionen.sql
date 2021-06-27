# Aufgabe 1
# Lassen Sie sich die Anzahl der Artikel, die in der Datenbank eingetragen sind ausgeben
SELECT COUNT(*) FROM ARTIKEL;

# Aufgabe 2
# Wie viele Kunden enthält die Kundentabelle? Wie viele Kunden sind mit ihrer E-MailAdresse
# und wie viele mit ihrer geschäftlichen Telefonnummer eingetragen? Formatieren
# Sie die Ausgabe wie folgt.
SELECT 
	COUNT(KUNDENNR) AS Kunden, 
	COUNT(EMAIL) AS 'Kunden mit Email', 
	COUNT(TELEFON_GESCH) AS 'Kunden mit Telefon'
FROM KUNDE;

# Aufgabe 3
# Ermitteln Sie das niedrigste, das höchste Gehalt, den Mittelwert und die Summe der
# Gehälter aller Mitarbeiter. Formatieren Sie die Ausgabe wie folgt.
SELECT MIN(GEHALT) AS MIN, AVG(GEHALT) AS AVG, MAX(GEHALT) AS MAX, SUM(GEHALT) AS SUM
FROM MITARBEITER;

# Aufgabe 4
# In welcher Abteilung werden die meisten Gehälter gezahlt? Ermitteln Sie die
# Duchschnittsgehälter und Summe der Gehälter aller Mitarbeiter pro Abteilung.
# a.
SELECT ABTEILUNG, AVG(GEHALT) AS 'Durchschnittsgehalt', SUM(GEHALT) AS 'Summe Gehälter'
FROM MITARBEITER
GROUP BY ABTEILUNG;

# Ändern Sie die Abfrage aus a) so, dass nur die Abteilungen 2,3 und 4 berücksichtigt
# werden. Außerdem soll die Gesamtsumme der Gehälter und das Durchschnittsgehalt
# über alle 3 Abteilungen ermittelt werden.
# b.
SELECT ABTEILUNG, AVG(GEHALT) AS 'Durchschnittsgehalt', SUM(GEHALT) AS 'Summe Gehälter'
FROM MITARBEITER
WHERE ABTEILUNG = 2 OR ABTEILUNG = 3 OR ABTEILUNG = 4
GROUP BY ABTEILUNG;

# Verändern Sie die Abfrage a) dahingehend, dass anstelle der Abteilungsnummer die
# Abteilungsbezeichnung ausgegeben wird. Außerdem soll die Ausgabe absteigend nach
# den Durchschittsgehältern pro Abteilung sortiert und nur die Abteilungen
# berücksichtigt werden, bei denen die Summe aller Gehälter über 4500€ liegen..
# c.


# Aufgabe 5
# Es soll der Preis des teuersten Monitors mit der Überschrift „höchster Preis“ ausgegeben
# werden.

# Aufgabe 6
# Lassen Sie die Anzahl der Artikel pro Kategorie und Hersteller ausgeben.
# Hinweis: Lösung mit Gruppierungsoperator ROLLUP. Dies gehört nicht zur
# Kernqualifikation der ITSEs und FISIs

# Aufgabe 7
# Für eine Marketingaktion soll ermittelt werden, aus welchen Orten die Kunden kommen.
# Geben Sie die Anzahl der Kunden pro Ort aus, dabei sollen nur Orte ausgegeben werden,
# aus denen mindestens 5 Kunden kommen. Sortieren Sie die Ausgabe absteigend nach der
# Anzahl der Kunden



# Aufgabe 8
# Sie möchten wissen wie viele Mitarbeiter pro Jahr eingestellt wurden. Lassen Sie sich a)
# die Anzahl der neu eingestellten Mitarbeiter pro Jahr ausgeben und b) zusätzlich noch die
# entsprechende Abteilung. Formatieren Sie die Abfragen wie folgt.
# Hinweis: Mit den Funktionen YEAR(datum) oder EXTRACT(YEAR FROM datum)
# erhält man das Jahr aus dem Datumsargument zurück



# Aufgabe 9
# Geben Sie eine Liste mit der Anzahl der Bestellungen und der Bestellsumme pro Monat
# aus. Formatieren Sie die Liste wie folgt.
# Hinweis: Die Funktion MONTHNAME(datum) gibt den Namen des Monats zurück


# Aufgabe 10
# Lassen Sie sich die Summe aller Rechnungsbeträge je Kunde für den Monat Januar 2014
# (Lieferdatum) ausgeben. Es sollen nur die Kunden ausgegeben werden, bei denen die die
# Summe der Rechnungsbeträge größer als 1500 € ist. Die Ausgabe soll absteigend nach der
# Summe der Rechnungsbeträge sortiert werden. 


# Aufgabe 11
# Lassen Sie sich die Bestellnummern von allen Bestellungen aus der Tabelle posten
# ausgeben, bei denen fünf Artikel oder mehr bestellt wurden. Sortieren Sie die Ausgabe
# absteigend nach Anzahl der bestellten Artikel. 



# Aufgabe 12
# Sie möchten sich einen Überblick verschaffen, welcher Kunde wie viele Artikel aus
# welcher Kategorie gekauft hat.
# Hinweis: Komplexität gehört nicht zur Kernqualifikation der ITSEs und FISIs

