package Bestenliste;

import User.*;


import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.*;
import java.util.ArrayList;
import java.util.Vector;

public class Bestenliste_Impl extends UnicastRemoteObject implements Bestenliste, Serializable {

    public Bestenliste_Impl() throws RemoteException {
    }

    public Vector<Benutzer> getListe() {


        // JDBC Treiber name und Datenbanklink
        String JDBC_DRIVER = "org.postgresql.Driver";
        String DB_URL = "jdbc:postgresql://localhost:5432/bohnanza";

        //  Datenbank Login
        String USER = "postgres";
        String PASS = "root";

        // Connection und Befehl initialisieren
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        Vector<Benutzer> liste = new Vector<>();
        try {
            //SQL-Abfrage
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement();
            String sql;

            /*Finde doppelten Nutzernamen*/

            sql = "SELECT * FROM benutzer ORDER BY gewonnene_spiele DESC ";
            rs = stmt.executeQuery(sql);



            while (rs.next()) {

                //Retrieve by column name
                String name = rs.getString("nutzername");
                String passwort = rs.getString("passwort");
                int spiele = rs.getInt("gewonnene_spiele");
                int punkte = rs.getInt("max_punkte");

                Benutzer benutzer = new BenutzerImpl(name, passwort, punkte, spiele);

                liste.addElement(benutzer);


            }
            rs.close();
            stmt.close();
            conn.close();
            return liste;
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

        return liste;
    }
}


