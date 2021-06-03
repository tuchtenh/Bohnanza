package Server;

import Bestenliste.Bestenliste_Impl;
import Chat.ChatServer;
import LogIn.*;
import Raumverwaltung.Lobby_Impl;

import java.net.InetAddress;
import java.rmi.AlreadyBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Server {
    private static final int PORT = 1099;
    private static Registry registry;

    public static void startRegistry() throws RemoteException {
        // Create server registry
        registry =  LocateRegistry.createRegistry(PORT);
    }

    public static void registerObject(String name, Remote remoteObj)
            throws RemoteException, AlreadyBoundException {

        // Bind the object in the registry.
        // It is bind with certain name.
        // Client will lookup on the registration of the name to get object.
        registry.bind(name, remoteObj);
        System.out.println("Registered: " + name + " -> "
                + remoteObj.getClass().getName() + "[" + remoteObj + "]");
    }

    public static void main(String[] args) throws Exception {
        System.out.println("Server starting...");
        startRegistry();

        System.out.println(InetAddress.getLocalHost().getHostAddress());

        //registriere LogIn
        registerObject("LogIn", new LogInImpl());
        //registriere Lobby
        registerObject("Lobby", new Lobby_Impl());
        //registriere LobbyChat
        registerObject("LobbyChat",new ChatServer());
        registerObject("Bestenliste",new Bestenliste_Impl());



        // Server was the start, and was listening to the request from the client.
        System.out.println("Server started!");
    }




}