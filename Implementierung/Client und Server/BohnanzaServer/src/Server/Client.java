package Server;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Vector;

import LogIn.*;
import Raumverwaltung.Lobby;
import Raumverwaltung.Spielvorraum;

public class Client {

    // Host or IP of Server
    final static String HOST = ServerAdresse.getHOST();
    final static int PORT = ServerAdresse.getPORT();
    /*
    private static final String HOST = "localhost";
    private static final int PORT = 1099;
    */
    private static Registry registry;


    public static void main(String[] args) throws Exception {

        // Search the registry in the specific Host, Port.
        registry = LocateRegistry.getRegistry(HOST, PORT);

        // Lookup WeatherService in the Registry.
        LogIn service1 = (LogIn) registry.lookup(LogIn.class.getSimpleName());

        Lobby service2 = (Lobby) registry.lookup(Lobby.class.getSimpleName());

        // Get Message:
        /*
        boolean status = service1.registrieren("Boi","password","password");

        boolean status1 = service1.anmelden("Boi","password");
        boolean status2 = service2.loescheSpieler("Boi","password");
*
        System.out.print("Registrieren-Status:");
        System.out.println(status);
        System.out.print("Anmelde-Status:");
        System.out.println(status1);
        System.out.print("Löschen-Status:");
        System.out.println(status2);

         */

        boolean status = service2.spielVorraumErstellen("Bohnen");
        System.out.print("Erstellt:");
        System.out.println(status);
        Vector<Spielvorraum> raeume = service2.getRaeume();
        System.out.println(raeume.get(0).getName());


    }
}