package LogIn;

import Server.DatenbankAdresse;
import User.Benutzer;
import User.BenutzerImpl;

import java.rmi.RemoteException;
import java.rmi.server.RMISocketFactory;
import java.rmi.server.UnicastRemoteObject;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;

/**
 * Verwaltet den Login
 *
 * @author Nils Fitting, Lukas Bayer
 * @version 1.0
 */
public class LogInImpl extends UnicastRemoteObject implements LogIn {

    /*
    // JDBC Treiber name und Datenbanklink
    static final String JDBC_DRIVER = "org.postgresql.Driver";
    static final String DB_URL = "jdbc:postgresql://localhost:5432/bohnanza";

    //  Datenbank Login
    static final String USER = "postgres";
    static final String PASS = "root";*/

    // JDBC Treiber name und Datenbanklink
    static final String JDBC_DRIVER = DatenbankAdresse.getJdbcDriver();
    static final String DB_URL = DatenbankAdresse.getDbUrl();

    //  Datenbank Login
    static final String USER = DatenbankAdresse.getUSER();
    static final String PASS = DatenbankAdresse.getPASS();

    // Connection und Befehl initialisieren
    Connection conn = null;
    Statement stmt = null;

    /**
     * todo: englisch?
     * Creates and exports a new UnicastRemoteObject object using an
     * anonymous port.
     *
     * <p>The object is exported with a server socket
     * created using the {@link RMISocketFactory} class.
     *
     * @throws RemoteException if failed to export object
     * @since 1.1
     */
    public LogInImpl() throws RemoteException {
    }


    private static String password(String password) {
        String generatedPassword = null;
        try {
            //MD5 Hash
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(password.getBytes());
            byte[] bytes = md5.digest();
            //Konvertierung in Hexadezimalsystem
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            //Gehashtes Passwort
            generatedPassword = sb.toString();
        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
        }
        return generatedPassword;
    }

    /**
     * Stellt neue Anmeldedaten in die Datenbank bei erfolgreichem registrieren.
     * kann eine RegistrationException werfen.
     *
     * @param nutzer      Benutzername der noch nicht im Datenbank ist. darf nicht null sein.
     * @param password    Passwort das genutzt wird um spaeter anzmelden zu koennen. darf nicht null sein.
     * @param passwordWdh Passwort das mit pw ueberstimmt.
     * @return true falls die registrietung erfolgreich war, anderfalls false.
     */
    @Override
    public boolean registrieren(String nutzer, String password, String passwordWdh) throws RemoteException {

        //Passwort und Passwortwiederholung stimmen nicht überein
        if (!password.equals(passwordWdh)) {
            System.out.println("Passwort und PasswortWiederholung stimmen nicht überein");
            return false;
        } else {
            try {
                //SQL-Abfrage
                Class.forName(JDBC_DRIVER);
                conn = DriverManager.getConnection(DB_URL, USER, PASS);
                stmt = conn.createStatement();
                String sql;

                /*Finde doppelten Nutzernamen*/

                sql = "SELECT nutzername FROM benutzer WHERE nutzername='" + nutzer + "'";
                ResultSet rs = stmt.executeQuery(sql);


                //Wenn der nutzername belegt ist, liefert SQL eine Ausgabe
                if (rs.next()) {
                    System.out.println("Benutzername schon vorhanden");
                    rs.close();
                    stmt.close();
                    conn.close();
                    return false;
                }


                /*Benutzer in DB einfügen mit Passworthash*/
                PreparedStatement pstmt = conn.prepareStatement(
                        "INSERT INTO benutzer(nutzername,passwort,gewonnene_spiele,spiele_gesamt,max_punkte) VALUES(?,?,?,?,?)");

                pstmt.setString(1, nutzer);
                pstmt.setString(2, password(password));
                pstmt.setInt(3, 0);
                pstmt.setInt(4, 0);
                pstmt.setInt(5, 0);

                int rows = pstmt.executeUpdate();

                //DB-Connections schließen
                rs.close();
                stmt.close();
                conn.close();

                //Errorhandling
            } catch (SQLException sql) {
                sql.printStackTrace();
            } catch (Exception sql2) {
                sql2.printStackTrace();
            } finally {
                try {
                    if (stmt != null)
                        stmt.close();
                } catch (SQLException sql3) {
                }
                try {
                    if (conn != null)
                        conn.close();
                } catch (SQLException sql4) {
                    sql4.printStackTrace();
                }
            }
        }
        return true;
    }

    /**
     * Anmeldefunktion zum einloggen in das System.
     * Kann eine LoginException werfen.
     *
     * @param nutzer   Benutzer name der im Datenbank schon liegt. darf nicht null sein.
     * @param password Passwort des Benutzernames das im Datenbank schon liegt. darf nicht null sein.
     * @return true falls die anmeldung erfolgreich war, anderfalls false.
     */
    @Override
    public boolean anmelden(String nutzer, String password) throws RemoteException {

        //Passwort hashen
        password = password(password);

        try {
            // SQL-Abfrage
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement();
            String sql;
            sql = "SELECT nutzername, passwort FROM benutzer WHERE nutzername='" + nutzer + "'";
            ResultSet rs = stmt.executeQuery(sql);


            //Über Ergebnis iterieren
            while (rs.next()) {
                //Retrieve by column name
                String user = rs.getString("nutzername");
                String pass = rs.getString("passwort");

                //Nuzter gefunden und Passwort stimmt überein
                if (nutzer.equals(user)) {
                    if (password.equals(pass)) {
                        rs.close();
                        stmt.close();
                        conn.close();
                        return true;
                    }
                }
            }
            rs.close();
            stmt.close();
            conn.close();

            //Errorhandling
        } catch (SQLException sql) {
            sql.printStackTrace();
        } catch (Exception sql2) {
            sql2.printStackTrace();
        } finally {
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException sql3) {
            }
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException sql4) {
                sql4.printStackTrace();
            }
        }
        //Benutzername nicht gefunden oder Passwort falsch
        System.out.println("Benutzername oder Passwort falsch");
        return false;
    }

    /**
     * Lädt einen Benutzer und dessen Spielstats aus der Datenbank
     *
     * @param nutzer gesuchter Nutzer
     * @return Benutzer - der Benutzer mit Name nutzer
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    @Override
    public Benutzer ladeBenutzer(String nutzer) throws RemoteException {
        try {
            // SQL-Abfrage
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement();
            String sql;
            sql = "SELECT * FROM benutzer WHERE nutzername='" + nutzer + "'";
            ResultSet rs = stmt.executeQuery(sql);

            Benutzer b;

            //Über Ergebnis iterieren
            while (rs.next()) {
                //Retrieve by column name
                String user = rs.getString("nutzername");
                String pass = rs.getString("passwort");

                //Nuzter gefunden und Passwort stimmt überein
                if (nutzer.equals(user)) {
                    b = new BenutzerImpl(user, pass, rs.getInt("max_punkte"), rs.getInt("gewonnene_Spiele"));
                    rs.close();
                    stmt.close();
                    conn.close();
                    return b;
                }
            }
            rs.close();
            stmt.close();
            conn.close();

            //Errorhandling
        } catch (SQLException sql) {
            sql.printStackTrace();
        } catch (Exception sql2) {
            sql2.printStackTrace();
        } finally {
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException sql3) {
                sql3.printStackTrace();
            }
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException sql4) {
                sql4.printStackTrace();
            }
        }
        return null;
    }
}


