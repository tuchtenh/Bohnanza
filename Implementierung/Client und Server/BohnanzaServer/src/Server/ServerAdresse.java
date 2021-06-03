//TODO: Verwalter-Klasse für Zentrale IP-Verwaltung des Servers

package Server;


import java.net.InetAddress;

public class ServerAdresse {

    private static String HOST = "131.246.70.105";
    private static int PORT = 1099;


    public static String getHOST(){
        return HOST;
    }

    public static int getPORT(){
        return PORT;
    }

    public static void setHOST(String Host){
        ServerAdresse.HOST = Host;
    }

    public static void setPORT(int PORT) {
        ServerAdresse.PORT = PORT;
    }
}
