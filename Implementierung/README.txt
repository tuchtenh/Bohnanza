Das aktuele Projekt liegt in IntelliJ_Project, alle anderen Ordner waren testweise

Damit alles läuft muss:

-postgresql mit dem Benutzer "postgres" und dem passwort "root" auf Serverseite installiert sein

- eine Datenbank namens "bohnanza muss existieren

- in Bohnanza muss eine Tabelle benutzer der Form

CREATE TABLE benutzer(
	nutzername varchar PRIMARY KEY;
	passwort varchar;
	gewonnene_spiele int;
	spiele_gesamt int;
	max_punkte int
)

haben

- die javafx-sdk-12.0.1 muss als Ordner in C:\Program Files\Java
liegen, sodass der lib Ordner den Pfad C:\Program Files\Java\javafx-sdk-12.0.1\lib
hat