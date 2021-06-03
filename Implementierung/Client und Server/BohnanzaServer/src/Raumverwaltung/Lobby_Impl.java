package Raumverwaltung;

import User.Benutzer;

import java.sql.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import java.util.Iterator;
import java.util.Vector;

public class Lobby_Impl extends UnicastRemoteObject implements Lobby {

    //DatenbankAdresse
    static final String JDBC_DRIVER = "org.postgresql.Driver";
    static final String DB_URL = "jdbc:postgresql://localhost:5432/bohnanza";

    //  Datenbank Login
    static final String USER = "postgres";
    static final String PASS = "root";

    // Connection und Befehl initialisieren
    Connection conn = null;
    Statement stmt = null;

    public Vector<Spielvorraum> raeume;


    public Lobby_Impl() throws RemoteException {

        this.raeume = new Vector<Spielvorraum>();

    }


    /**Der User wird abgemeldet und das Client schliest
     * @return bestaetigung das der Spieler abgemeldet wurde
     * @throws RemoteException
     */
    @Override
    public boolean abmelden() throws RemoteException{
        //TODO: WEITERLEITEN AUF LOGIN OBERFLÄCHE
        return false;
    }

    /** Loescht den User aus der Datenbank
     * @param nutzer Name des Users. muss gleich den Namen von dem User sein der sein User loechen moechte.
     * @param passwort Passwort des Users. muss gleich den Passwort von dem User sein der sein User loechen moechte.
     * @return bestaetigung das der User geloescht wurde
     * @throws RemoteException
     */
    @Override
    public boolean loescheSpieler(String nutzer, String passwort) throws RemoteException{


        //TODO: WEITERLEITEN AUF LOGIN OBERFLÄCHE

        //Passwort hashen
        passwort = password(passwort);

        try {
            // SQL-Abfrage
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement();
            String sql;
            sql = "SELECT nutzername, passwort FROM benutzer WHERE nutzername='"+nutzer+"'";
            ResultSet rs = stmt.executeQuery(sql);



            //Über Ergebnis iterieren
            while (rs.next()) {
                //Retrieve by column name
                String user = rs.getString("nutzername");
                String pass = rs.getString("passwort");

                //Nuzter gefunden und Passwort stimmt überein
                if (nutzer.equals(user)) {
                    if (passwort.equals(pass)) {
                        PreparedStatement pstmt = conn.prepareStatement("DELETE FROM benutzer WHERE nutzername=?");
                        pstmt.setString(1, nutzer);
                        int row=pstmt.executeUpdate();
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

    /**Erstellt einen Spielvorraum
     * @param name Beliebiger name eines Spielvorraumes. darf nicht null sein
     * @return gibt zurueck eine bestaetigung
     * @throws RemoteException
     */
    @Override
    public boolean spielVorraumErstellen(String name) throws RemoteException {
        //Iterator<Spielvorraum> itr = raeume.iterator();
        //Teste, ob Name bereits vergeben
        Spielvorraum s;
        for (int i=0;i<raeume.size();i++){
            if(raeume.get(i).equals(name)){
                System.out.println("Name bereits vergeben!");
                return false;
            }
        }
        Spielvorraum sv = new Spielvorraum_Impl(name);

        raeume.add(sv);

        return true;
    }

    /** gibt die Liste von Spielvorraeumen die im Moment erstellt sind
     * @return Alle Spielraeume die erstellt sind
     * @throws RemoteException
     */
    @Override
    public Vector<Spielvorraum> getRaeume() throws RemoteException{
        return raeume;
    }








    //Passworthash (übernommen)
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

}