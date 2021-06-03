Willkommen zum Spiel Bohnanza!

WINDOWS:

Um zu spielen, musst du Java 8 oder h�her als aktive Java Runtime Environment
installiert haben.
Um die .jar-Dateien im Folgenden auszuf�hren musst du sie in der Konsole deines
Betriebssystems ausf�hren mit:

java -jar <Name_der_JarDatein>.jar

--------------------------------------------------------------------------------
M�chtest du nur als Client gegen andere Spieler spielen? Dann musst du nur die
CLient.jar ausw�hlen und unter "Serveradresse setzen" einen dir bekannten Server
eintragen. Anschlie�end musst du dich Registrieren und kannst loslegen!

--------------------------------------------------------------------------------
M�chtest du als Server agieren und ggf. alleine gegen Bots oder gegen menschliche
Spieler spielen? Dann sind ein paar mehr Schritte erforderlich:

1. Installation der Datenbank
- F�hre die Datei "postgresql-11.3-4-windows-x64.exe" im aktuellen Ordner aus
und installiere Postgres in einem beliebigen Ordner. W�hle bei der Installation
den Benutzer "postgres" und das Passwort "root" aus.

- Nach erfolgreicher Installtion kannst du den Datenbankserver mittels "pgAdmin"
aufrufen.

- Erstellte eine neue Datenbank namens "bohnanza"

- Importiere die Datenbankstruktur, indem du die  Datei
"bohnanza.sql" �ffnest und den gesamten Inhalt �ber das QueryTool ausf�hrst.

2. Starten des Servers
- Nach erfolgreicher Installation kannst du nun die Server.jar starten

3. Starten des Clients
- Nach Starten des Servers kannsdt du dich, wie oben beschrieben, registrieren
und andere zum Spielen einladen.

LINUX:

Um zu spielen, musst du Java 8 als aktive Java Runtime Environment
installiert haben.
Um die .jar-Dateien im Folgenden auszuf�hren musst du sie in der Konsole deines
Betriebssystems ausf�hren mit:

java -jar <Name_der_JarDatein>.jar
--------------------------------------------------------------------------------
M�chtest du nur als Client gegen andere Spieler spielen? Dann musst du, je nach
Linux Distribution, die Firewall deaktivieren. Unter Ubuntu z.B. durch "sudo ufw
disable". Zus�tzlich sollten die IPTABLES geleert und deaktiviert werden (schnelle L�sung - nicht empfohlen), bzw.
spezifische Regeln (Rules) eingerichtet werden (empfohlen). Zum Deaktivieren gib folgende
Kommandozeilenbefehle nacheinander ein:

iptables -P INPUT ACCEPT
iptables -P OUTPUT ACCEPT
iptables -P FORWARD ACCEPT

iptables -F

Nun sollten keine Ports mehr geblockt werden. Du kannst nun die Client.jar Datei
ausf�hren, die Serveradresse setzen und loslegen.

--------------------------------------------------------------------------------
M�chtest du als Server agieren und ggf. alleine gegen Bots oder gegen menschliche
Spieler spielen? Dann sind ein paar mehr Schritte erforderlich:

ARCH:
1. Installation der Datenbank (ausf�hrlich unter https://wiki.archlinux.org/index.php/PostgreSQL#Installation)
- Installiere postgresql 11.4-1 oder h�her mittels yaourt
- Installiere pgAdmin 4 oder h�her mittels yaourt

- Nach erfolgreicher Installtion kannst du die Datenbank initialisieren (Kommandozeile):
sudo -iu postgres
initdb -D /var/lib/postgres/data

- Erstelle einen Nutzer postgres mit Passwort root (Kommandozeile):
createuser --interactive

- Gibt Zugriffsrechte von au�erhalb, indem du postgresql.conf �ffnest und den EIntrag
"listen_addresses = 'localhost, local_ip'" setzt, wobei local_ip deine lokale IP-Adresse
ist (zu findne �ber ip address in Kommandozeile).

- Erstelle einen neuen Server �ber pgAdmin mit der Hostadresse "localhost"

- Erstellte eine neue Datenbank namens "bohnanza"

- Importiere die Datenbankstruktur, indem du die  Datei
"bohnanza.sql" �ffnest und den gesamten Inhalt �ber das QueryTool ausf�hrst.

2. HOSTS-Datei bearbeiten:
- �ffne die hosts Datei unter /etc/hosts
- kommentiere "::1" und "127:0:1:1" mittels # aus und f�ge einen Eintrag deiner IP Adresse und deinen PC Namen hinzu

3. Starten des Servers
- Nach erfolgreicher Installation kannst du nun die Server.jar starten

4. Starten des Clients
- Nach Starten des Servers kannsdt du dich, wie oben beschrieben, registrieren
und andere zum Spielen einladen.