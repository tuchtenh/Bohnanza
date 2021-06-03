package Server;

/**
 * DatenbankAdresse - verwaltet den Datenbakzugang
 *
 * @author Lukas Bayer
 * @version 1.0
 */
public class DatenbankAdresse {

    // JDBC Treiber name und Datenbanklink
    private static String JDBC_DRIVER = "org.postgresql.Driver";
    private static String DB_URL = "jdbc:postgresql://localhost:5432/bohnanza";

    //  Datenbank Login
    private static String USER = "postgres";
    private static String PASS = "root";


    public static String getJdbcDriver() {
        return JDBC_DRIVER;
    }

    public static String getDbUrl() {
        return DB_URL;
    }

    public static String getPASS() {
        return PASS;
    }

    public static String getUSER() {
        return USER;
    }
}