package Tests.Server;

import LogIn.LogInImpl;
import Server.Server;
import org.junit.BeforeClass;
import org.junit.Test;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;

import static org.junit.Assert.*;

public class ServerTest {
    private static Server server;

    /**
     * erstellt einen neuen Server für Tests und startet diesen
     *
     * @throws Exception wenn main() nicht startet
     */
    @BeforeClass
    public static void beforeClass() throws Exception {
        server = new Server();
        String[] args = null;
        Server.main(args);
    }

    /**
     * testet, ob sich Objekte an die Registry anbinden lassen
     *
     * @throws RemoteException       wenn Verbindung zum Server schiefläuft
     * @throws AlreadyBoundException wenn bereits an Registry gebunden
     * @throws NotBoundException     wenn nicht an die Registry gebunden
     */
    @Test
    public void registerObject() throws RemoteException, AlreadyBoundException, NotBoundException {
        Server.registerObject("LogIn2", new LogInImpl());

        //alle Objekte in Registry registiert und auch nur diese:
        Registry reg = server.getRegistry();
        String[] list = reg.list();
        assertEquals(6, list.length);

        //Vergleich:
        int count = 0;
        boolean lobbyreg = false;
        boolean loginreg = false;
        boolean lobbychatregs = false;
        boolean bestenlistereg = false;
        boolean login2reg = false;
        boolean lobbyrefresherreg = false;
        boolean diff = false;

        for (int i = 0; i < 6; i++) {
            if (list[i] == "LogIn") {
                if (loginreg == true) {
                    diff = true;
                } else {
                    count++;
                    loginreg = true;
                }
            } else if (list[i] == "Lobby") {
                if (lobbyreg == true) {
                    diff = true;
                } else {
                    count++;
                    lobbyreg = true;
                }
            } else if (list[i] == "LobbyChat") {
                if (lobbychatregs == true) {
                    diff = true;
                } else {
                    count++;
                    lobbychatregs = true;
                }
            } else if (list[i] == "Bestenliste") {
                if (bestenlistereg == true) {
                    diff = true;
                } else {
                    count++;
                    bestenlistereg = true;
                }
            } else if (list[i] == "LogIn2") {
                if (login2reg == true) {
                    diff = true;
                } else {
                    count++;
                    login2reg = true;
                }
            } else if (list[i] == "LobbyRefresher") {
                if (lobbyrefresherreg == true) {
                    diff = true;
                } else {
                    count++;
                    lobbyrefresherreg = true;
                }
            } else {
                diff = true;
            }
        }

        assertFalse(diff);
        assertEquals(6, count);
        reg.unbind("LogIn2");
    }

    /**
     * testet, ob main() die Registry startet und alle Objekte daran bindet
     *
     * @throws Exception wenn main() nicht gestartet werden kann
     */
    @Test
    public void main() throws Exception {

        //Registry gestartet:
        assertNotEquals(null, server.getRegistry());

        //alle Objekte in Registry registiert und auch nur diese:
        Registry reg = server.getRegistry();
        String[] list = reg.list();
        assertEquals(5, list.length);

        //Vergleich:
        int count = 0;
        boolean lobbyreg = false;
        boolean loginreg = false;
        boolean lobbychatregs = false;
        boolean bestenlistereg = false;
        boolean lobbyrefresherreg = false;
        boolean diff = false;

        for (int i = 0; i < 5; i++) {
            if (list[i] == "LogIn") {
                if (loginreg == true) {
                    diff = true;
                } else {
                    count++;
                    loginreg = true;
                }
            } else if (list[i] == "Lobby") {
                if (lobbyreg == true) {
                    diff = true;
                } else {
                    count++;
                    lobbyreg = true;
                }
            } else if (list[i] == "LobbyChat") {
                if (lobbychatregs == true) {
                    diff = true;
                } else {
                    count++;
                    lobbychatregs = true;
                }
            } else if (list[i] == "Bestenliste") {
                if (bestenlistereg == true) {
                    diff = true;
                } else {
                    count++;
                    bestenlistereg = true;
                }
            } else if (list[i] == "LobbyRefresher") {
                if (lobbyrefresherreg == true) {
                    diff = true;
                } else {
                    count++;
                    lobbyrefresherreg = true;
                }
            } else {
                diff = true;
            }
        }

        assertFalse(diff);
        assertEquals(5, count);
    }

    /**
     * testet, ob eine Registry zurückgegeben wird
     */
    @Test
    public void getRegistry() {
        assertNotEquals(null, server.getRegistry());
    }
}