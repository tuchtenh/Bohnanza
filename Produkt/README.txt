Willkommen zum Spiel Bohnanza!

WINDOWS:

Um zu spielen, musst du Java 8 oder höher als aktive Java Runtime Environment
installiert haben.
Um die .jar-Dateien im Folgenden auszuführen musst du sie in der Konsole deines
Betriebssystems ausführen mit:

java -jar <Name_der_JarDatein>.jar

--------------------------------------------------------------------------------
Möchtest du nur als Client gegen andere Spieler spielen? Dann musst du nur die
CLient.jar auswählen und unter "Serveradresse setzen" einen dir bekannten Server
eintragen. Anschließend musst du dich Registrieren und kannst loslegen!

--------------------------------------------------------------------------------
Möchtest du als Server agieren und ggf. alleine gegen Bots oder gegen menschliche
Spieler spielen? Dann sind ein paar mehr Schritte erforderlich:

1. Installation der Datenbank
- Führe die Datei "postgresql-11.3-4-windows-x64.exe" im aktuellen Ordner aus
und installiere Postgres in einem beliebigen Ordner. Wähle bei der Installation
den Benutzer "postgres" und das Passwort "root" aus.

- Nach erfolgreicher Installtion kannst du den Datenbankserver mittels "pgAdmin"
aufrufen.

- Erstellte eine neue Datenbank namens "bohnanza"

- Importiere die Datenbankstruktur, indem du die  Datei
"bohnanza.sql" öffnest und den gesamten Inhalt über das QueryTool ausführst.

2. Starten des Servers
- Nach erfolgreicher Installation kannst du nun die Server.jar starten

3. Starten des Clients
- Nach Starten des Servers kannsdt du dich, wie oben beschrieben, registrieren
und andere zum Spielen einladen.

LINUX:

Um zu spielen, musst du Java 8 als aktive Java Runtime Environment
installiert haben.
Um die .jar-Dateien im Folgenden auszuführen musst du sie in der Konsole deines
Betriebssystems ausführen mit:

java -jar <Name_der_JarDatein>.jar
--------------------------------------------------------------------------------
Möchtest du nur als Client gegen andere Spieler spielen? Dann musst du, je nach
Linux Distribution, die Firewall deaktivieren. Unter Ubuntu z.B. durch "sudo ufw
disable". Zusätzlich sollten die IPTABLES geleert und deaktiviert werden (schnelle Lösung - nicht empfohlen), bzw.
spezifische Regeln (Rules) eingerichtet werden (empfohlen). Zum Deaktivieren gib folgende
Kommandozeilenbefehle nacheinander ein:

iptables -P INPUT ACCEPT
iptables -P OUTPUT ACCEPT
iptables -P FORWARD ACCEPT

iptables -F

Nun sollten keine Ports mehr geblockt werden. Du kannst nun die Client.jar Datei
ausführen, die Serveradresse setzen und loslegen.

--------------------------------------------------------------------------------
Möchtest du als Server agieren und ggf. alleine gegen Bots oder gegen menschliche
Spieler spielen? Dann sind ein paar mehr Schritte erforderlich:

ARCH:
1. Installation der Datenbank (ausführlich unter https://wiki.archlinux.org/index.php/PostgreSQL#Installation)
- Installiere postgresql 11.4-1 oder höher mittels yaourt
- Installiere pgAdmin 4 oder höher mittels yaourt

- Nach erfolgreicher Installtion kannst du die Datenbank initialisieren (Kommandozeile):
sudo -iu postgres
initdb -D /var/lib/postgres/data

- Erstelle einen Nutzer postgres mit Passwort root (Kommandozeile):
createuser --interactive

- Gibt Zugriffsrechte von außerhalb, indem du postgresql.conf öffnest und den EIntrag
"listen_addresses = 'localhost, local_ip'" setzt, wobei local_ip deine lokale IP-Adresse
ist (zu findne über ip address in Kommandozeile).

- Erstelle einen neuen Server über pgAdmin mit der Hostadresse "localhost"

- Erstellte eine neue Datenbank namens "bohnanza"

- Importiere die Datenbankstruktur, indem du die  Datei
"bohnanza.sql" öffnest und den gesamten Inhalt über das QueryTool ausführst.

2. HOSTS-Datei bearbeiten:
- Öffne die hosts Datei unter /etc/hosts
- kommentiere "::1" und "127:0:1:1" mittels # aus und füge einen Eintrag deiner IP Adresse und deinen PC Namen hinzu

3. Starten des Servers
- Nach erfolgreicher Installation kannst du nun die Server.jar starten

4. Starten des Clients
- Nach Starten des Servers kannsdt du dich, wie oben beschrieben, registrieren
und andere zum Spielen einladen.