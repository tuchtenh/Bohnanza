package Server;

import Bestenliste.Bestenliste_Impl;
import Chat.ChatServer;
import LogIn.LogInImpl;
import Observer.Refresher.Lobby.RefresherLobbyServer;
import Raumverwaltung.Lobby_Impl;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.AlreadyBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * Server für RMI
 *
 * @author Lukas Bayer, Alexander Wilhelm
 * @version 1.0
 */
public class Server {
    private static final int PORT = 2222;
    private static Registry registry;

    /**
     * Bindet Objekte an die Regsitry an
     *
     * @param name      Name des Objekts
     * @param remoteObj das anzubindende Objekt
     * @throws RemoteException       wenn bei Verbindung zum Server etwas schiefläuft
     * @throws AlreadyBoundException wenn bereits an Registry gebunden
     */
    public static void registerObject(String name, Remote remoteObj)
            throws RemoteException, AlreadyBoundException {
        try {
            System.setProperty("java.rmi.server.hostname", InetAddress.getLocalHost().getHostAddress());

            registry.bind(name, remoteObj);
            System.out.println("Registered: " + name + " -> "
                    + remoteObj.getClass().getName() + "[" + remoteObj + "]");
        } catch (UnknownHostException u) {
            u.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        System.setProperty("java.rmi.server.hostname", InetAddress.getLocalHost().getHostAddress());
        System.out.println("Server starting...");

        registry = LocateRegistry.createRegistry(PORT);

        System.out.println(InetAddress.getLocalHost().getHostAddress());

        //registriere LogIn
        registerObject("LogIn", new LogInImpl());
        //registriere Lobby
        registerObject("Lobby", new Lobby_Impl());
        //registriere LobbyChat
        registerObject("LobbyChat", new ChatServer());
        //registriere Bestenliste
        registerObject("Bestenliste", new Bestenliste_Impl());
        //registriere Lobbyrefresher
        registerObject("LobbyRefresher", new RefresherLobbyServer());

        // Server ist gestartet
        System.out.println("Server started!");
    }

    //für Tests:
    public Registry getRegistry() {
        return registry;
    }

}