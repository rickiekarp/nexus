# Aufgabe 1
# Listen Sie alle Artikel der Tabelle artikel auf, 
# deren Nettopreis höher als 100 Euro ist.
SELECT * FROM ARTIKEL
WHERE NETTOPREIS > 100;

# Aufgabe 2
# Listen Sie alle Artikel der Tabelle Artikel mit Artikelnr, Bezeichnung und Preis auf,
# deren Nettopreis zwischen 100 und 200 Euro liegen. Die Ausgabe soll
# aufsteigend nach Artikelbezeichnung und absteigend nach dem Preis sortiert
# werden.
SELECT ARTIKELNR, BEZEICHNUNG FROM ARTIKEL
WHERE NETTOPREIS > 100 AND NETTOPREIS < 200;

# Aufgabe 3
# Listen Sie alle unterschiedlichen Wohnorte der Kunden auf. (Jeder Wohnort darf
# nur einmal erscheinen). Der Ort soll unter der Überschrift Wohnort erscheinen. 
SELECT DISTINCT ORT as ORT FROM KUNDE
ORDER BY ORT;

# Aufgabe 4
# Name, PLZ, Ort aller Kunden, die aus Hamburg, Hannover oder Bremen
# kommen. Die Spalte Name soll die Überschrift „Kundenname“ erhalten. Die
# Ausgabe soll nach Wohnort und Name sortiert werden.
SELECT NAME AS KUNDENNAME, PLZ, ORT FROM KUNDE
WHERE ORT ='Hamburg' OR ORT='Bremen' OR ORT='Hannover'
ORDER BY ORT, NAME;

# Aufgabe 5
# Geben Sie die Kundennr, Name, Vorname, Ort alle Kunden aus, die eine e-mailAdresse
# bei „on-line“ haben. Sortieren Sie die Ausgabe alphabetisch nach dem
# Nachnamen und nach dem Vornamen.
SELECT KUNDENNR, NAME, VORNAME, ORT FROM KUNDE
WHERE EMAIL like'%on-line%'
ORDER BY NAME, VORNAME;

# Aufgabe 6
# Die Geschäftsführung beschließt für einen Monat eine Sonderaktion
# durchzuführen. Für alle Festplatten (Kategorie 4) wird ein Rabatt von 15%
# gewährt. Geben Sie die entsprechenden Artikel mit Artikelnummer, Bezeichnung,
# alter Nettopreis und neuer Nettopreis aus. 
SELECT ARTIKELNR, BEZEICHNUNG, NETTOPREIS, (NETTOPREIS * 0.85) FROM ARTIKEL
WHERE KATEGORIE = 4;

# Aufgabe 7
# Geben Sie alle Kunden mit Nachnamen, Vornamen und Ort aus, die nicht mit
# ihrer E-Mail-Adresse eingetragen sind? Sortieren Sie die Ausgabe aufsteigend
# nach dem Nachnamen.
SELECT NAME, VORNAME, ORT FROM KUNDE
WHERE EMAIL IS NULL
ORDER BY NAME ASC;

# Aufgabe 8
# Alle Mitarbeiter, die nach dem 1.1.2010 eingestellt wurden mit Nr, Namen,
# Vornamen und Eintrittsdatum, sortiert nach dem Eintrittsdatum.
SELECT MITARBEITERNR, NAME, VORNAME, EINTRITTSDATUM FROM MITARBEITER
WHERE EINTRITTSDATUM > '2010-01-01'
ORDER BY EINTRITTSDATUM;

# Aufgabe 9
# Die Firma möchte ihren Mitarbeitern eine Gehaltserhöhung von 5% zahlen.
# Geben Sie alle Mitarbeiter mit Nummer, Name, Vorname, Gehalt, eine neue
# Spalte „GehaltnachErhöhung“ mit dem Gehalt nach der Erhöhung sowie eine
# Spalte „Gehaltserhöhung“ mit dem Betrag der Gehaltserhöhung.
SELECT MITARBEITERNR, NAME, VORNAME, GEHALT, (GEHALT * 1.15) AS NachErhoehung, (GEHALT * 0.15) AS Gehaltserhöhung
FROM MITARBEITER;

# Aufgabe 10
# Alle Artikel, außer Monitore(Kategorie=1), mit Bezeichnung, Nettopreis und
# Kategorie, die mehr als 150€ kosten. Die Ausgabe ist nach Kategorie und Preis
# aufsteigend zu sortieren.
SELECT BEZEICHNUNG, NETTOPREIS, KATEGORIE FROM ARTIKEL
WHERE NETTOPREIS > 150 AND KATEGORIE != 1
ORDER BY KATEGORIE ASC, NETTOPREIS ASC;