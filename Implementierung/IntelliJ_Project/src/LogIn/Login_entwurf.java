package LogIn;

import java.sql.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

class Login_entwurf {
    // JDBC Treiber name und Datenbanklink
    static final String JDBC_DRIVER = "org.postgresql.Driver";
    static final String DB_URL = "jdbc:postgresql://localhost:5432/bohnanza";

    //  Datenbank Login
    static final String USER = "postgres";
    static final String PASS = "root";

    // Connection und Befehl initialisieren
    Connection conn = null;
    Statement stmt = null;

    //Registriert einen Nutzer
    public boolean registrieren(String nutzer, String password, String passwordWdh) {

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

                sql = "SELECT nutzername FROM benutzer";
                ResultSet rs = stmt.executeQuery(sql);

                //Iteriere durch Einträge und vergleiche Name
                while (rs.next()) {
                    String user = rs.getString("nutzername");
                    if (nutzer.equals(user)) {
                        rs.close();
                        stmt.close();
                        conn.close();
                        System.out.println("Benutzername schon vorhanden");
                        return false;
                    }
                }


                /*Benutzer in DB einfügen mit Passworthash*/
                PreparedStatement pstmt = conn.prepareStatement(
                        "INSERT INTO benutzer (nutzername, passwort) VALUES(?,?)");

                pstmt.setString(1, nutzer);
                pstmt.setString(2, password(password));
                int rows = pstmt.executeUpdate();

                //TODO: statistik und benutzer sollten als statement gleichzeitig ausgeführt werden
                /*Benutzerstatistik hinzufügen*/
                pstmt=conn.prepareStatement("INSERT INTO statistik(nutzername,gewonnene_spiele,spiele_gesamt,max_punkte) VALUES(?,?,?,?)");

                pstmt.setString(1, nutzer);
                pstmt.setInt(2, 0);
                pstmt.setInt(3, 0);
                pstmt.setInt(4, 0);
                rows = pstmt.executeUpdate();

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

    //Meldet einen Nutzer an
    public boolean anmelden(String nutzer, String password) {

        //Passwort hashen
        password = password(password);

        try {
            // SQL-Abfrage
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement();
            String sql;
            sql = "SELECT name, password FROM users";
            ResultSet rs = stmt.executeQuery(sql);

            //Über Ergebnis iterieren
            while (rs.next()) {
                //Retrieve by column name
                String user = rs.getString("name");
                String pass = rs.getString("password");

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


