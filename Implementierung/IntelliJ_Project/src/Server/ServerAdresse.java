package Server;

/**
 * ServerAdresse Klasse zum Verwalten der Serveradresse
 *
 * @author Lukas Bayer, Alexander Wilhelm
 * @version 1.0
 */
public class ServerAdresse {

    private static String HOST = "localhost";
    private static int PORT = 2222;


    public static String getHOST() {
        return HOST;
    }

    public static void setHOST(String Host) {
        ServerAdresse.HOST = Host;
    }

    public static int getPORT() {
        return PORT;
    }

    public static void setPORT(int PORT) {
        ServerAdresse.PORT = PORT;
    }
}
